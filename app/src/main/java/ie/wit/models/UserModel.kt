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
                     var name: String = "Ben Franklin",
                     var userType: String = "Manager") : Parcelable

{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "profilepic" to profilepic,
            "email" to email,
            //"name" to name,
            "userType" to userType)
    }
}