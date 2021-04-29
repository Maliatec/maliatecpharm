package com.maliatecpharm.activity.mainmenu.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
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
import java.util.regex.Pattern

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
    val MIN_PASSWORD_LENGTH = 6

    val EMAIL_ADDRESS_PATTERN = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

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

        val emails = arrayOf<String>("hello@gmail.com", "one.com", "")
        emails.forEach {
            Log.d("ActivityRegisterPage", "is valid email $it => ${isValidString(it)}")
        }
        setOnButtonClicked()

    }
    fun isValidString(str: String): Boolean{
        return EMAIL_ADDRESS_PATTERN.matcher(str).matches()
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

                            if (validateInput())
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

    fun validateInput(): Boolean {
        if (enterMail.text.toString().equals("")) {
            enterMail.setError("Please Enter Email")
            return false
        }
        if (!isEmailValid(enterMail.text.toString())) {
            enterMail.setError("Please Enter Valid Email")
            return false
        }
        if (firstName.text.toString().equals("")) {
            firstName.setError("Please Enter First Name")
            return false
        }
        if (lastName.text.toString().equals("")) {
            lastName.setError("Please Enter Last Name")
            return false
        }
        if (password.text.toString().equals(""))
        {
            password.setError("Please Enter Password")
            return false
        }
        if (password.text.length < MIN_PASSWORD_LENGTH) {
            password.setError("Password Length must be more than " + MIN_PASSWORD_LENGTH + "characters")
            return false
        }
        if (confirmPassword.text.toString().equals("")) {
            confirmPassword.setError("Please Enter Repeat Password")
            return false
        }
        if (!password.text.toString().equals(confirmPassword.text.toString())) {
            confirmPassword.setError("Password does not match")
            return false
        }
        return true
    }

    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}

