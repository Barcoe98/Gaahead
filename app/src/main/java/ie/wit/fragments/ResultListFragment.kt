package ie.wit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.R
import ie.wit.adapters.FixtureAdapter
import ie.wit.main.MainApp
import kotlinx.android.synthetic.main.fragment_fixture_list.view.*

class ResultListFragment : Fragment() {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_result_list, container, false)

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ResultListFragment().apply {
                arguments = Bundle().apply { }
            }
    }
}