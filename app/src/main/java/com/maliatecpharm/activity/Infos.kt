package com.maliatecpharm.activity

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.maliatecpharm.R
import com.maliatecpharm.activity.mainmenu.FirstFragment

class Infos :AppCompatActivity()
{

    private val sexList = arrayOf(
        "Male", "Female"
    )

    private lateinit var nameTv: TextView
    private lateinit var nameEt: EditText
    private lateinit var sexTv: TextView
    private lateinit var sexSpinner: Spinner
    private lateinit var ageTv: TextView
    private lateinit var ageEt: EditText
    private lateinit var saveBtn: Button

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.infos)

        nameTv = findViewById(R.id.Name_textview)
        nameEt = findViewById(R.id.nameEditText)
        sexTv = findViewById(R.id.Sex_textview)
        sexSpinner = findViewById(R.id.sexSpinner)
        ageTv = findViewById(R.id.Agetextview)
        ageEt = findViewById(R.id.ageEditText)
        saveBtn = findViewById(R.id.saveButton)

        SexSpinner()
        setOnBtnClicked()
    }

    private fun SexSpinner()
    {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sexList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sexSpinner.adapter = adapter
    }

    private fun setOnBtnClicked()
    {
        saveBtn.setOnClickListener {
            startActivity(Intent(this, FirstFragment::class.java))
        }
    }


}