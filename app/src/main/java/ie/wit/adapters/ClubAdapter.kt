package ie.wit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.squareup.picasso.Picasso
import ie.wit.R
import ie.wit.models.ClubModel
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.card_club.view.*

interface ClubListener {
    fun onClubClick(club: ClubModel)
}

class ClubAdapter(options: FirebaseRecyclerOptions<ClubModel>, private val listener: ClubListener?) : FirebaseRecyclerAdapter<ClubModel, ClubAdapter.ClubViewHolder>(options) {


    class ClubViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(club: ClubModel, listener: ClubListener) {
            with (club) {

                itemView.tag = club
                itemView.tName.text = club.name
                //itemView.tCounty.text = club.county
                // itemView.tColours.text = club.colours
                //itemView.tLocation.text = club.location
                //itemView.tYearFounded.text = club.year_founded
                //itemView.tDivision.text = club.division

                itemView.setOnClickListener { listener.onClubClick(club) }

                if(!club.logo.isEmpty()) {
                    Picasso.get().load(club.logo.toUri())
                        //.resize(180, 180)
                        .transform(CropCircleTransformation())
                        .into(itemView.tLogo)
                }
                else
                    itemView.tLogo.setImageResource(R.mipmap.ic_app_icon_round)
            }

            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubViewHolder {

        return ClubViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_club, parent, false))
    }

    override fun onBindViewHolder(holder: ClubViewHolder, position: Int, model: ClubModel) {
        holder.bind(model,listener!!)
    }

    override fun onDataChanged() {
        // Called each time there is a new data snapshot. You may want to use this method
        // to hide a loading spinner or check for the "no documents" state and update your UI.
        // ...
    }
}
