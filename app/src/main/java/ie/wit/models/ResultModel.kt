package ie.wit.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize

@IgnoreExtraProperties
@Parcelize
data class ResultModel(var uid: String? = "",
                        var teamALogo: String = "",
                        var teamBLogo: String = "",
                        var teamAName: String = "Kilkenny",
                        var teamAScore: String = "5-28",
                        var teamBName: String = "carlow",
                        var teamBScore: String = "1-12",
                        var date: String = "Sun 21 Mar",
                        var competition: String = "Leinster Hurling League Final",
                        var email: String? = "joe@bloggs.com") : Parcelable

{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "teamALogo" to teamALogo,
            "teamBLogo" to teamBLogo,
            "teamAName" to teamAName,
            "teamAScore" to teamAScore,
            "teamBName" to teamBName,
            "teamBScore" to teamBScore,
            "date" to date,
            "competition" to competition,
            "email" to email
        )
    }
}