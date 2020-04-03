package ie.wit.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import ie.wit.R
import ie.wit.activities.Home
import ie.wit.fragments.resultFragments.ResultFragment
import ie.wit.main.MainApp
import ie.wit.models.FixtureModel
import ie.wit.utils.createLoader
import kotlinx.android.synthetic.main.fragment_fixture_details.view.*
import kotlinx.android.synthetic.main.fragment_player.view.*
import org.jetbrains.anko.startActivity

class FixtureDetailsFragment : Fragment() {

    lateinit var app: MainApp
    lateinit var root: View
    lateinit var loader : AlertDialog
    var fixtureDetails: FixtureModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp

        arguments?.let {
            fixtureDetails = it.getParcelable("fixturedetails")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        root = inflater.inflate(R.layout.fragment_fixture_details, container, false)
        activity?.title = getString(R.string.action_edit)
        loader = createLoader(activity!!)

        root.dDate.text = fixtureDetails!!.date
        root.dcompetition.text = fixtureDetails!!.time
        root.dTeamAName.text = fixtureDetails!!.teamAName
        root.dTeamBName.text = fixtureDetails!!.teamBName

        return root
    }


    private fun setEditBtnListener(layout: View) {

        layout.addPlayerImage.setOnClickListener {
            //startFragment<EditFixtureFragment>(fixture)
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(fixture: FixtureModel) =
            FixtureDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("fixturedetails",fixture)
                }
            }
    }
}