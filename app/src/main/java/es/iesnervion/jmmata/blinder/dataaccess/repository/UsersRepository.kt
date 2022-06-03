package es.iesnervion.jmmata.blinder.dataaccess.repository

import android.widget.Toast
import com.google.firebase.firestore.GeoPoint
import es.iesnervion.jmmata.blinder.businessObject.UserBO
import es.iesnervion.jmmata.blinder.dataaccess.datatsource.UserLocalDataSource
import es.iesnervion.jmmata.blinder.dataaccess.datatsource.UserRemoteDataSource
import es.iesnervion.jmmata.blinder.dataaccess.remote.firebase.dto.UserDTO

class UsersRepository(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource
) {

    suspend fun getUsers(): List<UserBO> {
        var users = userLocalDataSource.getLocalUsers()
        if (users.isEmpty()) {
            users = userRemoteDataSource.getRemoteUsers()
            userLocalDataSource.isertLocalUsersList(users)
        }
        return users
    }

    suspend fun getUserById(id: String): UserBO {
        return userLocalDataSource.getLocalUser(id)
    }

    suspend fun insertUser(email: String, password: String, user: UserBO) {

    }

}