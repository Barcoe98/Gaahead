package ie.wit.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FixtureModel(var fId: Long = 0,
                        var teamAName: String = "",
                        var teamBName: String = "",
                        var date: String = "",
                        var time: String = "",
                        var location: String = "") : Parcelable