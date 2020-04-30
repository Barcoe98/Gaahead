package ie.wit.fragments.resultFragments

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
import ie.wit.adapters.ResultAdapter
import ie.wit.adapters.ResultListener
import ie.wit.main.MainApp
import ie.wit.models.ResultModel
import ie.wit.utils.SwipeToDeleteCallback
import ie.wit.utils.SwipeToEditCallback
import kotlinx.android.synthetic.main.list_view.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


open class ResultListFragment : Fragment(), AnkoLogger, ResultListener {

    lateinit var app: MainApp
    lateinit var root: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.list_view, container, false)
        activity?.title = getString(R.string.action_result_list)

        root.recyclerView.layoutManager = LinearLayoutManager(activity)

        var query = FirebaseDatabase.getInstance()
            .reference
            .child("user-results").child(app.auth.currentUser!!.uid)

        var options = FirebaseRecyclerOptions.Builder<ResultModel>()
            .setQuery(query, ResultModel::class.java)
            .setLifecycleOwner(this)
            .build()

        root.recyclerView.adapter = ResultAdapter(options, this)

        val swipeDeleteHandler = object : SwipeToDeleteCallback(activity!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                deleteResult((viewHolder.itemView.tag as ResultModel).uid)
                deleteUserResult(app.auth.currentUser!!.uid, (viewHolder.itemView.tag as ResultModel).uid)
            }
        }

        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(root.recyclerView)

        val swipeEditHandler = object : SwipeToEditCallback(activity!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                onResultClick(viewHolder.itemView.tag as ResultModel)
            }
        }
        val itemTouchEditHelper = ItemTouchHelper(swipeEditHandler)
        itemTouchEditHelper.attachToRecyclerView(root.recyclerView)


        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ResultListFragment().apply {
                arguments = Bundle().apply { }
            }
    }


    fun deleteResult(uid: String?) {
        app.database.child("results").child(uid!!)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.removeValue()
                    }
                    override fun onCancelled(error: DatabaseError) {
                        info("Firebase Result error : ${error.message}")
                    }
                })
    }


    fun deleteUserResult(userId: String, uid: String?) {
        app.database.child("user-results").child(userId).child(uid!!)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.removeValue()
                    }
                    override fun onCancelled(error: DatabaseError) {
                        info("Firebase Result error : ${error.message}")
                    }
                })
    }


    override fun onResultClick(result: ResultModel) {
        activity!!.supportFragmentManager.beginTransaction()
            .replace(R.id.homeFrame, EditResultFragment.newInstance(result))
            .addToBackStack(null)
            .commit()
    }


}


