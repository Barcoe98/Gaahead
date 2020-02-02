package ie.wit.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResultModel(var rId: Long = 0,
                       var teamAName: String = "",
                       var teamBName: String = "") : Parcelable