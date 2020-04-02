package ie.wit.main

import android.app.Application
import android.net.Uri
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import ie.wit.R
import ie.wit.models.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {

    lateinit var auth: FirebaseAuth
    lateinit var database: DatabaseReference
    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var storage: StorageReference
    lateinit var userImage: Uri



    override fun onCreate() {
        super.onCreate()
        info("GAAhead App started")
    }
}