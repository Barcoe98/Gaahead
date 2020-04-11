package ie.wit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.wit.R
import ie.wit.models.ClubModel
import kotlinx.android.synthetic.main.card_club.view.*

interface ClubListener {
    fun onClubClick(club: ClubModel)
}

class ClubAdapter constructor(var clubs: ArrayList<ClubModel>, private val listener: ClubListener, cluball : Boolean)
    : RecyclerView.Adapter<ClubAdapter.MainHolder>() {

    val clubAll = cluball

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_club, parent, false))
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val club = clubs[holder.adapterPosition]
        holder.bind(club, listener, clubAll)
    }

    override fun getItemCount(): Int = clubs.size

    fun removeAt(position: Int) {
        clubs.removeAt(position)
        notifyItemRemoved(position)
    }

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(club: ClubModel, listener: ClubListener, clubAll: Boolean) {
            /////Card Text//////////////////////////model
            itemView.tag = club
            itemView.tName.text = club.name
            //itemView.tCounty.text = club.county
            // itemView.tColours.text = club.colours
            //itemView.tLocation.text = club.location
            //itemView.tYearFounded.text = club.year_founded
            //itemView.tDivision.text = club.division

            if (!clubAll)
                itemView.setOnClickListener { listener.onClubClick(club) }
/*
            if(!club.logo.isEmpty()) {
                Picasso.get().load(club.logo.toUri())
                    //.resize(180, 180)
                    .transform(CropCircleTransformation())
                    .into(itemView.tLogo)
            }
            else
                itemView.tLogo.setImageResource(R.mipmap.ic_app_icon_round)
                itemView.tLogo.setImageResource(R.mipmap.ic_app_icon_round)
        }

 */
        }
    }
}
