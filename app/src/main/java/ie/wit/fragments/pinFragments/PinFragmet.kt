package ie.wit.fragments.pinFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.firebase.database.ValueEventListener
import ie.wit.R
import ie.wit.main.MainApp
import ie.wit.models.PinModel
import ie.wit.utils.*
import kotlinx.android.synthetic.main.fragment_fixture.view.*
import kotlinx.android.synthetic.main.fragment_pin.*
import kotlinx.android.synthetic.main.fragment_pin.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.util.HashMap

open class PinFragment : Fragment(), AnkoLogger {

    lateinit var app: MainApp
    lateinit var loader: AlertDialog
    lateinit var eventListener : ValueEventListener
    var pinned = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_pin, container, false)
        loader = createLoader(activity!!)
        activity?.title = getString(R.string.action_pin_add)

        setButtonListener(root)

        return root
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            PinFragment().apply {
                arguments = Bundle().apply {}
            }
    }


    private fun setButtonListener(layout: View) {
        layout.addPinBtn.setOnClickListener {

            val title = title.text.toString()
            val comment = comment.text.toString()

            when {
                layout.title.text.isEmpty() -> Toast.makeText(
                    app,
                    R.string.error_pin_title,
                    Toast.LENGTH_LONG
                ).show()
                layout.comment.text.isEmpty() -> Toast.makeText(
                    app,
                    R.string.error_pin_comment,
                    Toast.LENGTH_LONG
                ).show()
                else -> writeNewPin(
                    PinModel(
                        title = title,
                        comment = comment,
                        latitude = app.currentLocation.latitude,
                        longitude = app.currentLocation.longitude,
                        isPinned = pinned,
                        email = app.auth.currentUser?.email
                    )
                )


            }

            layout.title.setText("")
            layout.comment.setText("")
        }
    }


    override fun onPause() {
        super.onPause()
        app.database.child("user-pins")
            .child(app.auth.currentUser!!.uid)
            //.removeEventListener(eventListener)
    }

    fun writeNewPin(pin: PinModel) {

        // Create new pin at /pins & /pins/$uid
        showLoader(loader, "Adding pin to Firebase")
        info("Firebase DB Reference : $app.database")
        val uid = app.auth.currentUser!!.uid
        val key = app.database.child("pins").push().key
        if (key == null) {
            info("Firebase Error : Key Empty")
            return
        }
        pin.uid = key
        val pinValues = pin.toMap()

        val childUpdates = HashMap<String, Any>()
        childUpdates["/pins/$key"] = pinValues
        childUpdates["/user-pins/$uid/$key"] = pinValues

        app.database.updateChildren(childUpdates)
        hideLoader(loader)
    }
}
