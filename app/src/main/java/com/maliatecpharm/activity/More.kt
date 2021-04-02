package com.maliatecpharm.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.maliatecpharm.R
import com.maliatecpharm.activity.mainmenu.Home

class More:AppCompatActivity()
{
    private lateinit var moretv: TextView
    private lateinit var moreemptyBtn: Button

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.more_layout)

        moretv = findViewById(R.id.moreTextView)
        moreemptyBtn = findViewById(R.id.moreEmptyBtn)

        setOnBtnClicked()

    }

    private fun setOnBtnClicked()
    {
        moreemptyBtn.setOnClickListener {
            startActivity(Intent(this, Home::class.java))
        }
    }
}