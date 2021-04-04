package com.maliatecpharm.activity.mainmenu.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.maliatecpharm.R

class Activity_LogIn:AppCompatActivity()
{
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

    }

    private fun setOnButtonClicked()
    {
        logInButton.setOnClickListener {
            startActivity(Intent(this, MainMenuActivity::class.java))
        }
    }

    private fun setOnBtnClicked()
    {
        registerButton.setOnClickListener {
            startActivity(Intent(this, Activity_RegisterPage::class.java))
        }
    }


}