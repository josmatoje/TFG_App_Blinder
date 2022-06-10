package es.iesnervion.jmmata.blinder.dataaccess.remote.firebase.dto

//Modelo Data Transfer Object, para recibir los datos de la bbdd de firebase
data class UserMatchDTO(
    val friendName: String? = null,
    @field:JvmField
    val isMatch: Boolean? = null
)