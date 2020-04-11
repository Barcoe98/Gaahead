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
import kotlinx.android.synthetic.main.list_view.view.*
import org.jetbrains.anko.info

class PlayerAllFragment : PlayerListFragment(), PlayerListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.list_view, container, false)
        activity?.title = getString(R.string.menu_player_all)

        root.recyclerView.layoutManager = LinearLayoutManager(activity)
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
        root.swipeRefresh.setOnRefreshListener {
            root.swipeRefresh.isRefreshing = true
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
                        root.recyclerView.adapter =
                            PlayerAdapter(playerslist, this@PlayerAllFragment, true)
                        root.recyclerView.adapter?.notifyDataSetChanged()
                        checkSwipeRefresh()

                        app.database.child("players").removeEventListener(this)
                    }
                }
            })
    }
}