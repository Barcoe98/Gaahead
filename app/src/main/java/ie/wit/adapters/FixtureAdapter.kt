package ie.wit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import ie.wit.R
import ie.wit.models.FixtureModel
import kotlinx.android.synthetic.main.card_fixture.view.*


class FixtureAdapter constructor(private var fixtures: List<FixtureModel>)
    : RecyclerView.Adapter<FixtureAdapter.MainHolder>(), Filterable {

    internal var filterListResult : List<FixtureModel>

    init {
        this.filterListResult = fixtures
    }

    override fun getFilter(): Filter {
        return object :Filter(){
            override fun performFiltering(charString: CharSequence?): FilterResults {
                val charSearch = charString.toString()
                if(charSearch.isEmpty()) {
                    filterListResult = fixtures
                }
                else
                {
                    val resultList = ArrayList<FixtureModel>()
                    for (row in fixtures)
                    {
                        if(row.teamAName.toLowerCase().contains(charSearch.toLowerCase())) {
                            resultList.add(row)
                        }
                    }
                    filterListResult = resultList
                }
                val filterResults = Filter.FilterResults()
                filterResults.values = filterListResult
                return filterResults


            }

            override fun publishResults(charSquence: CharSequence?, filterResults: FilterResults?) {
                if (filterResults != null) {
                    filterListResult = filterResults.values as List<FixtureModel>
                }
                notifyDataSetChanged()

            }

        }
    }



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
        holder.bind(fixture)
    }

    override fun getItemCount(): Int = fixtures.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(fixture: FixtureModel) {
            /////Card Text//////////////////////////model
            itemView.fTeamAName.text = fixture.teamAName
            itemView.fTeamBName.text = fixture.teamBName
            itemView.fDate.text = fixture.date
            itemView.fTime.text = fixture.time
            itemView.fLocation.text = fixture.location
        }
    }




}