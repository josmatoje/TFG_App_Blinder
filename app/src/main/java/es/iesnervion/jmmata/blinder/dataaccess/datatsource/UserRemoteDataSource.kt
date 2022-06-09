package es.iesnervion.jmmata.blinder.dataaccess.datatsource

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.auth.User
import es.iesnervion.jmmata.blinder.businessObject.UserBO
import es.iesnervion.jmmata.blinder.businessObject.UserMatchBO

interface UserRemoteDataSource {

    suspend fun getRemoteUsers(afterAction:(QuerySnapshot)->Unit)

    suspend fun getRemoteUsersFriends(): List<UserBO>

    suspend fun getRemoteUser(id: String): UserBO

    suspend fun createAuthUser(email: String, password: String)

    suspend fun insertRemoteUser(user: UserBO)

    suspend fun insertRemoteMatch(userMatch: UserMatchBO)

    suspend fun updateDescriptionRemoteUser(userId: String, newDescription: String): Boolean

    suspend fun getRemoteUsersFilterBy(gender: String? = null,
                                       sexuality: String? = null,
                                       proximity: Int? = null,
                                       older: Int? = null,
                                       younger: Int? = null,
                                       likes: List<String>? = null): List<UserBO>

    suspend fun getmatchesFrom(id: String): List<UserMatchBO>

    suspend fun getFriendship(friendId: String): Boolean

    suspend fun deleteRemoteFriendship(userId: String)

}