package es.iesnervion.jmmata.blinder.dataaccess.local.room.datasourceImplementation

import android.content.Context
import es.iesnervion.jmmata.blinder.businessObject.UserBO
import es.iesnervion.jmmata.blinder.dataaccess.datatsource.UserLocalDataSource
import es.iesnervion.jmmata.blinder.dataaccess.local.room.database.UserDatabase
import es.iesnervion.jmmata.blinder.dataaccess.local.room.dbo.UserDBO
import es.iesnervion.jmmata.blinder.dataaccess.local.toUserBO
import es.iesnervion.jmmata.blinder.dataaccess.local.toUserDBO

class UserLocalDataSourceImpl(context: Context): UserLocalDataSource {

    private val database: UserDatabase = UserDatabase.getInstance(context)
    private val userDao = database.userDao()

    override suspend fun getLocalUsers(): List<UserBO> =
        userDao.getUsers().map { it.toUserBO() }

    override suspend fun getLocalUser(id: String): UserBO =
        userDao.getUserBy(id).toUserBO()

    override suspend fun getLocalUserFriendsFrom(id: String): List<UserBO> {
        var friends = userDao.getUsersFriendsFrom(id)
        val listOfFriends: MutableList<UserBO> = arrayListOf()
        for (userId in friends) {
            listOfFriends.add(getLocalUser(userId))
        }
        return listOfFriends
    }


    override suspend fun isertLocalUsersList(usersList: List<UserBO>) =
        userDao.insertUsersList(usersList.map { it.toUserDBO() })

    override suspend fun updateLocalUser(user: UserBO) =
        userDao.updateUser(user.toUserDBO())

}