@file:Suppress("UNREACHABLE_CODE")

package ie.wit.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.R
import ie.wit.activities.FixtureActivity
import ie.wit.adapters.FixtureAdapter
import ie.wit.adapters.FixtureListener
import ie.wit.main.MainApp
import ie.wit.models.FixtureModel
import kotlinx.android.synthetic.main.fragment_fixture_list.*
import kotlinx.android.synthetic.main.fragment_fixture_list.view.*


class FixtureListFragment : Fragment(), FixtureListener {

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
        activity?.title = getString(R.string.fixture_title)

        root.fRecyclerView.layoutManager = LinearLayoutManager(activity)
        root.fRecyclerView.adapter = FixtureAdapter(app.fixturesStore.findAll(),this)

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
      fun loadFixtures() {
        showFixtures(app.fixturesStore.findAll())
    }

      fun showFixtures (fixtures: List<FixtureModel>) {
        fRecyclerView.adapter = FixtureAdapter(fixtures,this)
        fRecyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onFixtureClick(fixture: FixtureModel) {
        val intent = Intent(activity, FixtureActivity::class.java).putExtra("fixture_edit", fixture)
        startActivity(intent)
        loadFixtures()
    }
}