package com.maliatecpharm.activity.mainmenu.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.maliatecpharm.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ActivityMainMenu : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener
{
    private val preferences: SharedPreferences by lazy {
        getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
    }
    private lateinit var toolbar: Toolbar
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerNavigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        toolbar = findViewById(R.id.toolbar)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navController = findNavController(R.id.fragment)

        val appBarConfiguration = AppBarConfiguration(setOf(R.id.HomeFragment, R.id.MedicationsFragment, R.id.fragmentListDoctors, R.id.vitalSignsFragment, R.id.MoreFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)

        drawer = findViewById(R.id.drawer_layout)
        toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)

        drawerNavigationView = findViewById(R.id.nav_view)
        drawerNavigationView.setNavigationItemSelectedListener(this)
    }
    override fun onPostCreate(savedInstanceState: Bundle?)
    {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }
    override fun onConfigurationChanged(newConfig: Configuration)
    {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        if (toggle.onOptionsItemSelected(item))
        {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean
    {
        when (item.itemId)
        {
            R.id.profile ->
            {
                startActivity(Intent(this, ActivityProfile::class.java))

            }
            R.id.medfriend ->
            {
                startActivity(Intent(this, ActivityMedFriend::class.java))
            }
            R.id.settings ->
            {
                startActivity(Intent(this, ActivitySettings::class.java))
            }
            R.id.logout ->
            {
                logoutUser()
            }
        }
        return true
    }
    private fun logoutUser()
    {
        lifecycleScope.launch(Dispatchers.IO) {
            val loggedInKey = getString(R.string.is_logged_in)
            preferences.edit().putString(loggedInKey, "").commit()
            withContext(Dispatchers.Main) {
                startActivity(Intent(this@ActivityMainMenu, ActivityLogIn::class.java))
                finish()
            }
        }
    }
    override fun onBackPressed()
    {
        if (!didDrawerClose())
        super.onBackPressed()
    }
    private fun didDrawerClose() = if (drawer.isDrawerOpen(GravityCompat.START))
    {
        drawer.close()
        true
    }
    else false
}
