package es.iesnervion.jmmata.blinder.dataaccess.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.ktx.Firebase
import es.iesnervion.jmmata.blinder.businessObject.FriendBO
import es.iesnervion.jmmata.blinder.businessObject.UserBO
import es.iesnervion.jmmata.blinder.businessObject.UserMatchBO
import es.iesnervion.jmmata.blinder.dataaccess.datatsource.UserLocalDataSource
import es.iesnervion.jmmata.blinder.dataaccess.datatsource.UserRemoteDataSource
import kotlinx.coroutines.Dispatchers
/*Clase repositorio que recoge los metodos de manera genérica e implementa un datasource local o remoto en función de como desee tratar los datos*/
class UsersRepository(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource
) {
    //Getters
    suspend fun getUsers(prueba:(QuerySnapshot)->Unit) =
        userRemoteDataSource.getRemoteUsers(prueba)

    suspend fun getUserById(id: String): UserBO =
        userRemoteDataSource.getRemoteUser(id)

    suspend fun getUsersFilterBy(gender: String? = null,
                                 sexuality: String? = null,
                                 proximity: Int? = null,
                                 older: Int? = null,
                                 younger: Int? = null,
                                 likes: List<String>? = null): List<UserBO> =
        userRemoteDataSource.getRemoteUsersFilterBy(gender, sexuality, proximity, older, younger, likes)


    suspend fun getFriendsFromActualUser(afterAction:(QuerySnapshot)->Unit) =
        userRemoteDataSource.getRemoteUsersFriends(afterAction)

    suspend fun getFriendFrom(id: String, afterAction:(FriendBO)->Unit){
        userRemoteDataSource.getRemoteFriend(id, afterAction)
    }

    suspend fun getMatchesFrom(id: String): List<UserMatchBO> =
        userRemoteDataSource.getMatchesFrom(id)

    suspend fun isFriendship(friendId: String): Boolean =
        userRemoteDataSource.getFriendship(friendId)

    //Inserts
    suspend fun createtUserAuth(email: String, password: String, afterAction:(Task<AuthResult>)->Unit) =
        userRemoteDataSource.createAuthUser(email, password, afterAction)

    suspend fun insertUser(user: UserBO) =
        userRemoteDataSource.insertRemoteUser(user)

    suspend fun inserteMatch(userMatch: UserMatchBO) {
        userRemoteDataSource.insertRemoteMatch(userMatch)
        /*if(userMatch.isMatch==true){
            val userId = getUserById(userMatch.id ?: "")
            userLocalDataSource.insertLocalUser(userId)
        }*/
    }

    //suspend fun insertFriendsOnLocal(usersList: List<UserBO>) =
        //userLocalDataSource.insertLocalUsersList(usersList)

    //Updates
    suspend fun updateUserDescription(user: UserBO): Boolean {
        var updated = false
        user.id?.let {
            updated = userRemoteDataSource.updateDescriptionRemoteUser(it, user.description)
        }
        return updated
    }

    //Deletes
    suspend fun deleteFriendShip(id: String) {
        //userLocalDataSource.deleteLocalUser(id)
        userRemoteDataSource.deleteRemoteFriendship(id)
    }

    //suspend fun deleteLocalDatabase() =
        //userLocalDataSource.deleteAllLocalUser()

}