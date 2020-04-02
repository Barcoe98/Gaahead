package ie.wit.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import ie.wit.R
import ie.wit.main.MainApp
import ie.wit.models.FixtureModel
import ie.wit.utils.*
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.fragment_fixture.*
import kotlinx.android.synthetic.main.fragment_fixture.view.*
import kotlinx.android.synthetic.main.manager_home.*
import kotlinx.android.synthetic.main.nav_header_home.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.util.HashMap

open class FixtureFragment : Fragment(), AnkoLogger {

    lateinit var app: MainApp
    //private val imageRequest = 1
    lateinit var loader: AlertDialog
    //lateinit var eventListener : ValueEventListener
    val IMAGE_REQUEST = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_fixture, container, false)
        loader = createLoader(activity!!)
        activity?.title = getString(R.string.fixture_title)

        setButtonListener(root)
        setImgBtnListener(root)

        return root
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            FixtureFragment().apply {
                arguments = Bundle().apply {}
            }
    }


    private fun setImgBtnListener(layout: View) {

        layout.logoABtn.setOnClickListener {
            showImagePickerFragment(this, IMAGE_REQUEST)
        }

        layout.logoBBtn.setOnClickListener {
            showImagePickerFragment(this, IMAGE_REQUEST)
        }
    }


    private fun setButtonListener(layout: View) {
        layout.addFixtureBtn.setOnClickListener {

            val teamAName = teamAName.text.toString()
            val teamBName = teamBName.text.toString()
            val date = date.text.toString()
            val time = time.text.toString()
            val location = location.text.toString()

            when {
                layout.teamAName.text.isEmpty() -> Toast.makeText(
                    app,
                    R.string.error_teamAName,
                    Toast.LENGTH_LONG
                ).show()
                layout.teamBName.text.isEmpty() -> Toast.makeText(
                    app,
                    R.string.error_teamBName,
                    Toast.LENGTH_LONG
                ).show()
                layout.date.text.isEmpty() -> Toast.makeText(
                    app,
                    R.string.error_date,
                    Toast.LENGTH_LONG
                ).show()
                layout.time.text.isEmpty() -> Toast.makeText(
                    app,
                    R.string.error_time,
                    Toast.LENGTH_LONG
                ).show()
                layout.location.text.isEmpty() -> Toast.makeText(
                    app,
                    R.string.error_location,
                    Toast.LENGTH_LONG
                ).show()
                else -> writeNewFixture(
                    FixtureModel(
                        teamAName = teamAName,
                        teamBName = teamBName,
                        time = time,
                        date = date,
                        location = location,
                        email = app.auth.currentUser?.email
                    )
                )
            }
            layout.teamAName.setText("")
            layout.teamBName.setText("")
            layout.location.setText("")
            layout.date.setText("")
            layout.time.setText("")

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


     fun onResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    //fixture.logoTeamA
                    uploadImageView(app,logoTeamA)
                    uploadImageView(app,logoTeamB)
                   // logoTeamA.setImageBitmap(readImage(this, resultCode, data))
                }
            }
        }
    }


    /*

    override fun onResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> {
                if (data != null) {
                    Picasso.get().load(readImageUri(resultCode, data).toString())
                        .resize(180, 180)
                        .transform(CropCircleTransformation())
                        .into(navViewManager.getHeaderView(0).logoTeamA, object : Callback {
                            override fun onSuccess() {
                                // Drawable is ready
                                uploadImageView(app,navViewManager.getHeaderView(0).logoTeamA)
                            }
                            override fun onError(e: Exception) {}
                        })
                }
            }
        }
    }

     */

}
