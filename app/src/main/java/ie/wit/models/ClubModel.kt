package ie.wit.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize

@IgnoreExtraProperties
@Parcelize
data class ClubModel(var uid: String? = "",
                     var logo: String = "",
                     var name: String = "Blacks & Whites",
                     var county: String = "Kilkenny",
                     var colours: String = "Black & White",
                     var yearFounded: String = "1974",
                     var division: String = "Junior",
                     var grounds: String = "Tom Walsh Park") : Parcelable

{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "logo" to logo,
            "name" to name,
            "county" to county,
            "colours" to colours,
            "yearFounded" to yearFounded,
            "division" to division,
            "grounds" to grounds
        )
    }
}