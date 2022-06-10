package es.iesnervion.jmmata.blinder.businessObject

import android.location.Location
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
    val friends: List<UserMatchBO> = ArrayList()) {

    override fun equals(obj: Any?): Boolean {
        var isEqual = false
        if (this === obj) {
            isEqual = true
        }else if (obj == null || javaClass != obj.javaClass) {
            isEqual = false
        }else if ((obj as UserBO).id.equals(this.id)) {
            isEqual = true
        }
        return isEqual
    }
}


