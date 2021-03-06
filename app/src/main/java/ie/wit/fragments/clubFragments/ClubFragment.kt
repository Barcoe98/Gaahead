package ie.wit.fragments.clubFragments

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
import ie.wit.models.ClubModel
import ie.wit.utils.*
import kotlinx.android.synthetic.main.fragment_club.*
import kotlinx.android.synthetic.main.fragment_club.view.*
import kotlinx.android.synthetic.main.fragment_fixture.view.*

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.util.HashMap

open class ClubFragment : Fragment(), AnkoLogger{

    lateinit var app: MainApp
    lateinit var loader: AlertDialog
    val IMAGE_REQUEST = 1
    var favourite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_club, container, false)
        loader = createLoader(activity!!)
        activity?.title = getString(R.string.action_club_add)

        setButtonListener(root)
        setImgBtnListener(root)
        setFavouriteListener(root)


        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ClubFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    private fun setImgBtnListener(layout: View) {

        layout.logoBtn.setOnClickListener {
            showImagePickerFragment(this, IMAGE_REQUEST)
        }
    }

    private fun setButtonListener(layout: View) {
        layout.addClubBtn.setOnClickListener {

            val name = name.text.toString()
            val county = county.text.toString()
            val colours = colours.text.toString()
            val division = division.text.toString()
            val grounds = grounds.text.toString()
            val yearFounded = yearFounded.text.toString()
            val trophies = trophies.text.toString()
            val history = history.text.toString()

            when {
                layout.name.text.isEmpty() -> Toast.makeText(
                    app,
                    R.string.error_clubName,
                    Toast.LENGTH_LONG
                ).show()
                layout.county.text.isEmpty() -> Toast.makeText(
                    app,
                    R.string.error_county,
                    Toast.LENGTH_LONG
                ).show()
                layout.colours.text.isEmpty() -> Toast.makeText(
                    app,
                    R.string.error_colours,
                    Toast.LENGTH_LONG
                ).show()
                layout.grounds.text.isEmpty() -> Toast.makeText(
                    app,
                    R.string.error_grounds,
                    Toast.LENGTH_LONG
                ).show()
                layout.division.text.isEmpty() -> Toast.makeText(
                    app,
                    R.string.error_division,
                    Toast.LENGTH_LONG
                ).show()
                layout.history.text.isEmpty() -> Toast.makeText(
                    app,
                    R.string.error_history,
                    Toast.LENGTH_LONG
                ).show()
                layout.trophies.text.isEmpty() -> Toast.makeText(
                    app,
                    R.string.error_trophies,
                    Toast.LENGTH_LONG
                ).show()
                layout.yearFounded.text.isEmpty() -> Toast.makeText(
                    app,
                    R.string.error_yearFounded,
                    Toast.LENGTH_LONG
                ).show()
                else -> writeNewClub(
                    ClubModel(
                        name = name,
                        county = county,
                        colours = colours,
                        grounds = grounds,
                        division = division,
                        trophies = trophies,
                        history = history,
                        isfavourite = favourite,
                        yearFounded = yearFounded
                    )
                )
            }
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.homeFrame,
                    ClubListFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onPause() {
        super.onPause()
        app.database.child("clubs")
        //.removeEventListener(eventListener)
    }

    fun writeNewClub(club: ClubModel) {

        // Create new club at clubs
        showLoader(loader, "Adding club to Firebase")
        info("Firebase DB Reference : $app.database")
        val key = app.database.child("clubs").push().key
        if (key == null) {
            info("Firebase Error : Key Empty")
            return
        }
        club.uid = key
        val clubValues = club.toMap()

        val childUpdates = HashMap<String, Any>()
        childUpdates["/clubs/$key"] = clubValues

        app.database.updateChildren(childUpdates)
        hideLoader(loader)
    }

    fun setFavouriteListener (layout: View) {
        layout.clubFavImg.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                if (!favourite) {
                    layout.clubFavImg.setImageResource(R.drawable.ic_bookmark_gold)
                    favourite = true
                }
                else {
                    layout.clubFavImg.setImageResource(R.drawable.ic_bookmark_border_black_24dp)
                    favourite = false
                }
            }
        })
    }
}
