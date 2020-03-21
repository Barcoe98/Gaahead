package ie.wit.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FixtureModel(var _id: String = "N/A",
                        var logoA: String = "",
                        var logoB: String = "",
                        var teamAName: String = "",
                        var teamBName: String = "",
                        var date: String = "",
                        var time: String = "",
                        var location: String = "") : Parcelable