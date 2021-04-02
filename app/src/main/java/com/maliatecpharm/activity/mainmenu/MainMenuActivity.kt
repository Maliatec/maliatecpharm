package com.maliatecpharm.activity.mainmenu

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.maliatecpharm.R
import com.maliatecpharm.activity.LogInActivity

import com.maliatecpharm.activity.Profile

class MainMenuActivity : AppCompatActivity()
{
    private var mDrawerToggle: ActionBarDrawerToggle? = null
    private lateinit var tvContent: TextView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var profile: TextView
    private lateinit var settings: TextView
    private lateinit var sync: TextView
    private lateinit var logout: TextView


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        setSupportActionBar(findViewById(R.id.toolbar))

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navController = findNavController(R.id.fragment)

        val appBarConfiguration = AppBarConfiguration(setOf(R.id.HomeFragment,R.id.MedicationsFragment,R.id.doctorsFragment,R.id.MoreFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)


        bottomNavigationView.setupWithNavController(navController)

        profile = findViewById(R.id.Profile)
        settings = findViewById(R.id.Settings)
        sync = findViewById(R.id.Sync)
        logout = findViewById(R.id.Logout)



        tvContent = findViewById(R.id.tv_content)
        drawerLayout = findViewById(R.id.drawer_layout)
        mDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        mDrawerToggle!!.syncState()

        setOnProfileClicked()
        setOnLogOutClicked()

    }

    fun update(view: View)
    {
        tvContent.text = (view as TextView).text
    }

    private fun setOnProfileClicked()
    {
        profile.setOnClickListener {
            startActivity(Intent(this, Profile::class.java))
        }
    }

    private fun setOnLogOutClicked()
    {
        logout.setOnClickListener {
            startActivity(Intent(this, LogInActivity::class.java))
        }
    }

}