package com.maliatecpharm.activity

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.maliatecpharm.R
import com.google.android.material.button.MaterialButton


class Medications:AppCompatActivity() {

    private lateinit var medicationsText: TextView
    private lateinit var medicationButton : MaterialButton
    private lateinit var addMedicationText: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.medications_layout)


        medicationsText = findViewById(R.id.medicationsText_textView)
        medicationButton= findViewById(R.id.add_medication_btn)
        addMedicationText = findViewById(R.id.add_medication_text_view)

        setOnMedicationButtonClicked()


    }

    private fun setOnMedicationButtonClicked()
    {
        medicationButton .setOnClickListener {
            startActivity(Intent(this, MaliaActivity::class.java))
        }

    }
}