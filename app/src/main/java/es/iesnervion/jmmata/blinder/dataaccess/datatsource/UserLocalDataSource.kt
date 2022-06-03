package es.iesnervion.jmmata.blinder.dataaccess.datatsource

import es.iesnervion.jmmata.blinder.businessObject.UserBO
import es.iesnervion.jmmata.blinder.dataaccess.local.room.dbo.UserDBO

interface UserLocalDataSource {

    suspend fun getLocalUsers(): List<UserBO>

    suspend fun getLocalUser(id: String): UserBO

    suspend fun getLocalUserFriendsFrom(id: String): List<UserBO>

    suspend fun isertLocalUsersList(usersList: List<UserBO>)

    suspend fun updateLocalUser(user: UserBO)

}