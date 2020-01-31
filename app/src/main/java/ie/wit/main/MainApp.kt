package ie.wit.main

import android.app.Application
import android.util.Log

class MainApp : Application() {


    override fun onCreate() {
        super.onCreate()
        Log.v("Fixture","Gaahead App started")
    }
}