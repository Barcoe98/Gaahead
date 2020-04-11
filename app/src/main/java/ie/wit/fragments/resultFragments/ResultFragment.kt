package ie.wit.fragments.resultFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import ie.wit.R
import ie.wit.main.MainApp
import ie.wit.models.ResultModel
import ie.wit.utils.*
import kotlinx.android.synthetic.main.fragment_result.*
import kotlinx.android.synthetic.main.fragment_result.teamAName
import kotlinx.android.synthetic.main.fragment_result.teamBName
import kotlinx.android.synthetic.main.fragment_result.view.*
import kotlinx.android.synthetic.main.fragment_result.view.logoABtn
import kotlinx.android.synthetic.main.fragment_result.view.logoBBtn
import kotlinx.android.synthetic.main.fragment_result.view.teamAName
import kotlinx.android.synthetic.main.fragment_result.view.teamBName
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.util.HashMap

open class ResultFragment : Fragment(), AnkoLogger {

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

        val root = inflater.inflate(R.layout.fragment_result, container, false)
        loader = createLoader(activity!!)
        activity?.title = getString(R.string.results_title)

        setButtonListener(root)

        return root
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            ResultFragment().apply {
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
        layout.addResultBtn.setOnClickListener {

            val teamAName = teamAName.text.toString()
            val teamBName = teamBName.text.toString()
            val teamAScore = teamAScore.text.toString()
            val teamBScore = teamBScore.text.toString()
            val date = date.text.toString()
            val competition = competition.text.toString()

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
                layout.teamBScore.text.isEmpty() -> Toast.makeText(
                    app,
                    R.string.error_date,
                    Toast.LENGTH_LONG
                ).show()
                layout.teamAScore.text.isEmpty() -> Toast.makeText(
                    app,
                    R.string.error_time,
                    Toast.LENGTH_LONG
                ).show()
                layout.date.text.isEmpty() -> Toast.makeText(
                    app,
                    R.string.error_time,
                    Toast.LENGTH_LONG
                ).show()
                layout.competition.text.isEmpty() -> Toast.makeText(
                    app,
                    R.string.error_competition,
                    Toast.LENGTH_LONG
                ).show()
                else -> writeNewResult(
                    ResultModel(
                        teamAName = teamAName,
                        teamAScore = teamAScore,
                        teamBName = teamBName,
                        teamBScore = teamBScore,
                        date = date,
                        competition = competition,
                        email = app.auth.currentUser?.email
                    )
                )
            }
            layout.teamAName.setText("")
            layout.teamBName.setText("")
            layout.teamAScore.setText("")
            layout.teamBScore.setText("")
            layout.date.setText("")
            layout.competition.setText("")

        }
    }


    override fun onPause() {
        super.onPause()
        app.database.child("user-results")
            .child(app.auth.currentUser!!.uid)
            //.removeEventListener(eventListener)
    }

    fun writeNewResult(result: ResultModel) {

        // Create new result at /results & /results/$uid
        showLoader(loader, "Adding result to Firebase")
        info("Firebase DB Reference : $app.database")
        val uid = app.auth.currentUser!!.uid
        val key = app.database.child("results").push().key
        if (key == null) {
            info("Firebase Error : Key Empty")
            return
        }
        result.uid = key
        val resultValues = result.toMap()

        val childUpdates = HashMap<String, Any>()
        childUpdates["/results/$key"] = resultValues
        childUpdates["/user-results/$uid/$key"] = resultValues

        app.database.updateChildren(childUpdates)
        hideLoader(loader)
    }

/*
     fun onResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    //fixture.logoTeamA
                    uploadImageView(app,`@+id/TeamALogo`)
                    uploadImageView(app,`@+id/TeamBLogo`)
                   // logoTeamA.setImageBitmap(readImage(this, resultCode, data))
                }
            }
        }
    }

 */

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
