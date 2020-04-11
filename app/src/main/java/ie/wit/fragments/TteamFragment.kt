package ie.wit.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import ie.wit.R
import ie.wit.main.MainApp

class TteamFragment : Fragment() {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_tteam, container, false)
        activity?.title = getString(R.string.team_title)
        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            TteamFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}