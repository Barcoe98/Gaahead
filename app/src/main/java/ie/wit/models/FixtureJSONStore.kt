package ie.wit.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import ie.wit.models.FixtureModel
import ie.wit.models.FixtureStore
import org.jetbrains.anko.AnkoLogger
import org.wit.helpers.*
import java.util.*

val JSON_FILE = "fixtures.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<FixtureModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class FixtureJSONStore : FixtureStore, AnkoLogger {

    val context: Context
    var fixtures = mutableListOf<FixtureModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<FixtureModel> {
        return fixtures
    }

    override fun create(fixture: FixtureModel) {
        fixture.fId = generateRandomId()
        fixtures.add(fixture)
        serialize()
    }


     fun update(fixture: FixtureModel) {
        // todo
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(fixtures, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        fixtures = Gson().fromJson(jsonString, listType)
    }
}