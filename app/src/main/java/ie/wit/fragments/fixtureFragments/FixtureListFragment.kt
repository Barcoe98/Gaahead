
package ie.wit.fragments.fixtureFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import ie.wit.R
import ie.wit.adapters.FixtureAdapter
import ie.wit.adapters.FixtureListener
import ie.wit.utils.createLoader
import ie.wit.utils.hideLoader
import ie.wit.utils.showLoader
import ie.wit.main.MainApp
import ie.wit.models.FixtureModel
import ie.wit.utils.SwipeToDeleteCallback
import ie.wit.utils.SwipeToEditCallback
import kotlinx.android.synthetic.main.fragment_fixture_list.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


open class FixtureListFragment : Fragment(), AnkoLogger, FixtureListener {

    lateinit var app: MainApp
    lateinit var loader: AlertDialog
    lateinit var root: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_fixture_list, container, false)
        activity?.title = getString(R.string.fixture_title)

        root.fRecyclerView.layoutManager = LinearLayoutManager(activity)
        setSwipeRefresh()

        val swipeDeleteHandler = object : SwipeToDeleteCallback(activity!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = root.fRecyclerView.adapter as FixtureAdapter
                adapter.removeAt(viewHolder.adapterPosition)
                deleteFixture((viewHolder.itemView.tag as FixtureModel).uid)
                deleteUserFixture(app.auth.currentUser!!.uid, (viewHolder.itemView.tag as FixtureModel).uid)
            }
        }
        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(root.fRecyclerView)

        val swipeEditHandler = object : SwipeToEditCallback(activity!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                onFixtureClick(viewHolder.itemView.tag as FixtureModel)
            }
        }
        val itemTouchEditHelper = ItemTouchHelper(swipeEditHandler)
        itemTouchEditHelper.attachToRecyclerView(root.fRecyclerView)

        return root
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            FixtureListFragment().apply {
                arguments = Bundle().apply { }
            }
    }


    open fun setSwipeRefresh() {
        root.fixtureSwipeRefresh.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                root.fixtureSwipeRefresh.isRefreshing = true
                getAllFixtures(app.auth.currentUser!!.uid)
            }
        })
    }


    fun checkSwipeRefresh() {
        if (root.fixtureSwipeRefresh.isRefreshing) root.fixtureSwipeRefresh.isRefreshing = false
    }


    fun deleteFixture(uid: String?) {
        app.database.child("fixtures").child(uid!!)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.removeValue()
                    }
                    override fun onCancelled(error: DatabaseError) {
                        info("Firebase Fixture error : ${error.message}")
                    }
                })
    }


    fun deleteUserFixture(userId: String, uid: String?) {
        app.database.child("user-fixtures").child(userId).child(uid!!)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.removeValue()
                    }
                    override fun onCancelled(error: DatabaseError) {
                        info("Firebase Fixture error : ${error.message}")
                    }
                })
    }


    override fun onFixtureClick(fixture: FixtureModel) {
        activity!!.supportFragmentManager.beginTransaction()
            .replace(R.id.homeFrame, EditFixtureFragment.newInstance(fixture))
            .addToBackStack(null)
            .commit()
    }


    fun getAllFixtures(userId: String?) {
        loader = createLoader(activity!!)
        showLoader(loader, "Downloading Fixtures from Firebase")
        val fixturesList = ArrayList<FixtureModel>()
        app.database.child("user-fixtures").child(userId!!)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    info("Firebase Fixture error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    hideLoader(loader)
                    val children = snapshot.children
                    children.forEach {
                        val fixture = it.getValue<FixtureModel>(FixtureModel::class.java)

                        fixturesList.add(fixture!!)
                        root.fRecyclerView.adapter =
                            FixtureAdapter(fixturesList, this@FixtureListFragment, false)
                        root.fRecyclerView.adapter?.notifyDataSetChanged()
                        checkSwipeRefresh()

                        app.database.child("user-fixtures").child(userId)
                            .removeEventListener(this)
                    }
                }
            })
    }


    override fun onResume() {
        super.onResume()
        if(this::class == FixtureListFragment::class)
        getAllFixtures(app.auth.currentUser!!.uid)
    }

}


