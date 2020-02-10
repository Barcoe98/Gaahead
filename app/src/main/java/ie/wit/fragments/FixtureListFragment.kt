package ie.wit.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.R
import ie.wit.adapters.FixtureAdapter
import ie.wit.main.MainApp
import ie.wit.models.FixtureModel
import kotlinx.android.synthetic.main.fragment_fixture_list.*
import kotlinx.android.synthetic.main.fragment_fixture_list.view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult

class FixtureListFragment : Fragment() {

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
        var root = inflater.inflate(R.layout.fragment_fixture_list, container, false)

        root.fRecyclerView.layoutManager = LinearLayoutManager(activity)
        root.fRecyclerView.adapter = FixtureAdapter(app.fixturesStore.findAll())

        return root

        //Loads Fixtures from json file
        loadFixtures()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FixtureListFragment().apply {
                arguments = Bundle().apply { }
            }
    }

    private fun loadFixtures() {
        showFixtures(app.fixturesStore.findAll())
    }

    private fun showFixtures (fixtures: List<FixtureModel>) {
        fRecyclerView.adapter = FixtureAdapter(fixtures)
        fRecyclerView.adapter?.notifyDataSetChanged()
    }
}