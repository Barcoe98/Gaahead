package ie.wit.main

import android.app.Application
import android.util.Log
import ie.wit.models.FixtureJSONStore
import ie.wit.models.FixtureMemStore
import ie.wit.models.FixtureModel
import ie.wit.models.FixtureStore

class MainApp : Application() {

    lateinit var fixturesStore: FixtureStore

    override fun onCreate() {
        super.onCreate()
        fixturesStore = FixtureJSONStore(applicationContext)

        Log.v("Fixture","Gaahead App started")
    }
}