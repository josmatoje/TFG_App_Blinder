package es.iesnervion.jmmata.blinder.dataaccess.local.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import es.iesnervion.jmmata.blinder.dataaccess.converters.RoomDateConverter
import es.iesnervion.jmmata.blinder.dataaccess.converters.RoomListConverter
import es.iesnervion.jmmata.blinder.dataaccess.local.room.dao.UserDao
import es.iesnervion.jmmata.blinder.dataaccess.local.room.dbo.UserDBO

@Database( entities = [
    UserDBO::class],
    exportSchema = false,
    version = 4)
@TypeConverters(RoomDateConverter::class, RoomListConverter::class)
abstract class UserDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object{

        private var USERDATABASE: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase {
            return USERDATABASE ?: synchronized(UserDatabase::class) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java, "User.db")
                    .build()
                USERDATABASE = instance
                instance
            }
        }
    }
}
