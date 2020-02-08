package ie.wit.activities

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import ie.wit.R
import ie.wit.adapters.FixtureAdapter
import ie.wit.fragments.*
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.fragment_fixture_list.*
import kotlinx.android.synthetic.main.home.*
import org.jetbrains.anko.toast


class Home : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    //lateinit var adapter: FixtureAdapter
    lateinit var ft: FragmentTransaction
    var searchView: SearchView?=null


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

        //hideNavItems()

    }

    private fun navigateTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.homeFrame, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_overview -> navigateTo(OverviewFragment.newInstance())
            //R.id.nav_add_player -> navigateTo(FixtureFragment.newInstance())
            R.id.nav_add_player -> navigateTo(PlayerFragment.newInstance())
            R.id.nav_team -> navigateTo(TeamFragment.newInstance())
            R.id.nav_fixture -> navigateTo(FixtureFragment.newInstance())
            R.id.nav_fixtures -> navigateTo(FixtureListFragment.newInstance())


            else -> toast("You Selected Something Else")
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_search -> true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        lateinit var adapter: FixtureAdapter
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView= menu.findItem(R.id.action_search).actionView as SearchView
        searchView!!.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        searchView!!.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                adapter.filter.filter(p0)
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                adapter.filter.filter(p0)
                return false
            }
        })


        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()
    }


    fun hideNavItems(){

        val navigationView: NavigationView  = findViewById(R.id.nav_team)
        val navMenu: Menu = navigationView.menu
        navMenu.findItem(R.id.nav_team).isVisible = false
    }

}
