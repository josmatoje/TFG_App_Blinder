package es.iesnervion.jmmata.blinder.dataaccess.remote

import android.location.Location
import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoLocation
import com.google.firebase.firestore.GeoPoint
import es.iesnervion.jmmata.blinder.businessObject.UserBO
import es.iesnervion.jmmata.blinder.businessObject.UserMatchBO
import es.iesnervion.jmmata.blinder.dataaccess.remote.firebase.dto.UserDTO
import es.iesnervion.jmmata.blinder.dataaccess.remote.firebase.dto.UserMatchDTO
import java.util.*
import kotlin.collections.HashMap
//Métodos para transformar de un objeto DTO a uno BO y viceversa. Facilitan la conversión de objetos de una misma esencia pero adaptando a sus peculiaridades

fun UserDTO.toUserBO(): UserBO {
    val ubicationProvider =  Location("provider")
    ubicationProvider.latitude = ubication?.latitude ?: 37.374183536705715
    ubicationProvider.longitude = ubication?.longitude ?: -5.9693604649348355

    return UserBO(
        null,
        userName ?: "",
        city ?: "",
        description ?: "",
        birthdate ?: Date(),
        ubicationProvider,
        likes ?: listOf(),
        sexuality ?: "",
        gender ?: ""
    )
}


fun UserBO.toUserDTO(): UserDTO {
    // Compute the GeoHash for a lat/lng point
    val lat = location.latitude
    val lng = location.longitude
    val hash = GeoFireUtils.getGeoHashForLocation(GeoLocation(lat, lng))

    val ubication: HashMap<String, Any> = hashMapOf(Pair("geohash", hash),Pair("lat", lat),Pair("lng", lng))
    return UserDTO(
        name,
        city,
        description,
        birthdate,
        GeoPoint(lat, lng),
        likes)
}

fun UserMatchDTO.toBO(): UserMatchBO =
    UserMatchBO(
        null,
        friendName,
        isMatch
    )

fun UserMatchBO.toDTO(): UserMatchDTO =
    UserMatchDTO(
        friendName,
        isMatch
    )
