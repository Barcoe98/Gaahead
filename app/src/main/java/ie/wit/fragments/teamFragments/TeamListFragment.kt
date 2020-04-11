package ie.wit.fragments.teamFragments

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
import ie.wit.adapters.TeamAdapter
import ie.wit.adapters.TeamListener
import ie.wit.utils.createLoader
import ie.wit.utils.hideLoader
import ie.wit.utils.showLoader
import ie.wit.main.MainApp
import ie.wit.models.TeamModel
import kotlinx.android.synthetic.main.fragment_fixture_list.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


open class TeamListFragment : Fragment(), AnkoLogger, TeamListener {

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

        return root
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            TeamListFragment().apply {
                arguments = Bundle().apply { }
            }
    }



    override fun onTeamClick(team: TeamModel) {
        activity!!.supportFragmentManager.beginTransaction()
            .replace(R.id.homeFrame, TeamDetailsFragment.newInstance(team))
            .addToBackStack(null)
            .commit()
    }


    fun getAllTeams() {
        loader = createLoader(activity!!)
        showLoader(loader, "Downloading Teams from Firebase")
        val teamsList = ArrayList<TeamModel>()
        app.database.child("teams")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    info("Firebase Team error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    hideLoader(loader)
                    val children = snapshot.children
                    children.forEach {
                        val team = it.getValue<TeamModel>(TeamModel::class.java)

                        teamsList.add(team!!)
                        root.fRecyclerView.adapter =
                            TeamAdapter(teamsList, this@TeamListFragment, false)
                        root.fRecyclerView.adapter?.notifyDataSetChanged()

                        app.database.child("teams")
                            .removeEventListener(this)
                    }
                }
            })
    }


    override fun onResume() {
        super.onResume()
        if(this::class == TeamListFragment::class)
            getAllTeams()
    }

}


