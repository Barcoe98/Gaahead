package ie.wit.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import ie.wit.R
import ie.wit.main.MainApp
import ie.wit.models.PlayerModel
import kotlinx.android.synthetic.main.fragment_edit_player.view.*
import kotlinx.android.synthetic.main.fragment_player_details.view.*

class PlayerDetailsFragment : Fragment() {

    lateinit var app: MainApp
    var playerDetails: PlayerModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp

        arguments?.let {
            playerDetails = it.getParcelable("playerdetails")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val root = inflater.inflate(R.layout.fragment_player_details, container, false)
        activity?.title = getString(R.string.info_title)

        root.playerName.text = playerDetails!!.playerName
        //root.playerAge.text = playerDetails!!.playerAge
        //root.playerHeight.text = playerDetails!!.playerHeight
        //root.playerWeight.text = playerDetails!!.playerWeight

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PlayerDetailsFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}