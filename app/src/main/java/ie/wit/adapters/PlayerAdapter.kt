package ie.wit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.wit.R
import ie.wit.models.FixtureModel
import ie.wit.models.PlayerModel
import ie.wit.models.ResultModel
import kotlinx.android.synthetic.main.card_player.view.*
import kotlinx.android.synthetic.main.card_result.view.*


interface PlayerListener {
    fun onPlayerClick(player: PlayerModel)
}

class PlayerAdapter constructor(private var players: List<PlayerModel>,
                                private val listener: PlayerListener)
    : RecyclerView.Adapter<PlayerAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_player,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val player = players[holder.adapterPosition]
        holder.bind(player, listener)
    }

    override fun getItemCount(): Int = players.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(player: PlayerModel, listener: PlayerListener) {
            /////cardview//////////////////////////model
            itemView.pName.text = player.playerName
            itemView.setOnClickListener {
                listener.onPlayerClick(player)
            }

        }
    }

}