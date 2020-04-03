package ie.wit.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import ie.wit.R
import ie.wit.main.MainApp

class TeamInfoFragment : Fragment() {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_team_info, container, false)
        activity?.title = getString(R.string.info_title)
        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            TeamInfoFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}