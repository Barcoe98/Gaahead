package ie.wit.fragments.clubFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import ie.wit.R
import ie.wit.adapters.ClubAdapter
import ie.wit.adapters.ClubListener
import ie.wit.main.MainApp
import ie.wit.models.ClubModel
import kotlinx.android.synthetic.main.list_view.view.*
import org.jetbrains.anko.AnkoLogger


open class ClubFavouritesFragment : Fragment(), AnkoLogger, ClubListener {

    lateinit var app: MainApp
    lateinit var root: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.list_view, container, false)
        activity?.title = getString(R.string.club_favourites_title)

        root.recyclerView.layoutManager = LinearLayoutManager(activity)

        //Populates Recyclerviwew with just Club that are Pinned
        var query = FirebaseDatabase.getInstance()
            .reference
            .child("clubs").orderByChild("isfavourite")
            .equalTo(true)

        var options = FirebaseRecyclerOptions.Builder<ClubModel>()
            .setQuery(query, ClubModel::class.java)
            .setLifecycleOwner(this)
            .build()

        root.recyclerView.adapter = ClubAdapter(options, this)

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ClubFavouritesFragment().apply {
                arguments = Bundle().apply { }
            }
    }

    override fun onClubClick(club: ClubModel) {
        activity!!.supportFragmentManager.beginTransaction()
            .replace(R.id.homeFrame, ClubDetailsFragment.newInstance(club))
            .addToBackStack(null)
            .commit()
    }
}


