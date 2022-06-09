package es.iesnervion.jmmata.blinder.dataaccess.remote.firebase.dto

import com.google.firebase.firestore.DocumentReference

data class FriendDTO (
    val friendName: String? = "",
    @field:JvmField
    val isMatch: Boolean? = false
)