package com.maliatecpharm.activity.mainmenu.activities

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.maliatecpharm.R

class ActivityResetPassword: AppCompatActivity() {

    lateinit var et_code: EditText
    lateinit var etPassword:EditText
    lateinit var etRepeatPassword:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        viewInitializations()
    }

    fun viewInitializations() {
        et_code = findViewById(R.id.et_code)
        etPassword = findViewById(R.id.et_password)
        etRepeatPassword = findViewById(R.id.et_repeat_password)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}