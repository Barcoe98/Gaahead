package ie.wit.fragments

import android.content.Intent
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ie.wit.R
import ie.wit.helpers.readImageFromPath
import ie.wit.helpers.showImagePicker
import ie.wit.main.MainApp
import ie.wit.models.FixtureModel
import kotlinx.android.synthetic.main.activity_fixture.*
import kotlinx.android.synthetic.main.fragment_fixture.*
import kotlinx.android.synthetic.main.fragment_fixture.addFixtureBtn
import kotlinx.android.synthetic.main.fragment_fixture.date
import kotlinx.android.synthetic.main.fragment_fixture.location
import kotlinx.android.synthetic.main.fragment_fixture.logoABtn
import kotlinx.android.synthetic.main.fragment_fixture.logoBBtn
import kotlinx.android.synthetic.main.fragment_fixture.logoTeamA
import kotlinx.android.synthetic.main.fragment_fixture.logoTeamB
import kotlinx.android.synthetic.main.fragment_fixture.teamAName
import kotlinx.android.synthetic.main.fragment_fixture.teamBName
import kotlinx.android.synthetic.main.fragment_fixture.time
import kotlinx.android.synthetic.main.fragment_fixture.view.*


class FixtureFragment : Fragment() {
/*
    lateinit var app: MainApp
    var fixture = FixtureModel()
    var edit = false
    private val imageRequest = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_fixture, container, false)
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

            fixture.teamAName = teamAName.text.toString()
            fixture.teamBName = teamBName.text.toString()
            fixture.date = date.text.toString()
            fixture.time = time.text.toString()
            fixture.location = location.text.toString()

            when {
                fixture.teamAName.isEmpty() -> Toast.makeText(app, R.string.enter_teamAName, Toast.LENGTH_LONG).show()
                fixture.teamBName.isEmpty() -> Toast.makeText(app, R.string.enter_teamBName, Toast.LENGTH_LONG).show()
                fixture.date.isEmpty() -> Toast.makeText(app, R.string.enter_date, Toast.LENGTH_LONG).show()
                fixture.time.isEmpty() -> Toast.makeText(app, R.string.enter_time, Toast.LENGTH_LONG).show()
                fixture.location.isEmpty() -> Toast.makeText(app, R.string.enter_location, Toast.LENGTH_LONG).show()

                else -> app.fixturesStore.create(fixture.copy())

            }
            fixture.teamAName = teamAName.setText("").toString()
            fixture.teamBName = teamBName.setText("").toString()
            fixture.location = location.setText("").toString()
            fixture.date = date.setText("").toString()
            fixture.time = time.setText("").toString()

        }

    }

    private fun setFixtureButtonListner(layout: View) {

        layout.addFixtureBtn.setOnClickListener {
            fixture.teamAName = teamAName.text.toString()
            fixture.teamBName = teamBName.text.toString()
            fixture.time = time.text.toString()
            fixture.date = date.text.toString()
            fixture.location = location.text.toString()

            if (fixture.teamAName.isNotEmpty()) {
                if (edit) {
                    app.fixturesStore.update(fixture.copy())
                } else {
                    app.fixturesStore.create(fixture.copy())
                }
                //info("Add Button Pressed. name: ${fixture.teamAName}")
                //setResult(AppCompatActivity.RESULT_OK)
                //fixa.loadFixtures()
                //finish()

            } else {
                Toast.makeText(app, R.string.enter_teamAName, Toast.LENGTH_LONG).show()
            }

            /*
            logoABtn.setOnClickListener {
                showImagePicker(this, IMAGE_REQUEST)
            }

            logoBBtn.setOnClickListener {
                showImagePicker(this, IMAGE_REQUEST)
            }

             */
        }
    }
}
/*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode){
            IMAGE_REQUEST -> {
                if (data !=null){
                    fixture.image = data.getData().toString()
                    logoTeamA.setImageBitmap(readImage(this, resultCode, data))
                }
            }
        }
    }

 */

 */

}