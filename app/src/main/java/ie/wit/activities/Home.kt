package ie.wit.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import ie.wit.R
import ie.wit.fragments.FixtureFragment
import ie.wit.fragments.FixtureListFragment
import ie.wit.fragments.PlayerFragment
import ie.wit.fragments.TeamFragment
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.home.*

import org.jetbrains.anko.toast

class Home : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var ft: FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)
        setSupportActionBar(toolbar)


        navView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        ft = supportFragmentManager.beginTransaction()

        val fragment =  FixtureListFragment.newInstance()
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
            //R.id.nav_overview -> navigateTo(FixtureFragment.newInstance())
            //R.id.nav_add_player -> navigateTo(FixtureFragment.newInstance())
            R.id.nav_add_player -> navigateTo(PlayerFragment.newInstance())
            R.id.nav_team -> navigateTo(TeamFragment.newInstance())
            R.id.nav_fixture -> navigateTo(FixtureFragment.newInstance())
            R.id.nav_fixture_list -> navigateTo(FixtureListFragment.newInstance())


            else -> toast("You Selected Something Else")
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    /*
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_fixture -> toast("You Selected Add Fixture")
            R.id.action_result -> toast("You Selected Fixture List")
        }
        return super.onOptionsItemSelected(item)
    }
    */

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()
    }
}
