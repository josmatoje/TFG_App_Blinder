package es.iesnervion.jmmata.blinder.dataaccess.repository

import com.google.firebase.firestore.QuerySnapshot
import es.iesnervion.jmmata.blinder.businessObject.UserBO
import es.iesnervion.jmmata.blinder.businessObject.UserMatchBO
import es.iesnervion.jmmata.blinder.dataaccess.datatsource.UserLocalDataSource
import es.iesnervion.jmmata.blinder.dataaccess.datatsource.UserRemoteDataSource
import kotlinx.coroutines.Dispatchers

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


    suspend fun getFriendsFromActualUser(): List<UserBO> = //{
        //var users = userLocalDataSource.getLocalUsers()
        //if(users.isEmpty()) {
            //users =
                userRemoteDataSource.getRemoteUsersFriends()
            //userLocalDataSource.insertLocalUsersList(users)
        //}
        //return users
    //}

    suspend fun getFriendFromActualUser(id: String): UserBO =
        //userLocalDataSource.getLocalUser(id)
        userRemoteDataSource.getRemoteUsersFriends().first { it.id == id }


    suspend fun getMatchessFrom(id: String): List<UserMatchBO> =
        userRemoteDataSource.getmatchesFrom(id)

    suspend fun isFriendship(friendId: String): Boolean =
        userRemoteDataSource.getFriendship(friendId)

    //Inserts
    suspend fun createtUserAuth(email: String, password: String) =
        userRemoteDataSource.createAuthUser(email, password)

    suspend fun insertUser(user: UserBO) =
        userRemoteDataSource.insertRemoteUser(user)

    suspend fun inserteMatch(userMatch: UserMatchBO) {
        userRemoteDataSource.insertRemoteMatch(userMatch)
        /*if(userMatch.isMatch==true){
            val userId = getUserById(userMatch.id ?: "")
            userLocalDataSource.insertLocalUser(userId)
        }*/
    }

    suspend fun insertFriendsOnLocal(usersList: List<UserBO>) =
        userLocalDataSource.insertLocalUsersList(usersList)

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