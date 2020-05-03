package ie.wit.activities

import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.gms.location.LocationServices
import com.google.android.material.navigation.NavigationView
import ie.wit.R
import ie.wit.fragments.AppInfoFragment
import ie.wit.fragments.clubFragments.ClubListFragment
import ie.wit.fragments.fixtureFragments.FixtureAllFragment
import ie.wit.fragments.pinFragments.PinFragment
import ie.wit.fragments.pinFragments.PinMapFragment
import ie.wit.fragments.playerFragments.PlayerAllFragment
import ie.wit.fragments.resultFragments.ResultAllFragment
import ie.wit.main.MainApp
import ie.wit.utils.*
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.home_manager.drawerLayout
import kotlinx.android.synthetic.main.home_player.*
import kotlinx.android.synthetic.main.home_supporter.*
import kotlinx.android.synthetic.main.nav_header_home.view.*
import org.jetbrains.anko.startActivity

import org.jetbrains.anko.toast

class SupporterHome : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var ft: FragmentTransaction
    lateinit var app: MainApp
    val IMAGE_REQUEST = 1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_supporter)
        setSupportActionBar(toolbar)
        app = application as MainApp

        //Map
        app.locationClient = LocationServices.getFusedLocationProviderClient(this)

        if(checkLocationPermissions(this)) {
            setCurrentLocation(app)
        }
        //

        navViewSupporter.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        navViewSupporter.getHeaderView(0).nav_header_email.text = app.auth.currentUser?.email
        navViewSupporter.getHeaderView(0).nav_header_name.text = app.auth.currentUser?.displayName
        navViewSupporter.getHeaderView(0).imageView.setOnClickListener { showImagePicker(this,IMAGE_REQUEST) }
        checkExistingSupporterPhoto(app,this)


        ft = supportFragmentManager.beginTransaction()
        val fragment =  FixtureAllFragment.newInstance()
        ft.replace(R.id.homeFrame, fragment)
        ft.commit()


    }

    private fun navigateTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.homeFrame, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.nav_fixtures_all -> navigateTo(FixtureAllFragment.newInstance())

            R.id.nav_results_all -> navigateTo(ResultAllFragment.newInstance())

            R.id.nav_players_all -> navigateTo(PlayerAllFragment.newInstance())

            R.id.nav_clubs_all -> navigateTo(ClubListFragment.newInstance())

            R.id.nav_pin_add -> navigateTo(PinFragment.newInstance())

            R.id.nav_pins_all -> navigateTo(PinMapFragment.newInstance())

            R.id.nav_sign_out -> signOut()

            R.id.nav_info -> navigateTo(AppInfoFragment.newInstance())

            else -> toast("You Selected Something Else")
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()
    }

    private fun signOut() {
        app.auth.signOut()
        startActivity<Login>()
        finish()
    }

    //map
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (isPermissionGranted(requestCode, grantResults)) {
            setCurrentLocation(app)
        } else {
            // permissions denied, so use the default location
            app.currentLocation = Location("Default").apply {
                latitude = 52.245696
                longitude = -7.139102
            }
        }
        Log.v("Fixture", "Home LAT: ${app.currentLocation.latitude} LNG: ${app.currentLocation.longitude}")
    }
}


