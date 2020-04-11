package ie.wit.fragments.playerFragments

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
import ie.wit.adapters.PlayerAdapter
import ie.wit.adapters.PlayerListener
import ie.wit.utils.createLoader
import ie.wit.utils.hideLoader
import ie.wit.utils.showLoader
import ie.wit.main.MainApp
import ie.wit.models.PlayerModel
import ie.wit.utils.SwipeToDeleteCallback
import ie.wit.utils.SwipeToEditCallback
import kotlinx.android.synthetic.main.list_view.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


open class PlayerListFragment : Fragment(), AnkoLogger, PlayerListener {

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
        activity?.title = getString(R.string.action_player_list)

        root.recyclerView.layoutManager = LinearLayoutManager(activity)
        setSwipeRefresh()

        val swipeDeleteHandler = object : SwipeToDeleteCallback(activity!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = root.recyclerView.adapter as PlayerAdapter
                adapter.removeAt(viewHolder.adapterPosition)
                deletePlayer((viewHolder.itemView.tag as PlayerModel).uid)
                deleteUserPlayer(app.auth.currentUser!!.uid, (viewHolder.itemView.tag as PlayerModel).uid)
            }
        }
        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(root.recyclerView)

        val swipeEditHandler = object : SwipeToEditCallback(activity!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                onPlayerClick(viewHolder.itemView.tag as PlayerModel)
            }
        }
        val itemTouchEditHelper = ItemTouchHelper(swipeEditHandler)
        itemTouchEditHelper.attachToRecyclerView(root.recyclerView)

        return root
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            PlayerListFragment().apply {
                arguments = Bundle().apply { }
            }
    }


    open fun setSwipeRefresh() {
        root.swipeRefresh.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                root.swipeRefresh.isRefreshing = true
                getAllPlayers(app.auth.currentUser!!.uid)
            }
        })
    }


    fun checkSwipeRefresh() {
        if (root.swipeRefresh.isRefreshing) root.swipeRefresh.isRefreshing = false
    }


    fun deletePlayer(uid: String?) {
        app.database.child("players").child(uid!!)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.removeValue()
                    }
                    override fun onCancelled(error: DatabaseError) {
                        info("Firebase Player error : ${error.message}")
                    }
                })
    }


    fun deleteUserPlayer(userId: String, uid: String?) {
        app.database.child("user-players").child(userId).child(uid!!)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.removeValue()
                    }
                    override fun onCancelled(error: DatabaseError) {
                        info("Firebase Player error : ${error.message}")
                    }
                })
    }



    override fun onPlayerClick(player: PlayerModel) {
        activity!!.supportFragmentManager.beginTransaction()
            .replace(R.id.homeFrame, PlayerDetailsFragment.newInstance(player))
            .addToBackStack(null)
            .commit()
    }


    fun getAllPlayers(userId: String?) {
        loader = createLoader(activity!!)
        showLoader(loader, "Downloading Players from Firebase")
        val playersList = ArrayList<PlayerModel>()
        app.database.child("user-players").child(userId!!)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    info("Firebase Player error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    hideLoader(loader)
                    val children = snapshot.children
                    children.forEach {
                        val player = it.getValue<PlayerModel>(PlayerModel::class.java)

                        playersList.add(player!!)
                        root.recyclerView.adapter =
                            PlayerAdapter(playersList, this@PlayerListFragment, false)
                        root.recyclerView.adapter?.notifyDataSetChanged()
                        checkSwipeRefresh()

                        app.database.child("user-players").child(userId)
                            .removeEventListener(this)
                    }
                }
            })
    }


    override fun onResume() {
        super.onResume()
        if(this::class == PlayerListFragment::class)
        getAllPlayers(app.auth.currentUser!!.uid)
    }

}


