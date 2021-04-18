package com.maliatecpharm.activity.mainmenu.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.basgeekball.awesomevalidation.AwesomeValidation
import com.basgeekball.awesomevalidation.ValidationStyle
import com.basgeekball.awesomevalidation.utility.RegexTemplate
import com.maliatecpharm.R
import com.maliatecpharm.activity.mainmenu.data.AppDataBase
import com.maliatecpharm.activity.mainmenu.data.UserDao
import com.maliatecpharm.activity.mainmenu.data.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ActivityRegisterPage : AppCompatActivity()
{

    private val userDao: UserDao by lazy {
        AppDataBase.getDataBase(this).userDao()
    }

    private lateinit var enterMail: EditText
    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var password: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var saveButton: Button

    private lateinit var awesomeValdiation: AwesomeValidation

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


        awesomeValdiation = AwesomeValidation(ValidationStyle.BASIC)
        awesomeValdiation.addValidation(
            this, R.id.edittext_enterEmail,
            RegexTemplate.NOT_EMPTY, R.string.invalid_username
        )

        awesomeValdiation.addValidation(
            this, R.id.edittext_Password,
            RegexTemplate.NOT_EMPTY, R.string.invalid_password
        )

        awesomeValdiation.addValidation(
            this, R.id.edittext_confirmPassword,
            RegexTemplate.NOT_EMPTY, R.string.invalid_password
        )
        setOnButtonClicked()
    }

    private fun saveUserToDb(email: String, password: String)
    {
        val userEntity = UserEntity(email = email, password = password)
        lifecycleScope.launch(Dispatchers.IO) {

            val userExists = userDao.getUserByEmail(email).firstOrNull() != null
            if (userExists)
            {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ActivityRegisterPage, R.string.user_already_exists, Toast.LENGTH_SHORT).show()
                }
            }
            else
            {
                userDao.registerUser(userEntity)
                finish()
            }
        }
    }

    private fun setOnButtonClicked()
    {
        saveButton.setOnClickListener {
            if (awesomeValdiation.validate())
            {
                Toast.makeText(this, "Form validate", Toast.LENGTH_SHORT).show()


                val email = enterMail.text.toString()
                val pass = password.text.toString()
                saveUserToDb(email = email, password = pass)
                startActivity(Intent(this@ActivityRegisterPage, ActivityMainMenu::class.java))

            }
            else
                Toast.makeText(this, "Validation failed", Toast.LENGTH_SHORT).show()
        }
    }
}

