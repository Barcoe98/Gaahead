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
import kotlinx.android.synthetic.main.activity_fixture.logoTeamA
import kotlinx.android.synthetic.main.activity_fixture.logoTeamB
import kotlinx.android.synthetic.main.activity_fixture.teamAName
import kotlinx.android.synthetic.main.activity_fixture.teamBName
import kotlinx.android.synthetic.main.activity_result.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast


class FixtureActivity : AppCompatActivity(), AnkoLogger {

    var fixture = FixtureModel()
    lateinit var app: MainApp
    var edit = false
    val IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fixture)
        app = application as MainApp

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
                finish()

            } else {
                toast(R.string.hint_playerName)
            }

        }

        addToResultsBtn.setOnClickListener {

            startActivityForResult<ResultActivity>(0)
            finish()
            deleteCurrentFixture()
        }
    }

    private fun deleteCurrentFixture() {
        app.fixturesStore.remove(fixture)
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
                finish()
            }
            R.id.item_cancelFixture -> navigateTo(FixtureListFragment.newInstance())
        }
        return super.onOptionsItemSelected(item)
    }
}