package ie.wit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.firebase.database.ValueEventListener
import ie.wit.R
import ie.wit.helpers.createLoader
import ie.wit.helpers.hideLoader
import ie.wit.helpers.showLoader
import ie.wit.main.MainApp
import ie.wit.models.FixtureModel
import kotlinx.android.synthetic.main.activity_fixture.*
import kotlinx.android.synthetic.main.activity_fixture.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.util.HashMap


class FixtureFragment : Fragment(), AnkoLogger {

    lateinit var app: MainApp
    //private val imageRequest = 1
    lateinit var loader : AlertDialog
    //lateinit var eventListener : ValueEventListener



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.activity_fixture, container, false)
        loader = createLoader(activity!!)
        activity?.title = getString(R.string.fixture_title)

        setButtonListener(root)

        return root
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            FixtureFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    /*
    private fun setImgBtnListener(layout: View){

        layout.teamBLogoBtn.setOnClickListener {
            showImagePicker(this, imageRequest)
        }

        layout.teamALogoBtn.setOnClickListener {
            showImagePicker(this, imageRequest)
        }
    }


     */


    private fun setButtonListener(layout: View) {
        layout.addFixtureBtn.setOnClickListener {

            val teamAName = teamAName.toString()
            val teamBName = teamBName.toString()
            val date = date.toString()
            val time = time.toString()
            val location = location.toString()

            when {
                layout.teamAName.text.isEmpty() -> Toast.makeText(app, R.string.error_teamAName, Toast.LENGTH_LONG).show()
                layout.teamBName.text.isEmpty() -> Toast.makeText(app, R.string.error_teamBName, Toast.LENGTH_LONG).show()
                layout.date.text.isEmpty() -> Toast.makeText(app, R.string.error_date, Toast.LENGTH_LONG).show()
                layout.time.text.isEmpty() -> Toast.makeText(app, R.string.error_time, Toast.LENGTH_LONG).show()
                layout.location.text.isEmpty() -> Toast.makeText(app, R.string.error_location, Toast.LENGTH_LONG).show()
                else -> writeNewFixture(FixtureModel(teamAName = teamAName, teamBName = teamBName, time = time, date = date, location = location, email = app.auth.currentUser?.email))
            }
            layout.teamAName.setText("").toString()
            layout.teamBName.setText("").toString()
            layout.location.setText("").toString()
            layout.date.setText("").toString()
            layout.time.setText("").toString()
        }
    }

    override fun onPause() {
        super.onPause()
        app.database.child("user-fixtures")
            .child(app.auth.currentUser!!.uid)
            //.removeEventListener(eventListener)
    }

    fun writeNewFixture(fixture: FixtureModel) {

        // Create new fixture at /fixtures & /fixtures/$uid
        showLoader(loader, "Adding fixture to Firebase")
        info("Firebase DB Reference : $app.database")
        val uid = app.auth.currentUser!!.uid
        val key = app.database.child("fixtures").push().key
        if (key == null) {
            info("Firebase Error : Key Empty")
            return
        }
        fixture.uid = key
        val fixtureValues = fixture.toMap()

        val childUpdates = HashMap<String, Any>()
        childUpdates["/fixtures/$key"] = fixtureValues
        childUpdates["/user-fixtures/$uid/$key"] = fixtureValues

        app.database.updateChildren(childUpdates)
        hideLoader(loader)
    }
}