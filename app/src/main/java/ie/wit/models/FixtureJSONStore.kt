package ie.wit.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.helpers.*
import java.util.*

val JSON_FILE = "fixtures.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<ArrayList<FixtureModel>>() {}.type

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

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(fixtures, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        fixtures = Gson().fromJson(jsonString, listType)
    }

    override fun update(fixture: FixtureModel) {

        val fixturesList = findAll() as ArrayList<FixtureModel>
        var foundFixture: FixtureModel? = fixturesList.find { p -> p.fId == fixture.fId }
        if (foundFixture != null) {
            foundFixture.teamAName = fixture.teamAName
            foundFixture.teamBName = fixture.teamBName
            foundFixture.time = fixture.time
            foundFixture.date = fixture.date
            foundFixture.location = fixture.location

        }
        serialize()
    }

    override fun remove(fixture: FixtureModel) {
        fixtures.remove(fixture)
        serialize()
    }
}
