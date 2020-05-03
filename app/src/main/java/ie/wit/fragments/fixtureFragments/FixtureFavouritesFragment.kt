package ie.wit.fragments.fixtureFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import ie.wit.R
import ie.wit.adapters.FixtureAdapter
import ie.wit.adapters.PinAdapter
import ie.wit.adapters.FixtureListener
import ie.wit.main.MainApp
import ie.wit.models.FixtureModel
import kotlinx.android.synthetic.main.list_view.view.*
import org.jetbrains.anko.AnkoLogger


open class FavouritesFragment : Fragment(), AnkoLogger, FixtureListener {

    lateinit var app: MainApp
    lateinit var root: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        root = inflater.inflate(R.layout.list_view, container, false)
        activity?.title = getString(R.string.fixture_favourites_title)

        root.recyclerView.layoutManager = LinearLayoutManager(activity)

        //query to just get bookmarked fixtures
        var query = FirebaseDatabase.getInstance()
            .reference
            .child("user-fixtures").child(app.auth.currentUser!!.uid).orderByChild("isfavourite")
            .equalTo(true)

        var options = FirebaseRecyclerOptions.Builder<FixtureModel>()
            .setQuery(query, FixtureModel::class.java)
            .setLifecycleOwner(this)
            .build()

        root.recyclerView.adapter = FixtureAdapter(options, this)

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FavouritesFragment().apply {
                arguments = Bundle().apply { }
            }
    }

    override fun onFixtureClick(fixture: FixtureModel) {
    }


}


