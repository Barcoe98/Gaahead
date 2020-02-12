package ie.wit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.wit.R
import ie.wit.models.ResultModel
import kotlinx.android.synthetic.main.card_result.view.*

class ResultAdapter constructor(private var results: List<ResultModel>)
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
        holder.bind(result)
    }

    override fun getItemCount(): Int = results.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(result: ResultModel) {
            /////cardview//////////////////////////model
            itemView.rTeamAName.text = result.teamAName
            itemView.rTeamBName.text = result.teamBName
            itemView.rTeamAScore.text = result.teamAScore
            itemView.rTeamBScore.text = result.teamBScore
            itemView.rSportType.text = result.type
        }
    }
}