package com.maliatecpharm.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.maliatecpharm.R
import com.maliatecpharm.activity.mainmenu.AddMedications
import java.util.*


class Profile : AppCompatActivity(), DatePickerDialog.OnDateSetListener
{

    private val GenderList = arrayOf(
        "Male", "Female"
    )


    private lateinit var gender: TextView
    private lateinit var genderSpinner: Spinner


    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var Phone: EditText

    private lateinit var Mail: EditText
    private lateinit var DateOfBirth: TextView
    private lateinit var Height: EditText
    private lateinit var Weight: EditText
    private lateinit var saveMyProfileBtn: Button

    private lateinit var textDate1: TextView

    var sDay = 0
    var sMonth = 0
    var sYear = 0


    var sSavedDay = 0
    var sSavedMonth = 0
    var sSavedYear = 0


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)



        gender = findViewById(R.id.Gender)
        genderSpinner = findViewById(R.id.sexSpinner)
        firstName = findViewById(R.id.fname_edittext)
        lastName = findViewById(R.id.lname_edittext)
        Phone = findViewById(R.id.Phone)
        Mail = findViewById(R.id.email)
        DateOfBirth = findViewById(R.id.dateOfBirth)
        Height = findViewById(R.id.height)
        Weight = findViewById(R.id.weight)
        saveMyProfileBtn = findViewById(R.id.saveButtonn)
        textDate1 = findViewById(R.id.tv_textTime1)

        firstName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_perm_identity_24, 0, 0, 0)
        lastName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_perm_identity_24, 0, 0, 0)
        Mail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_email_24, 0, 0, 0)
        Phone.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_phone_android_24, 0, 0, 0)
        DateOfBirth.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_date_range_24, 0, 0, 0)
        Height.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_height_24, 0, 0, 0)
        Weight.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_account_balance_wallet_24, 0, 0, 0)

        setOnBtnClicked()
        sexSpinner()
        pickSDate()

    }

    private fun sexSpinner()
    {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, GenderList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpinner.adapter = adapter
    }

    private fun setOnBtnClicked()
    {
        saveMyProfileBtn.setOnClickListener {
            startActivity(Intent(this, AddMedications::class.java))
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

            DatePickerDialog(this, fromListener, sYear, sMonth, sDay)
                .show()
        }
    }

    val fromListener = DatePickerDialog.OnDateSetListener { datePicker: DatePicker, year: Int, month: Int, dayOfMonth: Int ->

        sSavedDay = dayOfMonth
        sSavedMonth = month
        sSavedYear = year

        getSDateCalendar()

        //        TimePickerDialog(this, this, sHour, sMinute, true).show()
        textDate1.text = "$sSavedDay - $sSavedMonth - $sSavedYear"
    }

    override fun onDateSet(datePicker: DatePicker?, year: Int, month: Int, dayOfMonth: Int)
    {
        sSavedDay = dayOfMonth
        sSavedMonth = month
        sSavedYear = year


        textDate1.text = "$sSavedDay - $sSavedMonth - $sSavedYear"
    }


    //    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int)
    //    {
    //        sSavedHour = hourOfDay
    //        sSavedMinute = minute
    //
    //        textDate1.text = "Starting Date: \n$sSavedDay - $sSavedMonth - $sSavedYear \nHour: $sSavedHour Minute: $sSavedMinute"
    //    }
}


