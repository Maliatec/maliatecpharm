package com.maliatecpharm.activity.mainmenu.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.PatternsCompat.EMAIL_ADDRESS
import androidx.lifecycle.lifecycleScope
import com.maliatecpharm.R
import com.maliatecpharm.activity.mainmenu.data.AppDataBase
import com.maliatecpharm.activity.mainmenu.data.UserDao
import com.maliatecpharm.activity.mainmenu.data.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ActivityLogIn:AppCompatActivity()
{

    private val preferences: SharedPreferences by lazy {
        getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
    }

    private val userDao: UserDao by lazy {
        AppDataBase.getDataBase(this).userDao()
    }
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var logInButton: Button
    private lateinit var registerButton: Button
    private lateinit var remember: CheckBox
    private lateinit var forgotMyPass: Button
    private lateinit var loading: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        username = findViewById(R.id.edittext_username)
        password = findViewById(R.id.edittext_password)
        logInButton = findViewById(R.id.button_loginBtn)
        registerButton = findViewById(R.id.button_registerButton)
        remember = findViewById(R.id.checkbox_remember)
        forgotMyPass = findViewById(R.id.button_forgotMyPassBtn)
        loading = findViewById(R.id.progressbar_loading)

        setOnButtonClicked()
        setOnBtnClicked()
        emailValidation()

    }

    private fun setOnButtonClicked()
    {
        logInButton.setOnClickListener {
            val email = username.text.toString()
            val pass = password.text.toString()

            lifecycleScope.launch(Dispatchers.IO) {
                val user = userDao.getUserByEmailAndPass(
                    email = email, password = pass
                ).firstOrNull()

                if (user == null)
                {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@ActivityLogIn, getString(R.string.wrong_credentials), Toast.LENGTH_SHORT).show()
                    }
                }

                else {
                    val loggedInKey = getString(R.string.is_logged_in)
                    preferences.edit().putString(loggedInKey, user.email).commit()
                    withContext(Dispatchers.Main){
                        startActivity(Intent(this@ActivityLogIn, ActivityMainMenu::class.java))
                    }
                }
            }
        }
    }

    private fun setOnBtnClicked()
    {
        registerButton.setOnClickListener {
            startActivity(Intent(this, ActivityRegisterPage::class.java))
        }
    }

    private fun emailValidation(){
        username.addTextChangedListener(object :TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
            {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {
                logInButton.isEnabled = EMAIL_ADDRESS.matcher(username.text.toString()).matches()
//                username.setError("Invalid Email")
            }
            override fun afterTextChanged(s: Editable?)
            {
            }
        })
    }

}