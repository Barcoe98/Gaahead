package ie.wit.fragments.fixtureFragments


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
import kotlinx.android.synthetic.main.list_view.view.*
import org.jetbrains.anko.info

class FixtureAllFragment : FixtureListFragment(), FixtureListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.list_view, container, false)
        activity?.title = getString(R.string.menu_fixture_all)

        root.recyclerView.layoutManager = LinearLayoutManager(activity)
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
        root.swipeRefresh.setOnRefreshListener {
            root.swipeRefresh.isRefreshing = true
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
                        root.recyclerView.adapter =
                            FixtureAdapter(fixturesList, this@FixtureAllFragment, true)
                        root.recyclerView.adapter?.notifyDataSetChanged()
                        checkSwipeRefresh()

                        app.database.child("fixtures").removeEventListener(this)
                    }
                }
            })
    }
}