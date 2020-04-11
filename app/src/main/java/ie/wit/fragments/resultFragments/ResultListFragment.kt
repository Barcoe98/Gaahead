package ie.wit.fragments.resultFragments

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
import ie.wit.adapters.ResultAdapter
import ie.wit.adapters.ResultListener
import ie.wit.utils.createLoader
import ie.wit.utils.hideLoader
import ie.wit.utils.showLoader
import ie.wit.main.MainApp
import ie.wit.models.ResultModel
import ie.wit.utils.SwipeToDeleteCallback
import ie.wit.utils.SwipeToEditCallback
import kotlinx.android.synthetic.main.fragment_result_list.view.*
import kotlinx.android.synthetic.main.fragment_club_list.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


open class ResultListFragment : Fragment(), AnkoLogger, ResultListener {

    lateinit var app: MainApp
    lateinit var loader: AlertDialog
    lateinit var root: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_result_list, container, false)
        activity?.title = getString(R.string.result_title)

        root.rRecyclerView.layoutManager = LinearLayoutManager(activity)
        setSwipeRefresh()

        val swipeDeleteHandler = object : SwipeToDeleteCallback(activity!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = root.rRecyclerView.adapter as ResultAdapter
                adapter.removeAt(viewHolder.adapterPosition)
                deleteResult((viewHolder.itemView.tag as ResultModel).uid)
                deleteUserResult(app.auth.currentUser!!.uid, (viewHolder.itemView.tag as ResultModel).uid)
            }
        }
        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(root.rRecyclerView)

        val swipeEditHandler = object : SwipeToEditCallback(activity!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                onResultClick(viewHolder.itemView.tag as ResultModel)
            }
        }
        val itemTouchEditHelper = ItemTouchHelper(swipeEditHandler)
        itemTouchEditHelper.attachToRecyclerView(root.rRecyclerView)

        return root
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            ResultListFragment().apply {
                arguments = Bundle().apply { }
            }
    }


    open fun setSwipeRefresh() {
        root.clubSwipeRefresh.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                root.clubSwipeRefresh.isRefreshing = true
                getAllResults(app.auth.currentUser!!.uid)
            }
        })
    }


    fun checkSwipeRefresh() {
        if (root.clubSwipeRefresh.isRefreshing) root.clubSwipeRefresh.isRefreshing = false
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


    fun getAllResults(userId: String?) {
        loader = createLoader(activity!!)
        showLoader(loader, "Downloading Results from Firebase")
        val resultsList = ArrayList<ResultModel>()
        app.database.child("user-results").child(userId!!)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    info("Firebase Result error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    hideLoader(loader)
                    val children = snapshot.children
                    children.forEach {
                        val result = it.getValue<ResultModel>(ResultModel::class.java)

                        resultsList.add(result!!)
                        root.rRecyclerView.adapter =
                            ResultAdapter(resultsList, this@ResultListFragment, false)
                        root.rRecyclerView.adapter?.notifyDataSetChanged()
                        checkSwipeRefresh()

                        app.database.child("user-results").child(userId)
                            .removeEventListener(this)
                    }
                }
            })
    }


    override fun onResume() {
        super.onResume()
        if(this::class == ResultListFragment::class)
        getAllResults(app.auth.currentUser!!.uid)
    }

}


