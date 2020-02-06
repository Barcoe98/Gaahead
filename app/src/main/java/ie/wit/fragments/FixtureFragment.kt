package ie.wit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import ie.wit.R
import ie.wit.helpers.showImagePicker
import ie.wit.main.MainApp
import ie.wit.models.FixtureModel
import kotlinx.android.synthetic.main.fragment_fixture.*
import kotlinx.android.synthetic.main.fragment_fixture.view.*
import kotlinx.android.synthetic.main.home.*


class FixtureFragment : Fragment() {

    lateinit var app: MainApp
    var fixture = FixtureModel()
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

    fun setImgBtnListener( layout: View){

        layout.teamBLogoBtn.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }

        layout.teamALogoBtn.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }
    }

    fun setButtonListener( layout: View) {
        layout.addFixtureBtn.setOnClickListener {

            fixture.teamAName = teamAName.text.toString()
            fixture.teamBName = teamBName.text.toString()
            fixture.date = date.text.toString()
            fixture.time = time.text.toString()
            fixture.location = location.text.toString()


            when {
                fixture.teamAName.isEmpty() ->  Toast.makeText(app,R.string.enter_teamAName,Toast.LENGTH_LONG).show()
                fixture.teamBName.isEmpty() ->  Toast.makeText(app,R.string.enter_teamBName,Toast.LENGTH_LONG).show()
                fixture.date.isEmpty() ->  Toast.makeText(app,R.string.enter_date,Toast.LENGTH_LONG).show()
                fixture.time.isEmpty() ->  Toast.makeText(app,R.string.enter_time,Toast.LENGTH_LONG).show()
                fixture.location.isEmpty() ->  Toast.makeText(app,R.string.enter_location,Toast.LENGTH_LONG).show()

            else -> app.fixturesStore.create(fixture.copy())

            }

            fixture.teamAName = teamAName.setText("").toString()
            fixture.teamBName = teamBName.setText("").toString()
            fixture.location = location.setText("").toString()
            fixture.date = date.setText("").toString()
            fixture.time = time.setText("").toString()

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

}