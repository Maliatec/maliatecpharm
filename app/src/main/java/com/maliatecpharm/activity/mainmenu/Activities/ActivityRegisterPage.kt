package com.maliatecpharm.activity.mainmenu.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.maliatecpharm.R

class ActivityRegisterPage:AppCompatActivity()
{

    private lateinit var enterMail:EditText
    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var password: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var saveButton :Button


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registerpage)


        enterMail = findViewById(R.id.edittext_enterEmail)
        firstName = findViewById(R.id.edittext_firstNAME)
        lastName = findViewById(R.id.edittext_lastNAME)
        password = findViewById(R.id.edittext_Password)
        confirmPassword = findViewById(R.id.edittext_confirmPassword)
        saveButton = findViewById(R.id.button_registerBUTTON)



        setOnButtonClicked()


    }

    private fun setOnButtonClicked()
    {
        saveButton.setOnClickListener {
            startActivity(Intent(this, MainMenuActivity::class.java))
        }
    }

}