package ie.wit.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import ie.wit.R
import ie.wit.main.MainApp
import ie.wit.models.ResultModel
import kotlinx.android.synthetic.main.activity_result.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast


class ResultActivity : AppCompatActivity(), AnkoLogger{

    var result = ResultModel()
    lateinit var app: MainApp
    var edit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        app = application as MainApp

        if (intent.hasExtra("result_edit")) {
            result = intent.extras?.getParcelable("result_edit")!!

            teamAName.setText(result.teamAName)
            teamBName.setText(result.teamBName)
            teamAScore.setText(result.teamAScore)
            teamBScore.setText(result.teamBScore)
            type.setText(result.type)

            addResultBtn.setText(R.string.btn_editResult)
            edit = true
        }

        addResultBtn.setOnClickListener {
            result.teamAName = teamAName.text.toString()
            result.teamBName = teamBName.text.toString()
            result.teamAScore = teamAScore.text.toString()
            result.teamBScore = teamBScore.text.toString()
            result.type = type.text.toString()

            when {

                result.teamAName.isEmpty() -> { toast(R.string.error_teamAName) }
                result.teamBName.isEmpty() -> { toast(R.string.error_teamBName) }
                result.teamAScore.isEmpty() -> { toast(R.string.error_teamAScore) }
                result.teamBScore.isEmpty() -> { toast(R.string.error_teamBScore) }
                result.type.isEmpty() -> { toast(R.string.error_type) }

                else -> {
                    if (edit) {
                        app.resultsStore.update(result.copy())
                    } else {
                        app.resultsStore.create(result.copy())
                    }
                    info("Add Button Pressed. name: ${result.teamAName}")
                    setResult(RESULT_OK)
                    finish()

                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_result, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_deleteResult -> {
                app.resultsStore.remove(result)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}