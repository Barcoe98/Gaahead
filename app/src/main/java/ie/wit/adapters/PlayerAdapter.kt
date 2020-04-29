package ie.wit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ie.wit.R
import ie.wit.models.PlayerModel
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.card_player.view.*
import kotlinx.android.synthetic.main.fragment_player.view.*

interface PlayerListener {
    fun onPlayerClick(player: PlayerModel)
}

class PlayerAdapter constructor(var players: ArrayList<PlayerModel>, private val listener: PlayerListener, playerall : Boolean)
    : RecyclerView.Adapter<PlayerAdapter.MainHolder>() {

    val playerAll = playerall


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.card_player,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val player = players[holder.adapterPosition]
        holder.bind(player, listener, playerAll)
    }

    override fun getItemCount(): Int = players.size

    fun removeAt(position: Int) {
        players.removeAt(position)
        notifyItemRemoved(position)
    }

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(player: PlayerModel, listener: PlayerListener, playerAll: Boolean) {

            /////Card Text//////////////////////////model
            //itemView.tag = player
            itemView.pName.text = player.playerName
            //itemView..text = player.playerImage
            itemView.pAge.text = player.playerAge
            //itemView.rTeamBName.text = player.height
            //itemView.rTeamBScore.text = player.weight
            itemView.pPosition.text = player.playerPosition

            if(!playerAll)
                itemView.setOnClickListener { listener.onPlayerClick(player) }

            if(!player.playerImage.isEmpty()) {
                Picasso.get().load(player.playerImage.toUri())
                    //.resize(180, 180)
                    .transform(CropCircleTransformation())
                    .into(itemView.pImage)
            }
            else
                itemView.pImage.setImageResource(R.mipmap.ic_app_icon_round)
        }
        }
    }
