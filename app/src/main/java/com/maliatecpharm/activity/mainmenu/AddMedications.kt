package com.maliatecpharm.activity.mainmenu

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.maliatecpharm.R
import com.maliatecpharm.activity.Medications

class AddMedications: AppCompatActivity()
{
    private lateinit var addButton: Button

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_medication)

        addButton  = findViewById(R.id.addButton)
        setOnButtonClicked()

    }
    private fun setOnButtonClicked()
    {
        addButton.setOnClickListener {
            startActivity(Intent(this, Medications::class.java))
        }
    }


}