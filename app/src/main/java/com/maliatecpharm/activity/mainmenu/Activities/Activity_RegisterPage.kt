package com.maliatecpharm.activity.mainmenu.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.maliatecpharm.R

class Activity_RegisterPage:AppCompatActivity()
{

    private lateinit var email:TextView
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

        email =findViewById(R.id.textview_emailAdress)
        enterMail = findViewById(R.id.edittext_enterEmail)
        firstName = findViewById(R.id.edittext_firstNAME)
        lastName = findViewById(R.id.edittext_lastNAME)
        password = findViewById(R.id.edittext_Password)
        confirmPassword = findViewById(R.id.edittext_confirmPassword)
        saveButton = findViewById(R.id.button_registerBUTTON)

        enterMail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_contact_mail_24, 0, 0, 0)
        firstName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_perm_identity_24, 0, 0, 0)
        lastName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_perm_identity_24, 0, 0, 0)
        password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_lock_24, 0, 0, 0)
        confirmPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_lock_24, 0, 0, 0)


        setOnButtonClicked()


    }

    private fun setOnButtonClicked()
    {
        saveButton.setOnClickListener {
            startActivity(Intent(this, MainMenuActivity::class.java))
        }
    }

}