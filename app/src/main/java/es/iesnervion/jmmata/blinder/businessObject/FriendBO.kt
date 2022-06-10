package es.iesnervion.jmmata.blinder.businessObject

import java.util.ArrayList

data class FriendBO (
    var id: String? = null,
    val friendName: String = "",
    val likes: List<String> = ArrayList()
)