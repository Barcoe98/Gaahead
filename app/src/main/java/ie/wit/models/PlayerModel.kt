package ie.wit.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlayerModel(var playerId: Long = 0,
                       var playerName: String = "",
                       var playerAge: String = "",
                       var playerHeight: String = "",
                       var playerWeight: String = "",
                       var playerPosition: String = "") : Parcelable


