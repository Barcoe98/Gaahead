package ie.wit.fragments.resultFragments

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
import ie.wit.models.ResultModel
import ie.wit.utils.createLoader
import ie.wit.utils.hideLoader
import ie.wit.utils.showLoader
import kotlinx.android.synthetic.main.fragment_edit_fixture.view.*
import kotlinx.android.synthetic.main.fragment_edit_result.view.*
import kotlinx.android.synthetic.main.fragment_edit_result.view.editTeamAName
import kotlinx.android.synthetic.main.fragment_edit_result.view.editTeamBName
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class EditResultFragment : Fragment(), AnkoLogger {

    lateinit var app: MainApp
    lateinit var loader : AlertDialog
    lateinit var root: View
    var editResult: ResultModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp

        arguments?.let {
            editResult = it.getParcelable("editresult")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_edit_result, container, false)
        activity?.title = getString(R.string.action_result_edit)
        loader = createLoader(activity!!)

        //fiture to reult help
        root.editTeamAName.setText(editResult!!.teamAName)
        root.editTeamAScore.setText(editResult!!.teamAScore)
        root.editTeamBName.setText(editResult!!.teamBName)
        root.editTeamBScore.setText(editResult!!.teamBScore)
        root.editRDate.setText(editResult!!.date)
        root.editCompetition.setText(editResult!!.competition)

        root.editResultBtn.setOnClickListener {
            showLoader(loader, "Updating Result on Server...")
            updateResultData()
            updateResult(editResult!!.uid, editResult!!)
            updateUserResult(app.auth.currentUser!!.uid,
                               editResult!!.uid, editResult!!)
        }

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance(result: ResultModel) =
            EditResultFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("editresult",result)
                }
            }
    }

    fun updateResultData() {

        editResult!!.teamAName = root.editTeamAName.text.toString()
        editResult!!.teamBName = root.editTeamBName.text.toString()
        editResult!!.teamAScore = root.editTeamAScore.text.toString()
        editResult!!.teamBScore = root.editTeamBScore.text.toString()
        editResult!!.date = root.editDate.text.toString()
        editResult!!.competition = root.editCompetition.text.toString()
    }

    fun updateUserResult(userId: String, uid: String?, result: ResultModel) {
        app.database.child("user-results").child(userId).child(uid!!)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.setValue(result)
                        activity!!.supportFragmentManager.beginTransaction()
                        .replace(R.id.homeFrame,
                            ResultListFragment.newInstance()
                        )
                        .addToBackStack(null)
                        .commit()
                        hideLoader(loader)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        info("Firebase Result error : ${error.message}")
                    }
                })
    }

    fun updateResult(uid: String?, result: ResultModel) {
        app.database.child("results").child(uid!!)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.setValue(result)
                        hideLoader(loader)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        info("Firebase Result error : ${error.message}")
                    }
                })
    }
}

