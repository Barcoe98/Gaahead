package ie.wit.fragments.fixtureFragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import ie.wit.R
import ie.wit.adapters.FixtureAdapter
import ie.wit.adapters.PinAdapter
import ie.wit.adapters.FixtureListener
import ie.wit.models.FixtureModel
import kotlinx.android.synthetic.main.list_view.view.*

class FixtureAllFragment : FixtureListFragment(), FixtureListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.list_view, container, false)
        activity?.title = getString(R.string.action_fixture_all)

        root.recyclerView.layoutManager = LinearLayoutManager(activity)

        var query = FirebaseDatabase.getInstance()
            .reference.child("fixtures")

        var options = FirebaseRecyclerOptions.Builder<FixtureModel>()
            .setQuery(query, FixtureModel::class.java)
            .setLifecycleOwner(this)
            .build()

        root.recyclerView.adapter = FixtureAdapter(options,this)

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FixtureAllFragment().apply {
                arguments = Bundle().apply { }
            }
    }
}