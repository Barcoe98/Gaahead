package ie.wit.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import ie.wit.helpers.exists
import ie.wit.helpers.read
import ie.wit.helpers.write
import org.jetbrains.anko.AnkoLogger
import java.util.*

val player_JSON_FILE = "players.json"
val playerGsonBuilder = GsonBuilder().setPrettyPrinting().create()
val playerListType = object : TypeToken<ArrayList<PlayerModel>>() {}.type

fun generateRandomPlayerId(): Long {
    return Random().nextLong()
}

class PlayerJSONStore : PlayerStore, AnkoLogger {

    val context: Context
    var players = mutableListOf<PlayerModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, player_JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<PlayerModel> {
        return players
    }

    override fun create(player: PlayerModel) {
        player.playerId = generateRandomPlayerId()
        players.add(player)
        serialize()
    }

    override fun update(player: PlayerModel) {

        val playersList = findAll() as ArrayList<PlayerModel>
        var foundPlayer: PlayerModel? = playersList.find { p -> p.playerId == player.playerId }
        if (foundPlayer != null) {
            foundPlayer.playerName = player.playerName
            foundPlayer.playerAge = player.playerAge
            foundPlayer.playerPosition = player.playerPosition
            foundPlayer.playerHeight = player.playerHeight
            foundPlayer.playerWeight = player.playerWeight
        }
        serialize()
    }

    override fun remove(player: PlayerModel) {
        players.remove(player)
        serialize()
    }

    private fun serialize() {
        val jsonString = playerGsonBuilder.toJson(players, playerListType)
        write(context, player_JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, player_JSON_FILE)
        players = Gson().fromJson(jsonString, playerListType)
    }

}
