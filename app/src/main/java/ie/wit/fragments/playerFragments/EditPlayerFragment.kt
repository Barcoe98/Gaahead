package ie.wit.fragments.playerFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import ie.wit.R
import ie.wit.main.MainApp
import ie.wit.models.PlayerModel
import ie.wit.utils.createLoader
import ie.wit.utils.hideLoader
import ie.wit.utils.showLoader
import kotlinx.android.synthetic.main.fragment_edit_player.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class EditPlayerFragment : Fragment(), AnkoLogger {

    lateinit var app: MainApp
    lateinit var loader : AlertDialog
    lateinit var root: View
    var editPlayer: PlayerModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp

        arguments?.let {
            editPlayer = it.getParcelable("editplayer")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_edit_player, container, false)
        activity?.title = getString(R.string.action_player_edit)
        loader = createLoader(activity!!)

        //fiture to player help
        root.editPlayerName.setText(editPlayer!!.playerName)
        root.editPlayerAge.setText(editPlayer!!.playerAge)
        root.editPlayerHeight.setText(editPlayer!!.playerHeight)
        root.editPlayerWeight.setText(editPlayer!!.playerWeight)
        root.editPlayerPosition.setText(editPlayer!!.playerPosition)

        root.editPlayerBtn.setOnClickListener {
            showLoader(loader, "Updating Player on Server...")
            updatePlayerData()
            updatePlayer(editPlayer!!.uid, editPlayer!!)
            updateUserPlayer(app.auth.currentUser!!.uid,
                               editPlayer!!.uid, editPlayer!!)
        }

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance(player: PlayerModel) =
            EditPlayerFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("editplayer",player)
                }
            }
    }

    fun updatePlayerData() {

        editPlayer!!.playerName = root.editPlayerName.text.toString()
        editPlayer!!.playerAge = root.editPlayerAge.text.toString()
        editPlayer!!.playerHeight = root.editPlayerHeight.text.toString()
        editPlayer!!.playerWeight = root.editPlayerWeight.text.toString()
        editPlayer!!.playerPosition = root.editPlayerPosition.text.toString()
    }

    fun updateUserPlayer(userId: String, uid: String?, player: PlayerModel) {
        app.database.child("user-players").child(userId).child(uid!!)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.setValue(player)
                        activity!!.supportFragmentManager.beginTransaction()
                        .replace(R.id.homeFrame,
                            PlayerListFragment.newInstance()
                        )
                        .addToBackStack(null)
                        .commit()
                        hideLoader(loader)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        info("Firebase Player error : ${error.message}")
                    }
                })
    }

    fun updatePlayer(uid: String?, player: PlayerModel) {
        app.database.child("players").child(uid!!)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.setValue(player)
                        hideLoader(loader)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        info("Firebase Player error : ${error.message}")
                    }
                })
    }
}

