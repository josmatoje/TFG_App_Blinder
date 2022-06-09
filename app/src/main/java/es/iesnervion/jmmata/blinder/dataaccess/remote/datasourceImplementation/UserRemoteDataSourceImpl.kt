package es.iesnervion.jmmata.blinder.dataaccess.remote.datasourceImplementation

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import es.iesnervion.jmmata.blinder.businessObject.UserBO
import es.iesnervion.jmmata.blinder.businessObject.UserMatchBO
import es.iesnervion.jmmata.blinder.dataaccess.datatsource.UserRemoteDataSource
import es.iesnervion.jmmata.blinder.dataaccess.remote.firebase.dto.FriendDTO
import es.iesnervion.jmmata.blinder.dataaccess.remote.firebase.dto.UserDTO
import es.iesnervion.jmmata.blinder.dataaccess.remote.firebase.dto.UserMatchDTO
import es.iesnervion.jmmata.blinder.dataaccess.remote.toBO
import es.iesnervion.jmmata.blinder.dataaccess.remote.toDTO
import es.iesnervion.jmmata.blinder.dataaccess.remote.toUserBO
import es.iesnervion.jmmata.blinder.dataaccess.remote.toUserDTO
import kotlinx.coroutines.tasks.await

class UserRemoteDataSourceImpl: UserRemoteDataSource {

    private var db = Firebase.firestore
    private var auth: FirebaseAuth = Firebase.auth

    override suspend fun getRemoteUsers(afterAction:(QuerySnapshot)->Unit) {
        db  .collection("Users")
            .get()
            .addOnSuccessListener {
                afterAction(it)
            }.await()
    }

    override suspend fun getRemoteUsersFriends(): List<UserBO> {
        val friends = ArrayList<UserBO>()
        var userDocument: UserBO
        getRemoteUsers { document ->
            for (user in document){
                userDocument = user.toObject<UserDTO>().toUserBO()
                userDocument.id = user.id
                friends.add(userDocument)
            }
        }
        try{
            friends.filter { it.id?.let { id -> getFriendship(id) } == true }
        }catch (e: NoSuchElementException){ }
        finally {
            return friends
        }
    }


    override suspend fun getRemoteUser(id: String): UserBO {
        var user = UserBO()
        db.collection("Users")
            .document(id)
            .get()
            .addOnSuccessListener {
                user = it.toObject<UserBO>() ?: UserBO()
            }
        return user
    }



    override suspend fun createAuthUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    //TODO: add boolean to indicate the state of the insertion
                }
            }
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
        auth.uid?.let{ authId ->
            userMatch.id?.let { id ->
                db.collection("Users")
                    .document(authId)
                    .collection("UserMatches")
                    .document(id)
                    .set(userMatch.toDTO())
            }//TODO: DTO no almacena el id
        }


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



    override suspend fun getmatchesFrom(id: String): List<UserMatchBO> {
        val userList: ArrayList<UserMatchBO> =  ArrayList()
        db.collection("Users")
            .document(id)
            .collection("UserMatches")
            .get()
            .addOnSuccessListener { document ->
                for (userMatch in document){
                    userList.add(userMatch.toObject<UserMatchDTO>().toBO())
                }
            }.await()
        return userList
    }

    @Throws( NoSuchElementException::class)
    override suspend fun getFriendship(friendId: String): Boolean  =
        if (getmatchesFrom(auth.uid ?: "").first { it.id == friendId }.isMatch == true){
            getmatchesFrom(friendId).first { it.id == auth.uid ?: "" }.isMatch == true
        } else false

    override suspend fun deleteRemoteFriendship(userId: String) {
        db.collection("Users")
            .document(auth.uid ?: "")
            .collection("UserMatches")
            .document(userId)
            .delete()
    }

}