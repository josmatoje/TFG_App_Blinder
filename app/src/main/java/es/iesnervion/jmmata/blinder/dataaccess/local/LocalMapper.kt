package es.iesnervion.jmmata.blinder.dataaccess.local

import android.location.Location
import es.iesnervion.jmmata.blinder.businessObject.UserBO
import es.iesnervion.jmmata.blinder.dataaccess.local.room.dbo.UserDBO
import java.util.*

fun UserDBO.toUserBO(): UserBO {
    val ubicationProvider =  Location("provider")
    ubicationProvider.latitude = latitude ?: 0.0
    ubicationProvider.longitude = longitude ?: 0.0
    return UserBO(
        id,
        name,
        city ?: "",
        description ?: "",
        birthdate ?: Date(),
        ubicationProvider,
        likes ?: listOf(),
        friends ?: listOf()
    )
}


fun UserBO.toUserDBO(): UserDBO =
    UserDBO(
        id,
        name,
        city,
        description,
        birthdate,
        ubication.latitude,
        ubication.longitude,
        likes,
        friends
    )