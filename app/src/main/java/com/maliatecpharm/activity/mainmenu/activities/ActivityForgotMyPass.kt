package com.maliatecpharm.activity.mainmenu.activities

import android.content.Intent
import android.os.Bundle
import android.util.Patterns.EMAIL_ADDRESS
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.maliatecpharm.R

class ActivityForgotMyPass: AppCompatActivity()
{
    lateinit var etEmail: EditText
    private lateinit var login: Button
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_mypass)

        viewInitializations()
        validateInput()
        setOnBtnClicked()
    }
    fun viewInitializations()
    {
        etEmail = findViewById(R.id.et_email)
        login = findViewById(R.id.bt_signup)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun validateInput(): Boolean
    {
        if (etEmail.text.toString().equals(""))
        {
            etEmail.setError("Please Enter Email")
            return false
        }
        if (!isEmailValid(etEmail.text.toString()))
        {
            etEmail.setError("Please Enter Valid Email")
            return false
        }
        return true
    }

    fun isEmailValid(email: String): Boolean
    {
        return EMAIL_ADDRESS.matcher(email).matches()
    }
    private fun setOnBtnClicked()
    {
        login.setOnClickListener {
            startActivity(Intent(this, ActivityMainMenu::class.java))
        }
    }
}