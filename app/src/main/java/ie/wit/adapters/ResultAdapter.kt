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
import ie.wit.fragments.resultFragments.ResultAllFragment
import ie.wit.models.ResultModel
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.card_result.view.*

interface ResultListener {
    fun onResultClick(result: ResultModel)
}

class ResultAdapter(options: FirebaseRecyclerOptions<ResultModel>,
                      private val listener: ResultListener?)
    : FirebaseRecyclerAdapter<ResultModel,
        ResultAdapter.ResultViewHolder>(options) {

    class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(result: ResultModel, listener: ResultListener) {
                with(result){

                    /////Card Text//////////////////////////model itemView.tag = result
                    itemView.rTeamAName.text = result.teamAName
                    itemView.rTeamAScore.text = result.teamAScore
                    itemView.rTeamBName.text = result.teamBName
                    itemView.rTeamBScore.text = result.teamBScore
                    itemView.rDate.text = result.date
                    itemView.rCompetition.text = result.competition

                    if(listener is ResultAllFragment)
                        ; // Do Nothing, Don't Allow 'Clickable' Rows
                    else
                        itemView.setOnClickListener { listener.onResultClick(result) }

                    if (!result.teamALogo.isEmpty()) {
                        Picasso.get().load(result.teamALogo.toUri())
                            //.resize(180, 180)
                            .transform(CropCircleTransformation())
                            .into(itemView.rTeamALogo)
                    } else if (!result.teamBLogo.isEmpty()) {
                        Picasso.get().load(result.teamBLogo.toUri())
                            //.resize(180, 180)
                            .transform(CropCircleTransformation())
                            .into(itemView.rTeamBLogo)

                    } else
                        itemView.rTeamALogo.setImageResource(R.mipmap.ic_app_icon_round)
                    itemView.rTeamBLogo.setImageResource(R.mipmap.ic_app_icon_round)
                }
            }
        }


            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
                return ResultViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.card_result, parent, false)
                )
            }

            override fun onBindViewHolder(holder: ResultViewHolder, position: Int, model: ResultModel) {
                holder.bind(model, listener!!)
            }

            override fun onDataChanged() {
                // Called each time there is a new data snapshot. You may want to use this method
                // to hide a loading spinner or check for the "no documents" state and update your UI.
                // ...
            }
        }

