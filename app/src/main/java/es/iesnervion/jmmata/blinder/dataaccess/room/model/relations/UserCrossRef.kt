package es.iesnervion.jmmata.blinder.dataaccess.room.model.relations

import androidx.room.Entity

@Entity(primaryKeys = ["userSendMatch", "userReciveMatch"])
data class UserCrossRef (
    val userSendMatch: Int,
    val userReciveMatch: Int
)