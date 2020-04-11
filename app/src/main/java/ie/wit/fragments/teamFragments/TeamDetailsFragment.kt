package ie.wit.fragments.teamFragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import ie.wit.R
import ie.wit.main.MainApp
import ie.wit.models.TeamModel
import ie.wit.utils.createLoader
import kotlinx.android.synthetic.main.fragment_team_details.view.*

class TeamDetailsFragment : Fragment() {

    lateinit var app: MainApp
    lateinit var root: View
    lateinit var loader : AlertDialog
    var teamDetails: TeamModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp

        arguments?.let {
            teamDetails = it.getParcelable("teamdetails")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        root = inflater.inflate(R.layout.fragment_team_details, container, false)
        activity?.title = getString(R.string.action_edit)
        loader = createLoader(activity!!)

        root.tName.text = teamDetails!!.name
        root.tCounty.text = teamDetails!!.county
        root.tColours.text = teamDetails!!.colours
        root.tYearFounded.text = teamDetails!!.year_founded
        //root.tLocation.text = teamDetails!!.location
        //root.tDivision.text = teamDetails!!.division

        return root
    }


    companion object {
        @JvmStatic
        fun newInstance(team: TeamModel) =
            TeamDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("teamdetails",team)
                }
            }
    }
}