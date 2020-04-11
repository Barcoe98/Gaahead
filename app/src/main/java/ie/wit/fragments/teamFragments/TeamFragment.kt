package ie.wit.fragments.teamFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import ie.wit.R
import ie.wit.main.MainApp
import ie.wit.models.TeamModel
import ie.wit.utils.*
import kotlinx.android.synthetic.main.fragment_team.*
import kotlinx.android.synthetic.main.fragment_team.view.*

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.util.HashMap

open class TeamFragment : Fragment(), AnkoLogger {

    lateinit var app: MainApp
    lateinit var loader: AlertDialog
    val IMAGE_REQUEST = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_team, container, false)
        loader = createLoader(activity!!)
        activity?.title = getString(R.string.team_title)

        setButtonListener(root)
        setImgBtnListener(root)

        return root
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            TeamFragment().apply {
                arguments = Bundle().apply {}
            }
    }


    private fun setImgBtnListener(layout: View) {

        layout.logoBtn.setOnClickListener {
            showImagePickerFragment(this, IMAGE_REQUEST)
        }
    }


    private fun setButtonListener(layout: View) {
        layout.addTeamBtn.setOnClickListener {

            val name = name.text.toString()
            val county = county.text.toString()
            val colours = colours.text.toString()
            val division = division.text.toString()
            val location = location.text.toString()
            val yearFounded = year_founded.text.toString()

            when {
                layout.name.text.isEmpty() -> Toast.makeText(
                    app,
                    R.string.error_teamAName,
                    Toast.LENGTH_LONG
                ).show()
                layout.county.text.isEmpty() -> Toast.makeText(
                    app,
                    R.string.error_teamBName,
                    Toast.LENGTH_LONG
                ).show()
                layout.colours.text.isEmpty() -> Toast.makeText(
                    app,
                    R.string.error_date,
                    Toast.LENGTH_LONG
                ).show()
                layout.location.text.isEmpty() -> Toast.makeText(
                    app,
                    R.string.error_time,
                    Toast.LENGTH_LONG
                ).show()
                layout.division.text.isEmpty() -> Toast.makeText(
                    app,
                    R.string.error_time,
                    Toast.LENGTH_LONG
                ).show()
                layout.year_founded.text.isEmpty() -> Toast.makeText(
                    app,
                    R.string.error_competition,
                    Toast.LENGTH_LONG
                ).show()
                else -> writeNewTeam(
                    TeamModel(
                        name = name,
                        county = county,
                        colours = colours,
                        location = location,
                        division = division,
                        year_founded = yearFounded
                    )
                )
            }
            layout.name.setText("")
            layout.county.setText("")
            layout.colours.setText("")
            layout.location.setText("")
            layout.division.setText("")
            layout.year_founded.setText("")

        }
    }


    override fun onPause() {
        super.onPause()
        app.database.child("results")
        //.removeEventListener(eventListener)
    }

    fun writeNewTeam(team: TeamModel) {

        // Create new team at /teams
        showLoader(loader, "Adding team to Firebase")
        info("Firebase DB Reference : $app.database")
        val key = app.database.child("teams").push().key
        if (key == null) {
            info("Firebase Error : Key Empty")
            return
        }
        team.uid = key
        val teamValues = team.toMap()

        val childUpdates = HashMap<String, Any>()
        childUpdates["/teams/$key"] = teamValues

        app.database.updateChildren(childUpdates)
        hideLoader(loader)
    }
}
