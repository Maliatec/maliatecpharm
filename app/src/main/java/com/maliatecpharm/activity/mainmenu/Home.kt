package com.maliatecpharm.activity.mainmenu

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.maliatecpharm.R
import com.maliatecpharm.activity.LogInActivity
import com.maliatecpharm.activity.Medications

class Home : AppCompatActivity()
{

    private lateinit var emptyBtn: Button

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_layout)


        emptyBtn = findViewById(R.id.emptyBtn)

        setOnBtnClicked()

    }

    private fun setOnBtnClicked()
    {
        emptyBtn.setOnClickListener {
            startActivity(Intent(this, Medications::class.java))
        }
    }
}