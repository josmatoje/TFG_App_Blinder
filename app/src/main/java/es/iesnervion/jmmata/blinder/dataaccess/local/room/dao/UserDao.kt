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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsersList(userList : List<UserDBO>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userList : UserDBO)

    @Query("DELETE FROM users WHERE id = :userId")
    suspend fun deleteUser(userId: String)

    @Query("DELETE FROM users")
    suspend fun deleteAllUser()
}