package ie.wit.fragments.fixtureFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ie.wit.R
import ie.wit.adapters.FixtureAdapter
import ie.wit.adapters.PinAdapter
import ie.wit.adapters.FixtureListener
import ie.wit.main.MainApp
import ie.wit.models.FixtureModel
import ie.wit.utils.SwipeToDeleteCallback
import ie.wit.utils.SwipeToEditCallback
import kotlinx.android.synthetic.main.list_view.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


open class FixtureListFragment : Fragment(), AnkoLogger, FixtureListener {

    lateinit var app: MainApp
    lateinit var root: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.list_view, container, false)
        activity?.title = getString(R.string.action_fixture_list)

        root.recyclerView.layoutManager = LinearLayoutManager(activity)

        var query = FirebaseDatabase.getInstance()
            .reference
            .child("user-fixtures").child(app.auth.currentUser!!.uid)

        var options = FirebaseRecyclerOptions.Builder<FixtureModel>()
            .setQuery(query, FixtureModel::class.java)
            .setLifecycleOwner(this)
            .build()

        root.recyclerView.adapter = FixtureAdapter(options, this)

        val swipeDeleteHandler = object : SwipeToDeleteCallback(activity!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                deleteFixture((viewHolder.itemView.tag as FixtureModel).uid)
                deleteUserFixture(app.auth.currentUser!!.uid, (viewHolder.itemView.tag as FixtureModel).uid)
            }
        }

        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(root.recyclerView)

        val swipeEditHandler = object : SwipeToEditCallback(activity!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                onFixtureClick(viewHolder.itemView.tag as FixtureModel)
            }
        }
        val itemTouchEditHelper = ItemTouchHelper(swipeEditHandler)
        itemTouchEditHelper.attachToRecyclerView(root.recyclerView)

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FixtureListFragment().apply {
                arguments = Bundle().apply { }
            }
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


}


