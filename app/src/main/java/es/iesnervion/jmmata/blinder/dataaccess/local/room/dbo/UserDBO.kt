package es.iesnervion.jmmata.blinder.dataaccess.local.room.dbo

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import es.iesnervion.jmmata.blinder.dataaccess.converters.RoomDateConverter
import java.util.*
//Objeto DataBase Object, para manejar los datos de la bbdd local (Room)
@Entity(tableName = "users")
data class UserDBO (
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val city: String?,
    val description: String?,
    val birthdate: Date?,
    val latitude: Double?,
    val longitude : Double?,
    val likes: List<String>?,
    val sexuality: String?,
    val gender: String?
)
