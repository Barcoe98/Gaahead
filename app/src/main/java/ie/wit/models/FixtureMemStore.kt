package ie.wit.models

import android.util.Log

var lastfId = 0L

internal fun getfId(): Long {
    return lastfId++
}

class FixtureMemStore : FixtureStore {

    val fixtures = ArrayList<FixtureModel>()

    override fun findAll(): List<FixtureModel> {
        return fixtures
    }

    override fun findById(id:Long) : FixtureModel? {
        val foundFixture: FixtureModel? = fixtures.find { it.fId == id }
        return foundFixture
    }

    override fun create(fixture: FixtureModel) {
        fixture.fId = getfId()
        fixtures.add(fixture)
        logAll()
    }

    fun logAll() {
        Log.v("Fixture","** Fixtures List **")
        fixtures.forEach { Log.v("Fixture","${it}") }
    }
}