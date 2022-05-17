package es.iesnervion.jmmata.blinder.dataaccess.room.database

import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import es.iesnervion.jmmata.blinder.dataaccess.converters.RoomDateConverter
import es.iesnervion.jmmata.blinder.dataaccess.room.dao.UserDao
import es.iesnervion.jmmata.blinder.dataaccess.room.dao.UsersMatchDao
import es.iesnervion.jmmata.blinder.dataaccess.room.model.User
import es.iesnervion.jmmata.blinder.dataaccess.room.model.relations.UserCrossRef

@Database( entities = [
    User::class,
    UserCrossRef::class],
    exportSchema = false,
    version = 2)
@TypeConverters(RoomDateConverter::class)
abstract class UserDatabase: RoomDatabase() {
    abstract val userDao: UserDao
    abstract val usersMatchDao: UsersMatchDao

    companion object{
        private var USERDATABASE: UserDatabase? = null
        fun getInstance(context: Context): UserDatabase {
            return USERDATABASE ?: synchronized(UserDatabase::class) {
                val instance = Room.databaseBuilder(context.applicationContext,
                    UserDatabase::class.java, "User.db")
                    .addTypeConverter(RoomDateConverter()) //TODO: preguntar si necesito una variable o asi queda instanciado
                    .build()
                USERDATABASE = instance
                instance
            }
        }
    }
}
