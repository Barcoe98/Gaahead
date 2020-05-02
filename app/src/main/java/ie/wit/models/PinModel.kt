package ie.wit.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize

@IgnoreExtraProperties
@Parcelize
data class PinModel(var uid: String? = "",
                    var title: String = "",
                    var comment: String = "",
                    var profilePic: String = "",
                    var isPinned: Boolean = false,
                    var latitude: Double = 0.0,
                    var longitude: Double = 0.0,
                    var email: String? = "joe@bloggs.com") : Parcelable

{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "title" to title,
            "comment" to comment,
            "profilePic" to profilePic,
            "longitude" to longitude,
            "latitude" to latitude,
            "isPinned" to isPinned,
            "email" to email
        )
    }
}