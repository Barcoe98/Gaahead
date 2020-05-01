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
import ie.wit.fragments.playerFragments.PlayerAllFragment
import ie.wit.models.PlayerModel
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.card_player.view.*
import kotlinx.android.synthetic.main.fragment_player.view.*

interface PlayerListener {
    fun onPlayerClick(player: PlayerModel)
}

class PlayerAdapter (options: FirebaseRecyclerOptions<PlayerModel>, private val listener: PlayerListener?) : FirebaseRecyclerAdapter<PlayerModel, PlayerAdapter.PlayerViewHolder>(options) {

    class PlayerViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(player: PlayerModel, listener: PlayerListener) {
            with(player) {

                /////Card Text//////////////////////////model
                itemView.tag = player
                itemView.pName.text = player.playerName
                itemView.pAge.text = player.playerAge
                itemView.pPosition.text = player.playerPosition

                if(listener is PlayerAllFragment)
                else
                    itemView.setOnClickListener { listener.onPlayerClick(player) }
                if (!player.playerImage.isEmpty()) {
                    Picasso.get().load(player.playerImage.toUri())
                        //.resize(180, 180)
                        .transform(CropCircleTransformation())
                        .into(itemView.pImage)
                } else
                    itemView.pImage.setImageResource(R.mipmap.ic_app_icon_round)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {

        return PlayerViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_player, parent, false))
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int, model: PlayerModel) {
        holder.bind(model,listener!!)
    }

    override fun onDataChanged() {
    }
}
