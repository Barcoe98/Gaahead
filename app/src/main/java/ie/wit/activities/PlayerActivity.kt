package ie.wit.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ie.wit.R
import ie.wit.main.MainApp
import ie.wit.models.PlayerModel
import kotlinx.android.synthetic.main.activity_player.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast

/*
class PlayerActivity : AppCompatActivity(), AnkoLogger {

    var player = PlayerModel()
    lateinit var app: MainApp
    var edit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        app = application as MainApp

        if (intent.hasExtra("player_edit")) {
            player = intent.extras?.getParcelable("player_edit")!!

            playerName.setText(player.playerName)
            playerAge.setText(player.playerAge)
            playerPosition.setText(player.playerPosition)
            playerWeight.setText(player.playerWeight)
            playerHeight.setText(player.playerHeight)

            addPlayerBtn.setText(R.string.btn_editPlayer)
            edit = true
        }

        addPlayerBtn.setOnClickListener {

            player.playerName = playerName.text.toString()
            player.playerAge = playerAge.text.toString()
            player.playerPosition = playerPosition.text.toString()
            player.playerHeight = playerHeight.text.toString()
            player.playerWeight = playerWeight.text.toString()

            when {

                player.playerName.isEmpty() -> { toast(R.string.error_playerName) }
                player.playerAge.isEmpty() -> { toast(R.string.error_playerAge) }
                player.playerPosition.isEmpty() -> { toast(R.string.error_playerPosition) }
                player.playerHeight.isEmpty() -> { toast(R.string.error_playerHeight) }
                player.playerWeight.isEmpty() -> { toast(R.string.error_playerWeight) }

                else -> {
                    if (edit) {
                        app.playersStore.update(player.copy())
                    } else {
                        app.playersStore.create(player.copy())
                    }
                    info("Add Button Pressed. name: ${player.playerName}")
                    setResult(RESULT_OK)
                    finish()

                }
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_player, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_deletePlayer -> {
                app.playersStore.remove(player)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}



 */