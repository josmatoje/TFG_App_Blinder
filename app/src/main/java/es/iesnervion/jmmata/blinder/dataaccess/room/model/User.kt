package es.iesnervion.jmmata.blinder.dataaccess.room.model

import android.location.Location
import androidx.databinding.adapters.Converters
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import es.iesnervion.jmmata.blinder.dataaccess.converters.RoomDateConverter
import java.sql.Date

@Entity
data class User (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val password: String,
    @TypeConverters(RoomDateConverter::class)
    val birthDate: Date?,
    val ubication: String, //Estudiar Location
    val description: String,
    val gustos: String
)
