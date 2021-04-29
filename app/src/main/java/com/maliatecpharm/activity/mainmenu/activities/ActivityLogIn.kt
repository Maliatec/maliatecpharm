package com.maliatecpharm.activity.mainmenu.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.maliatecpharm.R
import com.maliatecpharm.activity.mainmenu.data.AppDataBase
import com.maliatecpharm.activity.mainmenu.data.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ActivityLogIn : AppCompatActivity()
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
    val MIN_PASSWORD_LENGTH = 6
    private lateinit var resetMyPass: Button
    private lateinit var forgotMyPass: Button


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        username = findViewById(R.id.edittext_username)
        password = findViewById(R.id.edittext_password)
        logInButton = findViewById(R.id.button_loginBtn)
        registerButton = findViewById(R.id.bt_signup)
        resetMyPass = findViewById(R.id.button_resetMyPassBtn)
        forgotMyPass = findViewById(R.id.button_forgotMyPassBtn)

        setOnButtonClicked()
        setOnBtnClicked()
        setOnResetClicked()
        setOnForgotClicked()

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
                    else
                    {
                        val loggedInKey = getString(R.string.is_logged_in)
                        preferences.edit().putString(loggedInKey, user.email).commit()
                        withContext(Dispatchers.Main) {
                            startActivity(Intent(this@ActivityLogIn, ActivityMainMenu::class.java))
                            finish()
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

    fun validateInput(): Boolean
    {
        if (username.text.toString() == "")
        {
            username.error = "Please Enter Email"
            return false
        }
        if (password.text.toString() == "")
        {
            password.error = "Please Enter Password"
            return false
        }

        if (!isEmailValid(username.text.toString()))
        {
            username.error = "Please Enter Valid Email"
            return false
        }

        if (password.text.length < MIN_PASSWORD_LENGTH)
        {
            password.error = "Password Length must be more than " + MIN_PASSWORD_LENGTH + "characters"
            return false
        }
        return true
    }

    fun isEmailValid(email: String?): Boolean
    {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun setOnResetClicked()
    {
        resetMyPass.setOnClickListener {
            startActivity(Intent(this, ActivityResetPassword::class.java))
        }
    }

    private fun setOnForgotClicked()
    {
        forgotMyPass.setOnClickListener {
            startActivity(Intent(this, ActivityForgotMyPass::class.java))
        }
    }

}