package es.iesnervion.jmmata.blinder.dataaccess.datatsource

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.auth.User
import es.iesnervion.jmmata.blinder.businessObject.FriendBO
import es.iesnervion.jmmata.blinder.businessObject.UserBO
import es.iesnervion.jmmata.blinder.businessObject.UserMatchBO

interface UserRemoteDataSource { //Interfaz para ser implementada en una clase RemoteDataSource perteneciente a nuestro modelo de bbdd

    suspend fun getRemoteUsers(afterAction:(QuerySnapshot)->Unit)

    suspend fun getRemoteUser(id: String): UserBO

    suspend fun getRemoteUsersFriends(afterAction:(QuerySnapshot)->Unit)

    suspend fun getRemoteFriend(id: String, afterAction:(FriendBO)->Unit)

    suspend fun createAuthUser(email: String, password: String, afterAction:(Task<AuthResult>)->Unit)

    suspend fun insertRemoteUser(user: UserBO)

    suspend fun insertRemoteMatch(userMatch: UserMatchBO)

    suspend fun updateDescriptionRemoteUser(userId: String, newDescription: String): Boolean

    suspend fun getRemoteUsersFilterBy(gender: String? = null,
                                       sexuality: String? = null,
                                       proximity: Int? = null,
                                       older: Int? = null,
                                       younger: Int? = null,
                                       likes: List<String>? = null): List<UserBO>

    suspend fun getMatchesFrom(id: String): List<UserMatchBO>

    suspend fun getFriendship(friendId: String): Boolean

    suspend fun deleteRemoteFriendship(userId: String)

}