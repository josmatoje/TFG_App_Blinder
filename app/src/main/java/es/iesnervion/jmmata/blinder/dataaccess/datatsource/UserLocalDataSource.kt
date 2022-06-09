package es.iesnervion.jmmata.blinder.dataaccess.datatsource

import es.iesnervion.jmmata.blinder.businessObject.UserBO

interface UserLocalDataSource {

    suspend fun getLocalUsers(): List<UserBO>

    suspend fun getLocalUser(id: String): UserBO

    suspend fun insertLocalUsersList(usersList: List<UserBO>)

    suspend fun insertLocalUser(user: UserBO)

    suspend fun deleteLocalUser (id: String)

    suspend fun deleteAllLocalUser()
}