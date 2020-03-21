package ie.wit.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize

@IgnoreExtraProperties
@Parcelize
data class FixtureModel(var uid: String? = "",
                        var logoA: String = "",
                        var logoB: String = "",
                        var teamAName: String = "Kilkenny",
                        var teamBName: String = "carlow",
                        var date: String = "10/10/10",
                        var time: String = "10:00",
                        var location: String = "Nolan Park",
                        var email: String? = "joe@bloggs.com") : Parcelable

{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "logoA" to logoA,
            "logoB" to logoB,
            "teamAName" to teamAName,
            "teamBName" to teamBName,
            "date" to date,
            "time" to time,
            "location" to location,
            "email" to email
        )
    }
}