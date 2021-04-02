package com.maliatecpharm.activity.mainmenu

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.maliatecpharm.R
import com.maliatecpharm.activity.Profile

class RegisterPage:AppCompatActivity()
{

    private lateinit var email:TextView
    private lateinit var enterMail:EditText
    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var pass: EditText
    private lateinit var confirmPass: EditText
    private lateinit var saveButton :Button


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_page)

        email =findViewById(R.id.emailadress_textview)
        enterMail = findViewById(R.id.entermail_edittext)
        firstName = findViewById(R.id.firstname_edittext)
        lastName = findViewById(R.id.lastname_edittext)
        pass = findViewById(R.id.password_edittext)
        confirmPass = findViewById(R.id.confirmpassword_edittext)
        saveButton = findViewById(R.id.registerbutton)

        enterMail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_contact_mail_24, 0, 0, 0)
        firstName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_perm_identity_24, 0, 0, 0)
        lastName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_perm_identity_24, 0, 0, 0)
        pass.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_lock_24, 0, 0, 0)
        confirmPass.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_lock_24, 0, 0, 0)


        setOnButtonClicked()


    }

    private fun setOnButtonClicked()
    {
        saveButton.setOnClickListener {
            startActivity(Intent(this, MainMenuActivity::class.java))
        }
    }

}