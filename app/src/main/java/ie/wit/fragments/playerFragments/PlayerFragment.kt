package ie.wit.fragments.playerFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import ie.wit.R
import ie.wit.fragments.MyInfoFragment
import ie.wit.main.MainApp
import ie.wit.models.PlayerModel
import ie.wit.utils.*
import kotlinx.android.synthetic.main.fragment_player.view.*
import kotlinx.android.synthetic.main.fragment_player.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.util.HashMap

open class PlayerFragment : Fragment(), AnkoLogger {

    lateinit var app: MainApp
    lateinit var loader: AlertDialog
    val IMAGE_REQUEST = 1
    //lateinit var eventListener : ValueEventListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_player, container, false)
        loader = createLoader(activity!!)
        activity?.title = getString(R.string.action_player_add)

        setButtonListener(root)
        setImgBtnListener(root)

        return root
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            PlayerFragment().apply {
                arguments = Bundle().apply {}
            }
    }


    private fun setImgBtnListener(layout: View) {

        layout.addPlayerImage.setOnClickListener {
            showImagePickerFragment(this, IMAGE_REQUEST)
        }

    }


    private fun setButtonListener(layout: View) {
        layout.addPlayerBtn.setOnClickListener {

            val name = playerName.text.toString()
            val age = playerAge.text.toString()
            val height = playerHeight.text.toString()
            val weight = playerWeight.text.toString()
            val position = playerPosition.text.toString()

            when {
                layout.playerName.text.isEmpty() -> Toast.makeText(
                    app,
                    R.string.error_teamAName,
                    Toast.LENGTH_LONG
                ).show()
                layout.playerAge.text.isEmpty() -> Toast.makeText(
                    app,
                    R.string.error_teamBName,
                    Toast.LENGTH_LONG
                ).show()
                layout.playerHeight.text.isEmpty() -> Toast.makeText(
                    app,
                    R.string.error_date,
                    Toast.LENGTH_LONG
                ).show()
                layout.playerWeight.text.isEmpty() -> Toast.makeText(
                    app,
                    R.string.error_time,
                    Toast.LENGTH_LONG
                ).show()
                layout.playerPosition.text.isEmpty() -> Toast.makeText(
                    app,
                    R.string.error_time,
                    Toast.LENGTH_LONG
                ).show()
                else -> writeNewPlayer(
                    PlayerModel(
                        playerName = name,
                        playerAge = age,
                        playerHeight = height,
                        playerWeight = weight,
                        playerPosition = position,
                        email = app.auth.currentUser?.email
                    )
                )
            }
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.homeFrame,
                    PlayerListFragment.newInstance())
                .addToBackStack(null)
                .commit()

        }
    }


    override fun onPause() {
        super.onPause()
        app.database.child("user-players")
            .child(app.auth.currentUser!!.uid)
            //.removeEventListener(eventListener)
    }

    fun writeNewPlayer(player: PlayerModel) {

        // Create new player at /players & /players/$uid
        showLoader(loader, "Adding player to Firebase")
        info("Firebase DB Reference : $app.database")
        val uid = app.auth.currentUser!!.uid
        val key = app.database.child("players").push().key
        if (key == null) {
            info("Firebase Error : Key Empty")
            return
        }
        player.uid = key
        val playerValues = player.toMap()

        val childUpdates = HashMap<String, Any>()
        childUpdates["/players/$key"] = playerValues
        childUpdates["/user-players/$uid/$key"] = playerValues

        app.database.updateChildren(childUpdates)
        hideLoader(loader)
    }
}
