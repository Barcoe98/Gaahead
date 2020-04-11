package ie.wit.fragments.clubFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import ie.wit.R
import ie.wit.adapters.ClubAdapter
import ie.wit.adapters.ClubListener
import ie.wit.utils.createLoader
import ie.wit.utils.hideLoader
import ie.wit.utils.showLoader
import ie.wit.main.MainApp
import ie.wit.models.ClubModel
import kotlinx.android.synthetic.main.list_view.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


open class ClubListFragment : Fragment(), AnkoLogger, ClubListener {

    lateinit var app: MainApp
    lateinit var loader: AlertDialog
    lateinit var root: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.list_view, container, false)
        activity?.title = getString(R.string.fixture_title)

        root.recyclerView.layoutManager = LinearLayoutManager(activity)

        return root
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            ClubListFragment().apply {
                arguments = Bundle().apply { }
            }
    }



    override fun onClubClick(club: ClubModel) {
        activity!!.supportFragmentManager.beginTransaction()
            .replace(R.id.homeFrame, ClubDetailsFragment.newInstance(club))
            .addToBackStack(null)
            .commit()
    }


    fun getAllClubs() {
        loader = createLoader(activity!!)
        showLoader(loader, "Downloading Clubs from Firebase")
        val clubsList = ArrayList<ClubModel>()
        app.database.child("clubs")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    info("Firebase Club error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    hideLoader(loader)
                    val children = snapshot.children
                    children.forEach {
                        val club = it.getValue<ClubModel>(ClubModel::class.java)

                        clubsList.add(club!!)
                        root.recyclerView.adapter =
                            ClubAdapter(clubsList, this@ClubListFragment, false)
                        root.recyclerView.adapter?.notifyDataSetChanged()

                        app.database.child("clubs")
                            .removeEventListener(this)
                    }
                }
            })
    }


    override fun onResume() {
        super.onResume()
        if(this::class == ClubListFragment::class)
            getAllClubs()
    }

}


