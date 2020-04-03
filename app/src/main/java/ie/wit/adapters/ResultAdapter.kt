package ie.wit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ie.wit.R
import ie.wit.models.FixtureModel
import ie.wit.models.ResultModel
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.card_fixture.view.*
import kotlinx.android.synthetic.main.card_fixture.view.fDate
import kotlinx.android.synthetic.main.card_result.view.*
import kotlinx.android.synthetic.main.fragment_fixture.view.*
import kotlinx.android.synthetic.main.login.*

interface ResultListener {
    fun onResultClick(result: ResultModel)
}

class ResultAdapter constructor(var results: ArrayList<ResultModel>, private val listener: ResultListener, resultall : Boolean)
    : RecyclerView.Adapter<ResultAdapter.MainHolder>() {

    val resultAll = resultall


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.card_result,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val result = results[holder.adapterPosition]
        holder.bind(result, listener, resultAll)
    }

    override fun getItemCount(): Int = results.size

    fun removeAt(position: Int) {
        results.removeAt(position)
        notifyItemRemoved(position)
    }

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(result: ResultModel, listener: ResultListener, resultAll: Boolean) {
            /////Card Text//////////////////////////model
            itemView.tag = result
            itemView.rTeamAName.text = result.teamAName
            itemView.rTeamAScore.text = result.teamAScore
            itemView.rTeamBName.text = result.teamBName
            itemView.rTeamBScore.text = result.teamBScore
            itemView.rDate.text = result.date
            itemView.rCompetition.text = result.competition

            if(!resultAll)
                itemView.setOnClickListener { listener.onResultClick(result) }

            if(!result.teamALogo.isEmpty()) {
                Picasso.get().load(result.teamALogo.toUri())
                    //.resize(180, 180)
                    .transform(CropCircleTransformation())
                    .into(itemView.rTeamALogo)
            }

            else if(!result.teamBLogo.isEmpty()) {
                Picasso.get().load(result.teamBLogo.toUri())
                    //.resize(180, 180)
                    .transform(CropCircleTransformation())
                    .into(itemView.rTeamBLogo)

            }
            else
                itemView.rTeamALogo.setImageResource(R.mipmap.ic_app_icon_round)
                itemView.rTeamBLogo.setImageResource(R.mipmap.ic_app_icon_round)
        }
        }
    }
