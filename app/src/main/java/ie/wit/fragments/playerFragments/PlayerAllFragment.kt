package ie.wit.fragments.playerFragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import ie.wit.R
import ie.wit.adapters.PlayerAdapter
import ie.wit.adapters.PlayerListener
import ie.wit.models.PlayerModel
import ie.wit.utils.createLoader
import ie.wit.utils.hideLoader
import ie.wit.utils.showLoader
import kotlinx.android.synthetic.main.fragment_player_list.view.*
import org.jetbrains.anko.info

class PlayerAllFragment : PlayerListFragment(), PlayerListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_player_list, container, false)
        activity?.title = getString(R.string.menu_fixture_all)

        root.pRecyclerView.layoutManager = LinearLayoutManager(activity)
        setSwipeRefresh()

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PlayerAllFragment().apply {
                arguments = Bundle().apply { }
            }
    }

    override fun setSwipeRefresh() {
        root.playerSwipeRefresh.setOnRefreshListener {
            root.playerSwipeRefresh.isRefreshing = true
            getAllUsersPlayers()
        }
    }

    override fun onResume() {
        super.onResume()
        getAllUsersPlayers()
    }

    fun getAllUsersPlayers() {
        loader = createLoader(activity!!)
        showLoader(loader, "Downloading All Users Players from Firebase")
        val playerslist = ArrayList<PlayerModel>()
        app.database.child("results")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    info("Firebase Player error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    hideLoader(loader)
                    val children = snapshot.children
                    children.forEach {
                        val player = it.
                            getValue<PlayerModel>(PlayerModel::class.java)

                        playerslist.add(player!!)
                        root.pRecyclerView.adapter =
                            PlayerAdapter(playerslist, this@PlayerAllFragment, true)
                        root.pRecyclerView.adapter?.notifyDataSetChanged()
                        checkSwipeRefresh()

                        app.database.child("players").removeEventListener(this)
                    }
                }
            })
    }
}