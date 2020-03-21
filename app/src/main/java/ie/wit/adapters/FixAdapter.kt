package ie.wit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.wit.R
import ie.wit.models.FixtureModel
import kotlinx.android.synthetic.main.card_fixture.view.*

interface FixListener {
    fun onFixClick(fixture: FixtureModel)
}

class FixAdapter constructor(var fixtures: ArrayList<FixtureModel>,  private val listener: FixListener)
    : RecyclerView.Adapter<FixAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_fixture,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val fixture = fixtures[holder.adapterPosition]
        holder.bind(fixture, listener)
    }

    override fun getItemCount(): Int = fixtures.size

    fun removeAt(position: Int) {
        fixtures.removeAt(position)
        notifyItemRemoved(position)
    }

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(fixture: FixtureModel, listener: FixListener) {
            /////Card Text//////////////////////////model
            itemView.fTeamAName.text = fixture.teamAName
            itemView.fTeamBName.text = fixture.teamBName
            itemView.fDate.text = fixture.date
            itemView.fTime.text = fixture.time
            itemView.fLocation.text = fixture.location
            itemView.setOnClickListener { listener.onFixClick(fixture) }

        }
    }
}