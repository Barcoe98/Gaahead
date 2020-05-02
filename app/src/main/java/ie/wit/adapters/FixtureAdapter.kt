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
import ie.wit.fragments.fixtureFragments.FixtureAllFragment
import ie.wit.models.FixtureModel
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.card_fixture.view.*

interface FixtureListener {
    fun onFixtureClick(fixture: FixtureModel)
}

class FixtureAdapter(options: FirebaseRecyclerOptions<FixtureModel>,
                     private val listener: FixtureListener?)
    : FirebaseRecyclerAdapter<FixtureModel,
        FixtureAdapter.FixtureViewHolder>(options) {


    class FixtureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(fixture: FixtureModel, listener: FixtureListener) {
            with(fixture) {
                itemView.tag = fixture
                itemView.fTeamAName.text = fixture.teamAName
                itemView.fTeamBName.text = fixture.teamBName
                itemView.fDate.text = fixture.date
                itemView.fTime.text = fixture.time
                itemView.fLocation.text = fixture.location


                if (listener is FixtureAllFragment)
                    ; // Do Nothing, Don't Allow 'Clickable' Rows
                else
                    itemView.setOnClickListener { listener.onFixtureClick(fixture) }

                if(fixture.isfavourite) itemView.imagefavouriteStar.setImageResource(R.drawable.ic_bookmark_gold)

                if (!fixture.logoA.isEmpty()) {
                    Picasso.get().load(fixture.logoA.toUri())
                        //.resize(180, 180)
                        .transform(CropCircleTransformation())
                        .into(itemView.fTeamALogo)
                } else if (!fixture.logoB.isEmpty()) {
                    Picasso.get().load(fixture.logoB.toUri())
                        //.resize(180, 180)
                        .transform(CropCircleTransformation())
                        .into(itemView.fTeamBLogo)

                } else
                    itemView.fTeamALogo.setImageResource(R.mipmap.ic_app_icon_round)
                itemView.fTeamBLogo.setImageResource(R.mipmap.ic_app_icon_round)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FixtureViewHolder {

        return FixtureViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_fixture, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FixtureViewHolder, position: Int, model: FixtureModel) {
        holder.bind(model, listener!!)
    }

    override fun onDataChanged() {
        // Called each time there is a new data snapshot. You may want to use this method
        // to hide a loading spinner or check for the "no documents" state and update your UI.
        // ...
    }
}