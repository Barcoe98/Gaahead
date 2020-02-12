package ie.wit.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResultModel(var rId: Long = 0,
                       var teamAScore: String = "",
                       var teamAName: String = "",
                       var teamBScore: String = "",
                       var teamBName: String = "",
                       var type: String = "") : Parcelable