package com.maliatecpharm.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.maliatecpharm.R


class VitalSigns:AppCompatActivity()
{

    private lateinit var enterCholesterol: EditText
    private lateinit var enterFitness: EditText
    private lateinit var enterGlucose: EditText
    private lateinit var enterBloodPressure: EditText
    private lateinit var enterPulse: EditText
    private lateinit var enterQualityOfLife: EditText
    private lateinit var saveButton: Button



    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vital_signs)


        enterCholesterol = findViewById(R.id.enterCholesterol_edittext)
        enterFitness = findViewById(R.id.enterFitness_edittext)
        enterGlucose = findViewById(R.id.enterGlucose_edittext)
        enterBloodPressure = findViewById(R.id.enterBloodPressure_edittext)
        enterPulse = findViewById(R.id.enterPulse_edittext)
        enterQualityOfLife = findViewById(R.id.QualityOfLife_edittext)
        saveButton = findViewById(R.id.saveBut)


//        enterCholesterol.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cholesterol, 0, 0, 0)
//        enterFitness.setCompoundDrawablesWithIntrinsicBounds(R.drawable.fitness, 0, 0, 0)
//        enterGlucose.setCompoundDrawablesWithIntrinsicBounds(R.drawable.glucose, 0, 0, 0)
//        enterBloodPressure.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bloodpressure, 0, 0, 0)
//        enterPulse.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pulse, 0, 0, 0)
//        enterQualityOfLife.setCompoundDrawablesWithIntrinsicBounds(R.drawable.qualoflife, 0, 0, 0)
//
//













    }
}