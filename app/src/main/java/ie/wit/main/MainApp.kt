package ie.wit.main

import android.app.Application
import android.util.Log
import ie.wit.models.*

class MainApp : Application() {

    lateinit var fixturesStore: FixtureStore
    lateinit var resultsStore: ResultStore

    override fun onCreate() {
        super.onCreate()
        fixturesStore = FixtureJSONStore(applicationContext)
        resultsStore = ResultJSONStore(applicationContext)


        Log.v("Fixture","Gaahead App started")
    }
}