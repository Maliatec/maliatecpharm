package com.maliatecpharm.activity.mainmenu.activities

import android.os.Bundle
import android.util.Patterns.EMAIL_ADDRESS
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.maliatecpharm.R

class ActivityForgotMyPass: AppCompatActivity()
{
    lateinit var etEmail: EditText

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_mypass)

        viewInitializations()
        validateInput()
    }

    fun viewInitializations()
    {
        etEmail = findViewById(R.id.et_email)
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

}