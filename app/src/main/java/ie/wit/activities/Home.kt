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
import ie.wit.fragments.*
import ie.wit.main.MainApp
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.manager_home.*
import kotlinx.android.synthetic.main.manager_home.drawerLayout
import kotlinx.android.synthetic.main.nav_header_home.view.*
import kotlinx.android.synthetic.main.supporter_home.*
import org.jetbrains.anko.startActivity

import org.jetbrains.anko.toast

class Home : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var ft: FragmentTransaction
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.supporter_home)
        setSupportActionBar(toolbar)
        app = application as MainApp

        navViewSupporter.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navViewSupporter.getHeaderView(0).nav_header_email.text = app.auth.currentUser?.email

        ft = supportFragmentManager.beginTransaction()
        val fragment =  InfoFragment.newInstance()
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
           // R.id.nav_info -> navigateTo(InfoFragment.newInstance())
            //R.id.nav_players -> navigateTo(PlayerListFragment.newInstance())
           // R.id.nav_team -> navigateTo(TeamFragment.newInstance())
            R.id.nav_fixture_list -> navigateTo(FixtureListFragment.newInstance())
            R.id.nav_fixture -> navigateTo(FixtureFragment.newInstance())
            //R.id.nav_result_list -> navigateTo(ResultListFragment.newInstance())
            R.id.nav_sign_out -> signOut()

            else -> toast("You Selected Something Else")
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_addFixture -> {
                //startActivityForResult<FixtureActivity>(0)
            }
            R.id.item_addResult -> {
                //startActivityForResult<ResultActivity>(0)
            }
            R.id.item_addPlayer -> {
                //startActivityForResult<PlayerActivity>(0)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //redundant
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

    private fun signOut() {
        app.auth.signOut()
        startActivity<Login>()
        finish()
    }
}
