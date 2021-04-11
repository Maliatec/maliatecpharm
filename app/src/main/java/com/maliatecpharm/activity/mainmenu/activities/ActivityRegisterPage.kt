package com.maliatecpharm.activity.mainmenu.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.maliatecpharm.R
import com.maliatecpharm.activity.mainmenu.data.AppDataBase
import com.maliatecpharm.activity.mainmenu.data.UserDao
import com.maliatecpharm.activity.mainmenu.data.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ActivityRegisterPage:AppCompatActivity()
{

    private val userDao: UserDao by lazy {
        AppDataBase.getDataBase(this).userDao()
    }

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

    private fun saveUserToDb(email: String, password: String){
        val userEntity = UserEntity(email = email, password = password)
        lifecycleScope.launch(Dispatchers.IO){

            val userExists = userDao.getUserByEmail(email).firstOrNull() != null
            if (userExists){
                withContext(Dispatchers.Main){
                    Toast.makeText(this@ActivityRegisterPage, R.string.user_already_exists, Toast.LENGTH_SHORT).show()
                }
            }

            else {
                userDao.registerUser(userEntity)
                finish()
            }
        }
    }

    private fun setOnButtonClicked()
    {
        saveButton.setOnClickListener {
            val email = enterMail.text.toString()
            val pass = password.text.toString()
            saveUserToDb(email = email, password = pass)
        }
    }
}