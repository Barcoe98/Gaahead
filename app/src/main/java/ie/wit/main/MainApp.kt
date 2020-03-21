package ie.wit.main

import android.app.Application
import android.util.Log
import ie.wit.models.*

class MainApp : Application() {

    //lateinit var fixturesStore: FixtureStore
    //lateinit var resultsStore: ResultStore
    //lateinit var playersStore: PlayerStore

    override fun onCreate() {
        super.onCreate()
        //fixturesStore = FixtureJSONStore(applicationContext)
        ///resultsStore = ResultJSONStore(applicationContext)
        //playersStore = PlayerJSONStore(applicationContext)


        Log.v("Fixture","Gaahead App started")
    }
}