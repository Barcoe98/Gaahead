package ie.wit.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize

@IgnoreExtraProperties
@Parcelize
data class PlayerModel(var uid: String? = "",
                       var playerImage: String = "",
                       var playerName: String = "Frank Honest",
                       var playerAge: String = "25",
                       var playerHeight: String = "192Cm",
                       var playerWeight: String = "72Kg",
                       var playerPosition: String = "Midfield",
                       var email: String? = "joe@bloggs.com") : Parcelable

{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "playerImage" to playerImage,
            "playerName" to playerName,
            "playerAge" to playerAge,
            "playerHeight" to playerHeight,
            "playerWeight" to playerWeight,
            "playerPosition" to playerPosition,
            "email" to email
        )
    }
}