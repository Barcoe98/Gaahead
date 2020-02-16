package ie.wit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.wit.R
import ie.wit.models.FixtureModel
import ie.wit.models.ResultModel
import kotlinx.android.synthetic.main.card_result.view.*


interface ResultListener {
    fun onResultClick(result: ResultModel)
}

class ResultAdapter constructor(private var results: List<ResultModel>,
                                private val listener: ResultListener)
    : RecyclerView.Adapter<ResultAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_result,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val result = results[holder.adapterPosition]
        holder.bind(result, listener)
    }

    override fun getItemCount(): Int = results.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(result: ResultModel, listener: ResultListener) {
            /////cardview//////////////////////////model
            itemView.rTeamAName.text = result.teamAName
            itemView.rTeamBName.text = result.teamBName
            itemView.rTeamAScore.text = result.teamAScore
            itemView.rTeamBScore.text = result.teamBScore
            itemView.rSportType.text = result.type
            itemView.setOnClickListener {
                listener.onResultClick(result)
            }

        }
    }
}