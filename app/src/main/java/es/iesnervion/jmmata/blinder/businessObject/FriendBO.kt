package es.iesnervion.jmmata.blinder.businessObject

data class FriendBO (
    var id: String?,
    val friendName: String = "",
    val isMatch: Boolean = false
)