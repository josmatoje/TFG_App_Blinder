package es.iesnervion.jmmata.blinder.dataaccess.local.room.datasourceImplementation

import android.content.Context
import es.iesnervion.jmmata.blinder.businessObject.UserBO
import es.iesnervion.jmmata.blinder.dataaccess.datatsource.UserLocalDataSource
import es.iesnervion.jmmata.blinder.dataaccess.local.room.database.UserDatabase
import es.iesnervion.jmmata.blinder.dataaccess.local.toUserBO
import es.iesnervion.jmmata.blinder.dataaccess.local.toUserDBO

class UserLocalDataSourceImpl(context: Context): UserLocalDataSource {

    private val database: UserDatabase = UserDatabase.getInstance(context)
    private val userDao = database.userDao()

    override suspend fun getLocalUsers(): List<UserBO> =
        userDao.getUsers().map { it.toUserBO() }

    override suspend fun getLocalUser(id: String): UserBO =
        userDao.getUserBy(id).toUserBO()

    override suspend fun insertLocalUsersList(usersList: List<UserBO>) =
        userDao.insertUsersList(usersList.map { it.toUserDBO() })

    override suspend fun insertLocalUser(user: UserBO) {
        userDao.insertUser(user.toUserDBO())
    }

    override suspend fun deleteLocalUser(id: String) =
        userDao.deleteUser(id)

    override suspend fun deleteAllLocalUser() =
        userDao.deleteAllUser()

}