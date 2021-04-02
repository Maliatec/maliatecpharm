package com.maliatecpharm.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.maliatecpharm.R
import com.maliatecpharm.activity.mainmenu.MainMenuActivity
import com.maliatecpharm.activity.mainmenu.RegisterPage

class LogInActivity:AppCompatActivity()
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
        setContentView(R.layout.login_layout)

        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        logInButton = findViewById(R.id.loginBtn)
        registerButton = findViewById(R.id.register_button)
        remember = findViewById(R.id.remember_cb)
        forgotMyPass = findViewById(R.id.forgotmypass)
        loading = findViewById(R.id.loading)



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
            startActivity(Intent(this, RegisterPage::class.java))
        }
    }



}