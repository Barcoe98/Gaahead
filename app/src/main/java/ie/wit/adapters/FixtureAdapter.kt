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
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.card_fixture.view.*
import kotlinx.android.synthetic.main.fragment_fixture.view.*
import kotlinx.android.synthetic.main.login.*

interface FixtureListener {
    fun onFixtureClick(fixture: FixtureModel)
}

class FixtureAdapter constructor(var fixtures: ArrayList<FixtureModel>, private val listener: FixtureListener, fixtureall : Boolean)
    : RecyclerView.Adapter<FixtureAdapter.MainHolder>() {

    val fixtureAll = fixtureall


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.card_fixture,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val fixture = fixtures[holder.adapterPosition]
        holder.bind(fixture, listener, fixtureAll)
    }

    override fun getItemCount(): Int = fixtures.size

    fun removeAt(position: Int) {
        fixtures.removeAt(position)
        notifyItemRemoved(position)
    }

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(fixture: FixtureModel, listener: FixtureListener, fixtureAll: Boolean) {
            /////Card Text//////////////////////////model
            itemView.tag = fixture
            itemView.fTeamAName.text = fixture.teamAName
            itemView.fTeamBName.text = fixture.teamBName
            itemView.fDate.text = fixture.date
            itemView.fTime.text = fixture.time
            itemView.fLocation.text = fixture.location

            if(!fixtureAll)
                itemView.setOnClickListener { listener.onFixtureClick(fixture) }

            if(!fixture.logoA.isEmpty()) {
                Picasso.get().load(fixture.logoA.toUri())
                    //.resize(180, 180)
                    .transform(CropCircleTransformation())
                    .into(itemView.fTeamALogo)
            }

            else if(!fixture.logoB.isEmpty()) {
                Picasso.get().load(fixture.logoB.toUri())
                    //.resize(180, 180)
                    .transform(CropCircleTransformation())
                    .into(itemView.fTeamBLogo)

            }
            else
                itemView.fTeamALogo.setImageResource(R.mipmap.ic_app_icon_round)
                itemView.fTeamBLogo.setImageResource(R.mipmap.ic_app_icon_round)
        }
        }
    }
