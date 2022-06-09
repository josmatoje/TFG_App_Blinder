package es.iesnervion.jmmata.blinder.businessObject

data class UserMatchBO (
    var id: String?,
    val friendName: String? = "",
    val isMatch: Boolean? = false
)