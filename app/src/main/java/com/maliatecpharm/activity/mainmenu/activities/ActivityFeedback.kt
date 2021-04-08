package com.maliatecpharm.activity.mainmenu.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.maliatecpharm.R


class ActivityFeedback : AppCompatActivity()
{
    private lateinit var enterYourFeedback: EditText
    private lateinit var sendButton: Button

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        enterYourFeedback = findViewById(R.id.edittext_yourfeedback)
        sendButton = findViewById(R.id.button_Send)


    }


}