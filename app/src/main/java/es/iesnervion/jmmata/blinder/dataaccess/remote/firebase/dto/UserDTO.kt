package es.iesnervion.jmmata.blinder.dataaccess.remote.firebase.dto

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.GeoPoint
import java.util.*
import kotlin.collections.HashMap

data class UserDTO(
    val userName: String? = null,
    val city: String? = null,
    val description: String? = null,
    val birthdate: Date? = null,
    val ubication: GeoPoint? = null,
    val likes: List<String>? = null,
    val sexuality: String? = null,
    val gender: String? = null
)
