package es.iesnervion.jmmata.blinder.dataaccess.local.room.dao

import androidx.room.*
import es.iesnervion.jmmata.blinder.dataaccess.local.room.dbo.UserDBO

@Dao
interface UserDao {

    @Transaction
    @Query("SELECT * FROM users")
    suspend fun getUsers(): List<UserDBO>

    @Transaction
    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserBy(userId: String): UserDBO

    @Transaction
    @Query("SELECT friends FROM users WHERE id = :userId")
    suspend fun getUsersFriendsFrom(userId: String): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsersList(userList : List<UserDBO>)

    @Update
    suspend fun updateUser(user: UserDBO)
}