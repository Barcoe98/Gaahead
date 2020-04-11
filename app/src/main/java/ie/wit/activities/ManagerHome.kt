package ie.wit.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import ie.wit.R
import ie.wit.fragments.playerFragments.PlayerAllFragment
import ie.wit.fragments.playerFragments.PlayerListFragment
import ie.wit.fragments.resultFragments.ResultAllFragment
import ie.wit.fragments.resultFragments.ResultFragment
import ie.wit.fragments.resultFragments.ResultListFragment
import ie.wit.fragments.clubFragments.ClubFragment
import ie.wit.fragments.fixtureFragments.FixtureAllFragment
import ie.wit.fragments.fixtureFragments.FixtureListFragment
import ie.wit.fragments.playerFragments.PlayerFragment
import ie.wit.main.MainApp
import ie.wit.utils.readImageUri
import ie.wit.utils.showImagePicker
import ie.wit.utils.uploadImageView
import ie.wit.utils.writeImageRef
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.home_manager.*
import kotlinx.android.synthetic.main.home_manager.drawerLayout
import kotlinx.android.synthetic.main.nav_header_home.view.*
import org.jetbrains.anko.startActivity

import org.jetbrains.anko.toast

class ManagerHome : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var ft: FragmentTransaction
    lateinit var app: MainApp
    val IMAGE_REQUEST = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_manager)
        setSupportActionBar(toolbar)
        app = application as MainApp

        navViewManager.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navViewManager.getHeaderView(0).nav_header_email.text = app.auth.currentUser?.email
        navViewManager.getHeaderView(0).nav_header_name.text = app.auth.currentUser?.displayName
        navViewManager.getHeaderView(0).imageView.setOnClickListener { showImagePicker(this,IMAGE_REQUEST) }

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
            // R.id.nav_team_info -> navigateTo(TeamInfoFragment.newInstance())
            //R.id.nav_team -> navigateTo(.newInstance())

            R.id.nav_add_fixture -> navigateTo(ClubFragment.newInstance())
            R.id.nav_fixture_list -> navigateTo(FixtureListFragment.newInstance())
            R.id.nav_fixture_all -> navigateTo(FixtureAllFragment.newInstance())

            R.id.nav_add_result -> navigateTo(ResultFragment.newInstance())
            R.id.nav_result_list -> navigateTo(ResultListFragment.newInstance())
            R.id.nav_results_all -> navigateTo(ResultAllFragment.newInstance())

            R.id.nav_add_player -> navigateTo(PlayerFragment.newInstance())
            R.id.nav_player_list -> navigateTo(PlayerListFragment.newInstance())
            R.id.nav_player_all -> navigateTo(PlayerAllFragment.newInstance())

            R.id.nav_sign_out -> signOut()

            else -> toast("You Selected Something Else")
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    /*
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
     */


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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    writeImageRef(app, readImageUri(resultCode, data).toString())
                    Picasso.get().load(readImageUri(resultCode, data).toString())
                        .resize(180, 180)
                        .transform(CropCircleTransformation())
                        .into(navViewManager.getHeaderView(0).imageView, object : Callback {
                            override fun onSuccess() {
                                // Drawable is ready
                                uploadImageView(app,navViewManager.getHeaderView(0).imageView)
                            }
                            override fun onError(e: Exception) {}
                        })
                }
            }
        }
    }
}


