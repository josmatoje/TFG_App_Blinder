package es.iesnervion.jmmata.blinder.dataaccess.remote

import android.location.Location
import com.google.firebase.firestore.GeoPoint
import es.iesnervion.jmmata.blinder.businessObject.UserBO
import es.iesnervion.jmmata.blinder.dataaccess.local.room.dbo.UserDBO
import es.iesnervion.jmmata.blinder.dataaccess.remote.firebase.dto.UserDTO
import java.util.*

fun UserDTO.toUserBO(): UserBO {
    val ubicationProvider =  Location("provider")
    ubicationProvider.latitude = ubication.latitude
    ubicationProvider.longitude = ubication.longitude
    return UserBO(
        id,
        userName,
        city ?: "",
        description ?: "",
        birthdate ?: Date(),
        ubicationProvider,
        likes ?: listOf(),
        friends ?: listOf()
    )
}


fun UserBO.toUserDTO(): UserDTO =
    UserDTO(
        id,
        name,
        city,
        description,
        birthdate,
        GeoPoint(ubication.latitude, ubication.longitude),
        likes,
        friends
    )