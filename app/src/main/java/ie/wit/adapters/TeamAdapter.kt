package ie.wit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ie.wit.R
import ie.wit.models.TeamModel
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.card_team.view.*

interface TeamListener {
    fun onTeamClick(team: TeamModel)
}

class TeamAdapter constructor(var teams: ArrayList<TeamModel>, private val listener: TeamListener, teamall : Boolean)
    : RecyclerView.Adapter<TeamAdapter.MainHolder>() {

    val teamAll = teamall

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.card_team,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val team = teams[holder.adapterPosition]
        holder.bind(team, listener, teamAll)
    }

    override fun getItemCount(): Int = teams.size

    fun removeAt(position: Int) {
        teams.removeAt(position)
        notifyItemRemoved(position)
    }

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(team: TeamModel, listener: TeamListener, teamAll: Boolean) {
            /////Card Text//////////////////////////model
            itemView.tag = team
            itemView.tName.text = team.name
            itemView.tCounty.text = team.county
            itemView.tColours.text = team.colours
            itemView.tLocation.text = team.location
            itemView.tYearFounded.text = team.year_founded
            itemView.tDivision.text = team.division

            if(!teamAll)
                itemView.setOnClickListener { listener.onTeamClick(team) }

            if(!team.logo.isEmpty()) {
                Picasso.get().load(team.logo.toUri())
                    //.resize(180, 180)
                    .transform(CropCircleTransformation())
                    .into(itemView.tLogo)
            }
            else
                itemView.tLogo.setImageResource(R.mipmap.ic_app_icon_round)
                itemView.tLogo.setImageResource(R.mipmap.ic_app_icon_round)
        }
        }
    }
