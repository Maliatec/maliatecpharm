package com.maliatecpharm.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.maliatecpharm.R

class  Medications : AppCompatActivity()
{
    private lateinit var text1: TextView
    private lateinit var btn1: Button
    private lateinit var text2: TextView



    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.medications_layout)


        text1 = findViewById(R.id.text1)
        btn1 = findViewById(R.id.btn1)
        text2 = findViewById(R.id.text2)

        setOnBtnClicked()

    }

    private fun setOnBtnClicked()
    {
        btn1.setOnClickListener {
            startActivity(Intent(this, MaliaActivity::class.java))
        }
    }
}