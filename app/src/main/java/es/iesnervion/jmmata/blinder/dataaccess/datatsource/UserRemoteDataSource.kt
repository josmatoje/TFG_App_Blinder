package es.iesnervion.jmmata.blinder.dataaccess.datatsource

import es.iesnervion.jmmata.blinder.businessObject.UserBO

interface UserRemoteDataSource {

    suspend fun getRemoteUsers(): List<UserBO>

    suspend fun insertRemoteUser(email: String, password: String, user: UserBO)

    suspend fun updateRemoteUser(user: UserBO)

    suspend fun getRemoteUsersFilterBy(likes: List<String>?)
}