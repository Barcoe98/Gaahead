package ie.wit.fragments.clubFragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import ie.wit.R
import ie.wit.main.MainApp
import ie.wit.models.ClubModel
import ie.wit.utils.createLoader
import kotlinx.android.synthetic.main.fragment_club_details.view.*

class ClubDetailsFragment : Fragment() {

    lateinit var app: MainApp
    lateinit var root: View
    lateinit var loader : AlertDialog
    var clubDetails: ClubModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp

        arguments?.let {
            clubDetails = it.getParcelable("clubdetails")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        root = inflater.inflate(R.layout.fragment_club_details, container, false)
        activity?.title = getString(R.string.action_club_details)
        loader = createLoader(activity!!)

        root.tName.text = clubDetails!!.name
        root.tCounty.text = clubDetails!!.county
        root.tColours.text = clubDetails!!.colours
        root.tYearFounded.text = clubDetails!!.yearFounded
        root.tGrounds.text = clubDetails!!.grounds
        root.tDivision.text = clubDetails!!.division
        root.tTrophies.text = clubDetails!!.trophies
        root.tHistory.text = clubDetails!!.history

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance(club: ClubModel) =
            ClubDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("clubdetails",club)
                }
            }
    }
}