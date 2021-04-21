package com.maliatecpharm.activity.mainmenu.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.maliatecpharm.R

class ActivityAddDiagnosis:AppCompatActivity()
{
    private lateinit var addDiagnosis: Button
    private lateinit var diagnosiseName: EditText

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_diagnosis)

        addDiagnosis = findViewById(R.id.button_addBtn)
        diagnosiseName =findViewById(R.id.edittext_nameOfMedicine)
    }


}