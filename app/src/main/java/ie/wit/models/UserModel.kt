package ie.wit.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize

@IgnoreExtraProperties
@Parcelize
data class UserModel(var uid: String? = "",
                     var profilepic: String = "",
                     var email: String? = "bob@bob.com",
                     var name: String = "",
                     var age: String = "",
                     var trophiesWon: String = "",
                     var userType: String = "") : Parcelable

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