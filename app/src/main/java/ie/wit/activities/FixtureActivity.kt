package ie.wit.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ie.wit.R
import ie.wit.fragments.FixtureListFragment
import ie.wit.helpers.readImage
import ie.wit.helpers.readImageFromPath
import ie.wit.helpers.showImagePicker
import ie.wit.main.MainApp
import ie.wit.models.FixtureModel
import kotlinx.android.synthetic.main.activity_fixture.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast


class FixtureActivity : AppCompatActivity(), AnkoLogger {

    var fixture = FixtureModel()
    lateinit var app: MainApp
    //lateinit var fixa: FixtureListFragment
    var edit = false
    val IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fixture)
        app = application as MainApp
        //fixa = application as FixtureListFragment


        if (intent.hasExtra("fixture_edit")) {
            fixture = intent.extras?.getParcelable("fixture_edit")!!
            teamAName.setText(fixture.teamAName)
            teamBName.setText(fixture.teamBName)
            date.setText(fixture.date)
            time.setText(fixture.time)
            location.setText(fixture.location)

            logoTeamA.setImageBitmap(readImageFromPath(this, fixture.logoA))
            logoTeamB.setImageBitmap(readImageFromPath(this, fixture.logoB))
            addFixtureBtn.setText(R.string.btn_editFixture)
            edit = true
        }

        addFixtureBtn.setOnClickListener {
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
                info("Add Button Pressed. name: ${fixture.teamAName}")
                setResult(RESULT_OK)
                //fixa.loadFixtures()
                finish()

            } else {
                toast(R.string.hint_playerName)
            }
            logoABtn.setOnClickListener {
                showImagePicker(this, IMAGE_REQUEST)
            }

            logoBBtn.setOnClickListener {
                showImagePicker(this, IMAGE_REQUEST)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode){
            IMAGE_REQUEST -> {
                if (data !=null){
                    fixture.logoA = data.data.toString()
                    logoTeamA.setImageBitmap(readImage(this, resultCode, data))
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_fixture, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun navigateTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.homeFrame, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_deleteFixture -> {
                app.fixturesStore.remove(fixture)
                //fRecyclerView.adapter?.notifyDataSetChanged()
                //navigateTo(FixtureListFragment.newInstance())
                //navigateTo(FixtureListFragment)
                //fixa.loadFixtures()
                finish()
            }
            R.id.item_cancelFixture -> navigateTo(FixtureListFragment.newInstance())
            R.id.item_addFixture -> startActivityForResult<FixtureActivity>(0)

        }
        return super.onOptionsItemSelected(item)
    }
}