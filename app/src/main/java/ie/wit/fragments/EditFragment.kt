package ie.wit.fragments

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
import ie.wit.helpers.createLoader
import ie.wit.helpers.hideLoader
import ie.wit.helpers.showLoader
import ie.wit.main.MainApp
import ie.wit.models.FixtureModel
import kotlinx.android.synthetic.main.fragment_edit_fixture.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class EditFragment : Fragment(), AnkoLogger {

    lateinit var app: MainApp
    lateinit var loader : AlertDialog
    lateinit var root: View
    var editFixture: FixtureModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp

        arguments?.let {
            editFixture = it.getParcelable("editfixture")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_edit_fixture, container, false)
        activity?.title = getString(R.string.action_edit)
        loader = createLoader(activity!!)

        root.editTeamAName.setText(editFixture!!.teamAName)
        root.editTeamBName.setText(editFixture!!.teamBName)
        root.editDate.setText(editFixture!!.date)
        root.editTime.setText(editFixture!!.time.toString())
        root.editLocation.setText(editFixture!!.location)

        root.editFixtureBtn.setOnClickListener {
            showLoader(loader, "Updating Fixture on Server...")
            updateFixtureData()
            updateFixture(editFixture!!.uid, editFixture!!)
            updateUserFixture(app.auth.currentUser!!.uid,
                               editFixture!!.uid, editFixture!!)
        }

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance(fixture: FixtureModel) =
            EditFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("editfixture",fixture)
                }
            }
    }

    fun updateFixtureData() {

        editFixture!!.teamAName = root.editTeamAName.text.toString()
        editFixture!!.teamBName = root.editTeamBName.text.toString()
        editFixture!!.date = root.editDate.text.toString()
        editFixture!!.time = root.editTime.text.toString()
        editFixture!!.location = root.editLocation.text.toString()
    }

    fun updateUserFixture(userId: String, uid: String?, fixture: FixtureModel) {
        app.database.child("user-fixtures").child(userId).child(uid!!)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.setValue(fixture)
                        activity!!.supportFragmentManager.beginTransaction()
                        .replace(R.id.homeFrame, FixtureListFragment.newInstance())
                        .addToBackStack(null)
                        .commit()
                        hideLoader(loader)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        info("Firebase Donation error : ${error.message}")
                    }
                })
    }

    fun updateFixture(uid: String?, fixture: FixtureModel) {
        app.database.child("fixtures").child(uid!!)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.setValue(fixture)
                        hideLoader(loader)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        info("Firebase Fixture error : ${error.message}")
                    }
                })
    }
}

