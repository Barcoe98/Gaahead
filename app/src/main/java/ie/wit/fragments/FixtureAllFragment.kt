package ie.wit.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import ie.wit.R
import ie.wit.adapters.FixtureAdapter
import ie.wit.adapters.FixtureListener
import ie.wit.models.FixtureModel
import ie.wit.utils.createLoader
import ie.wit.utils.hideLoader
import ie.wit.utils.showLoader
import kotlinx.android.synthetic.main.fragment_fixture_list.view.*
import org.jetbrains.anko.info

class FixtureAllFragment : FixtureListFragment(), FixtureListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_fixture_list, container, false)
        activity?.title = getString(R.string.menu_fixture_all)

        root.fRecyclerView.layoutManager = LinearLayoutManager(activity)
        setSwipeRefresh()

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FixtureAllFragment().apply {
                arguments = Bundle().apply { }
            }
    }

    override fun setSwipeRefresh() {
        root.fixtureSwipeRefresh.setOnRefreshListener {
            root.fixtureSwipeRefresh.isRefreshing = true
            getAllUsersFixtures()
        }
    }

    override fun onResume() {
        super.onResume()
        getAllUsersFixtures()
    }

    fun getAllUsersFixtures() {
        loader = createLoader(activity!!)
        showLoader(loader, "Downloading All Users Fixtures from Firebase")
        val fixturesList = ArrayList<FixtureModel>()
        app.database.child("fixtures")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    info("Firebase Fixture error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    hideLoader(loader)
                    val children = snapshot.children
                    children.forEach {
                        val fixture = it.
                            getValue<FixtureModel>(FixtureModel::class.java)

                        fixturesList.add(fixture!!)
                        root.fRecyclerView.adapter =
                            FixtureAdapter(fixturesList, this@FixtureAllFragment, true)
                        root.fRecyclerView.adapter?.notifyDataSetChanged()
                        checkSwipeRefresh()

                        app.database.child("fixtures").removeEventListener(this)
                    }
                }
            })
    }
}