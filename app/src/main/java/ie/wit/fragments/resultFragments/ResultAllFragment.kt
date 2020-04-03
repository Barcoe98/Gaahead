package ie.wit.fragments.resultFragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import ie.wit.R
import ie.wit.adapters.ResultAdapter
import ie.wit.adapters.ResultListener
import ie.wit.models.ResultModel
import ie.wit.utils.createLoader
import ie.wit.utils.hideLoader
import ie.wit.utils.showLoader
import kotlinx.android.synthetic.main.fragment_result_list.view.*
import org.jetbrains.anko.info

class ResultAllFragment : ResultListFragment(), ResultListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_result_list, container, false)
        activity?.title = getString(R.string.menu_fixture_all)

        root.rRecyclerView.layoutManager = LinearLayoutManager(activity)
        setSwipeRefresh()

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ResultAllFragment().apply {
                arguments = Bundle().apply { }
            }
    }

    override fun setSwipeRefresh() {
        root.resultSwipeRefresh.setOnRefreshListener {
            root.resultSwipeRefresh.isRefreshing = true
            getAllUsersResults()
        }
    }

    override fun onResume() {
        super.onResume()
        getAllUsersResults()
    }

    fun getAllUsersResults() {
        loader = createLoader(activity!!)
        showLoader(loader, "Downloading All Users Results from Firebase")
        val resultslist = ArrayList<ResultModel>()
        app.database.child("results")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    info("Firebase Result error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    hideLoader(loader)
                    val children = snapshot.children
                    children.forEach {
                        val result = it.
                            getValue<ResultModel>(ResultModel::class.java)

                        resultslist.add(result!!)
                        root.rRecyclerView.adapter =
                            ResultAdapter(resultslist, this@ResultAllFragment, true)
                        root.rRecyclerView.adapter?.notifyDataSetChanged()
                        checkSwipeRefresh()

                        app.database.child("results").removeEventListener(this)
                    }
                }
            })
    }
}