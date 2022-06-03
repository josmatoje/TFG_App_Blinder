package es.iesnervion.jmmata.blinder.businessObject

import android.location.Location
import java.util.*

data class UserBO (
    val id: String = "",
    val name: String = "",
    val city: String = "",
    val description: String = "",
    val birthdate: Date = Date(),
    val ubication: Location = Location(""),
    val likes: List<String> = listOf(),
    val friends: List<String> = listOf()
)