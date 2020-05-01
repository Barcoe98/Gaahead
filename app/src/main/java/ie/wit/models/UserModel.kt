package ie.wit.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize

@IgnoreExtraProperties
@Parcelize
data class UserModel(var uid: String? = "",
                     var profilepic: String = "",
                     var email: String? = "benfranklin@gmail.com",
                     var name: String = "Michael Barcoe",
                     var age: String = "Michael Barcoe",
                     var trophiesWon: String = "Michael Barcoe",
                     var userType: String = "Manager") : Parcelable

{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "profilepic" to profilepic,
            "name" to name,
            "age" to age,
            "trophiesWon" to trophiesWon,
            "email" to email,
            "userType" to userType)
    }
}