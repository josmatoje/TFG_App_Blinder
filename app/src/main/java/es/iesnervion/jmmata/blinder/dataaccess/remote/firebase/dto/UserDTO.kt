package es.iesnervion.jmmata.blinder.dataaccess.remote.firebase.dto

import com.google.firebase.firestore.GeoPoint
import java.util.*

data class UserDTO(
    val id: String,
    val userName: String,
    val city: String?,
    val description: String?,
    val birthdate: Date?,
    val ubication: GeoPoint,
    val friends: List<String>?,
    val likes: List<String>?
)