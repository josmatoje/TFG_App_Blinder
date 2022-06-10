package es.iesnervion.jmmata.blinder.dataaccess.remote.datasourceImplementation

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import es.iesnervion.jmmata.blinder.businessObject.FriendBO
import es.iesnervion.jmmata.blinder.businessObject.UserBO
import es.iesnervion.jmmata.blinder.businessObject.UserMatchBO
import es.iesnervion.jmmata.blinder.dataaccess.datatsource.UserRemoteDataSource
import es.iesnervion.jmmata.blinder.dataaccess.remote.firebase.dto.UserMatchDTO
import es.iesnervion.jmmata.blinder.dataaccess.remote.toBO
import es.iesnervion.jmmata.blinder.dataaccess.remote.toDTO
import es.iesnervion.jmmata.blinder.dataaccess.remote.toUserDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class UserRemoteDataSourceImpl: UserRemoteDataSource { //Clase que realiza llamadas a firebase. En la mayoria de casos se devuelven
// objetos BO que son los que usa la aplicación.

    //Variables
    private var db = Firebase.firestore
    private var auth = Firebase.auth
    private var authId = Firebase.auth.uid ?: ""


    /*Descripción: devuelve el listado de usuarios de la base de datos de firebase como parámetro a la función (o tarea) que se le pasa por
    parámetros a esta función.*/
    override suspend fun getRemoteUsers(afterAction:(QuerySnapshot)->Unit) {
        db  .collection("Users")
            .get()
            .addOnSuccessListener {
                afterAction(it)
            }.await()
    }

    /*Descripción: devuelve un usuario de firebase correspondiente al id pasado por parametros*/
    override suspend fun getRemoteUser(id: String): UserBO {
        var user = UserBO()
        db.collection("Users")
            .document(id)
            .get()
            .addOnSuccessListener {
                user = it.toObject<UserBO>() ?: UserBO()
            }.await()
        return user
    }

    /*Descripción: metodo que llama a firebase y recoge el listado de documentos de amigos del usuario registrado y los pasa como parámetros a la
    función (o tarea) que se recibe por parámetros.*/
    override suspend fun getRemoteUsersFriends(afterAction:(QuerySnapshot)->Unit) {
        db.collection("Users")
            .document(authId)
            .collection("UserMatches")
            .get()
            .addOnSuccessListener {
                afterAction(it)
                /*GlobalScope.launch(Dispatchers.IO) {
                    collection.filter { getFriendship(it.id) }
                                .map { getRemoteFriend(it.id) }
                }*/
            }.await()
    }

    /*Descripción: método que llama a firebase y recoge un amigos del usuario registrado indicado por el id que recibe y lo pasa como parámetros
    a la función (o tarea) que se recibe por parámetros.*/
    override suspend fun getRemoteFriend(id: String, afterAction:(FriendBO)->Unit) {
        var friend = FriendBO()
        db.collection("Users")
            .document(id)
            .get()
            .addOnSuccessListener {
                friend = it.toObject<FriendBO>() ?: FriendBO()
                afterAction(friend)
            }.await()
    }

/*

    override suspend fun getRemoteFriend(id: String): FriendBO {
        var friend = FriendBO()
        db.collection("Users")
            .document(id)
            .get()
            .addOnSuccessListener {
                friend = it.toObject<FriendBO>() ?: FriendBO()
            }.await()
        return friend
    }
*/

    override suspend fun createAuthUser(email: String, password: String, afterAction:(Task<AuthResult>)->Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                afterAction(it)
            }.await()
    }

    override suspend fun insertRemoteUser( user: UserBO) {
        user.id?.let { id ->
            db.collection("Users")
                .document(id)
                .set(
                    user.toUserDTO()
                )
        }
    }

    override suspend fun insertRemoteMatch(userMatch: UserMatchBO) {
        userMatch.id?.let { id ->
            db.collection("Users")
                .document(authId)
                .collection("UserMatches")
                .document(id)
                .set(userMatch.toDTO())
        }//TODO: DTO no almacena el id
    }

    override suspend fun updateDescriptionRemoteUser(userId: String, newDescription: String): Boolean {
        var updated = false
        db.collection("Users")
            .document(userId)
            .update("description", newDescription)
            .addOnFailureListener{ updated = false }
            .addOnCompleteListener { updated = true }
        return updated
    }

    override suspend fun getRemoteUsersFilterBy(gender: String?,
                                                sexuality: String?,
                                                proximity: Int?,
                                                older: Int?,
                                                younger: Int?,
                                                likes: List<String>?): List<UserBO> {

        return emptyList() //TODO: implement filter
    }

    override suspend fun getMatchesFrom(id: String): List<UserMatchBO> {
        val userList: ArrayList<UserMatchBO> =  ArrayList()
        var auxUserMatch: UserMatchBO
        db.collection("Users")
            .document(id)
            .collection("UserMatches")
            .get()
            .addOnSuccessListener { document ->
                for (userMatch in document){
                    auxUserMatch = userMatch.toObject<UserMatchDTO>().toBO()
                    auxUserMatch.id = userMatch.id
                    userList.add(auxUserMatch)
                }
            }.await()
        return userList
    }

    override suspend fun getFriendship(friendId: String): Boolean {
        var friends: List<UserMatchBO>
        var isFriend = false

        GlobalScope.launch(Dispatchers.IO) {
            friends = getMatchesFrom(authId)
             isFriend = if (friends.isNotEmpty() &&
                 friends.firstOrNull() { it.id == friendId }?.isMatch == true) {
                friends = getMatchesFrom(friendId)
                friends.isNotEmpty() &&
                    friends.firstOrNull { it.id == authId }?.isMatch == true
            } else false
        }
        return isFriend
    }

    override suspend fun deleteRemoteFriendship(userId: String) {
        db.collection("Users")
            .document(auth.uid ?: "")
            .collection("UserMatches")
            .document(userId)
            .delete()
    }


}