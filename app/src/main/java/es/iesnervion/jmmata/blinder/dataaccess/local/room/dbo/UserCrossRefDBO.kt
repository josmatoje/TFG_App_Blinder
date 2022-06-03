package es.iesnervion.jmmata.blinder.dataaccess.local.room.dbo

import androidx.room.Entity

@Entity(primaryKeys = ["userSendMatch", "userReciveMatch"])
data class UserCrossRefDBO (
    val userSendMatch: Int,
    val userReciveMatch: Int
)