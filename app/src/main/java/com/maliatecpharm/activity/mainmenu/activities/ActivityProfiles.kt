package com.maliatecpharm.activity.mainmenu.activities

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.maliatecpharm.R
import com.maliatecpharm.uimodel.Profiles
import com.maliatecpharm.activity.mainmenu.fragments.FragmentProfile
import java.util.*

class ActivityProfiles : AppCompatActivity(),
    DatePickerDialog.OnDateSetListener
{
    private val GenderList = arrayOf(
        "Male", "Female"
    )
    private lateinit var gender: TextView
    private lateinit var genderSpinner: Spinner
    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var takePictureBtn: Button
    private lateinit var chooseImage: ImageView
    private lateinit var enterPhone: EditText
    private lateinit var enterMail: EditText
    private lateinit var DateOfBirth: TextView
    private lateinit var Height: EditText
    private lateinit var Weight: EditText
    private lateinit var saveMyProfileBtn: Button
    private lateinit var plusButton: FloatingActionButton
    private lateinit var textDate1: TextView
    private lateinit var btnCancel: Button
    val context = this
    private val REQUEST_CODE = 42

    var sDay = 0
    var sMonth = 0
    var sYear = 0
    var sSavedDay = 0
    var sSavedMonth = 0
    var sSavedYear = 0

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profiles)

        gender = findViewById(R.id.textview_gender)
        genderSpinner = findViewById(R.id.spinner_genderSpinner)
        firstName = findViewById(R.id.edittext_firstName)
        lastName = findViewById(R.id.edittext_lastName)
        takePictureBtn = findViewById(R.id.button_btnTakePic)
        chooseImage = findViewById(R.id.imageview_picture)
        enterPhone = findViewById(R.id.edittext_phone)
        enterMail = findViewById(R.id.edittext_email)
        DateOfBirth = findViewById(R.id.textview_dateOfBirth)
        Height = findViewById(R.id.edittext_height)
        Weight = findViewById(R.id.edittext_weight)
        saveMyProfileBtn = findViewById(R.id.button_saveButtonn)
        plusButton = findViewById(R.id.button_addButton)
        textDate1 = findViewById(R.id.textview_dateOfBirthday)
        btnCancel = findViewById(R.id.btnCancel)

        sexSpinner()
        pickSDate()
        takePicture()
        onSaveClickListener()

        btnCancel.setOnClickListener {
            clearEdits()
            finish()
        }
    }

    private fun onSaveClickListener()
    {
        saveMyProfileBtn.setOnClickListener {
            if (firstName.text.isEmpty())
            {
                Toast.makeText(this, "Enter customer Name", Toast.LENGTH_SHORT).show()
                firstName.requestFocus()
            }
            else
            {
                val profiles = Profiles()
                profiles.firstName = firstName.text.toString()
                profiles.lastName = lastName.text.toString()

                if (lastName.text.isEmpty())
                    profiles.lastName = ""
                else profiles.lastName = lastName.text.toString()
                FragmentProfile.dbHandler.addProfile(this, profiles)

                this.finish()
            }
        }
    }

    private fun clearEdits()
    {
        firstName.text.clear()
        lastName.text.clear()
    }

    private fun sexSpinner()
    {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, GenderList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpinner.adapter = adapter
    }

    private fun takePicture()
    {
        takePictureBtn.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePictureIntent, REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK)
        {
            val takenImage = data?.extras?.get("data") as Bitmap
            chooseImage.setImageBitmap(takenImage)
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun getSDateCalendar()
    {
        val cal = Calendar.getInstance()
        sDay = cal.get(Calendar.DAY_OF_MONTH)
        sMonth = cal.get(Calendar.MONTH)
        sYear = cal.get(Calendar.YEAR)
    }

    private fun pickSDate()
    {
        DateOfBirth.setOnClickListener {
            getSDateCalendar()
            DatePickerDialog(this, fromListener, sYear, sMonth, sDay).show()
        }
    }

    private val fromListener = DatePickerDialog.OnDateSetListener { datePicker: DatePicker, year: Int, month: Int, dayOfMonth: Int ->

        sSavedDay = dayOfMonth
        sSavedMonth = month
        sSavedYear = year
        getSDateCalendar()
        textDate1.text = "$sSavedDay - $sSavedMonth - $sSavedYear"
    }

    override fun onDateSet(datePicker: DatePicker?, year: Int, month: Int, dayOfMonth: Int)
    {
        sSavedDay = dayOfMonth
        sSavedMonth = month
        sSavedYear = year
        textDate1.text = "$sSavedDay - $sSavedMonth - $sSavedYear"
    }


}