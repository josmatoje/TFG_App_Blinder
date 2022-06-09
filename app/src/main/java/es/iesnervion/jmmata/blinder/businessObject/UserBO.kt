package es.iesnervion.jmmata.blinder.businessObject

import android.location.Location
import com.google.firebase.firestore.DocumentReference
import java.util.*

data class UserBO (
    var id: String? = null,
    val name: String = "",
    val city: String = "",
    val description: String = "",
    val birthdate: Date = Date(),
    val location: Location = Location(""),
    val likes: List<String> = ArrayList(),
    val sexuality: String = "",
    val gender: String = "",
    val friends: List<UserMatchBO> = ArrayList()
)