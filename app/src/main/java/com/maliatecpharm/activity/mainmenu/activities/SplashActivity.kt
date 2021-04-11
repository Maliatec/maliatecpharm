package com.maliatecpharm.activity.mainmenu.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.maliatecpharm.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashActivity : AppCompatActivity()
{

    private val preferences: SharedPreferences by lazy {
        getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        determineNavigation()
    }

    private fun determineNavigation(){
        lifecycleScope.launch(Dispatchers.IO) {
            val loggedInKey = getString(R.string.is_logged_in)
            val loggedInEmail = preferences.getString(loggedInKey, "") ?: ""

            withContext(Dispatchers.Main) {
                if (loggedInEmail.isEmpty())
                {
                    startActivity(Intent(this@SplashActivity, ActivityLogIn::class.java))
                }
                else
                {
                    startActivity(Intent(this@SplashActivity, ActivityMainMenu::class.java))
                }
                finish()
            }
        }
    }
}