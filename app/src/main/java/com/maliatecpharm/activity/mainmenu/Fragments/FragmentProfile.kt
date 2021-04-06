package com.maliatecpharm.activity.mainmenu.Fragments

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.maliatecpharm.R
import com.maliatecpharm.activity.mainmenu.Activities.ActivityMedications
import com.maliatecpharm.activity.mainmenu.Activities.ActivityProfile
import org.w3c.dom.Text
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class FragmentProfile : Fragment(), DatePickerDialog.OnDateSetListener
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
    private lateinit var validationNumber: TextView
    private lateinit var enterMail: EditText
    private lateinit var saveMail: TextView
    private lateinit var DateOfBirth: TextView
    private lateinit var Height: EditText
    private lateinit var Weight: EditText
    private lateinit var saveMyProfileBtn: Button
    private lateinit var addButton: FloatingActionButton
    private lateinit var textDate1: TextView
    private val REQUEST_CODE = 42

    var sDay = 0
    var sMonth = 0
    var sYear = 0

    var sSavedDay = 0
    var sSavedMonth = 0
    var sSavedYear = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        gender = view.findViewById(R.id.textview_gender)
        genderSpinner = view.findViewById(R.id.spinner_genderSpinner)
        firstName = view.findViewById(R.id.edittext_firstName)
        lastName = view.findViewById(R.id.edittext_lastName)
        takePictureBtn = view.findViewById(R.id.button_btnTakePic)
        chooseImage = view.findViewById(R.id.imageview_picture)
        enterPhone = view.findViewById(R.id.edittext_phone)
        validationNumber = view.findViewById(R.id.textview_validationNumberTextView)
        enterMail = view.findViewById(R.id.edittext_email)
        saveMail = view.findViewById(R.id.textview_valdiationEmailTextView)
        DateOfBirth = view.findViewById(R.id.textview_dateOfBirth)
        Height = view.findViewById(R.id.edittext_height)
        Weight = view.findViewById(R.id.edittext_weight)
        saveMyProfileBtn = view.findViewById(R.id.button_saveButtonn)
        addButton = view.findViewById(R.id.button_addButton)
        textDate1 = view.findViewById(R.id.textview_dateOfBirthday)


        setOnBtnClicked()
        sexSpinner()
        pickSDate()
        takePicture()
        phoneValidation()
        emailValidation()
        setOnButtonClicked()

        return view
    }

    private fun sexSpinner()
    {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, GenderList)
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

    private fun setOnBtnClicked()
    {
        saveMyProfileBtn.setOnClickListener {
            //   startActivity(Intent(requireContext(), AddMedications::class.java))
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
            DatePickerDialog(requireContext(), fromListener, sYear, sMonth, sDay).show()
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

    private fun phoneValidation()
    {
        enterPhone.addTextChangedListener(object : TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
            {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {
                if (mobileValidate(enterPhone.text.toString()))
                {
                    validationNumber.isEnabled = true
                }
                else
                    validationNumber.isEnabled = false
            }

            override fun afterTextChanged(s: Editable?)
            {
            }
        })
    }

    private fun mobileValidate(text: String?): Boolean
    {
        var p: Pattern = Pattern.compile("[0-8][0-9]{7}")
        var m: Matcher = p.matcher(text)
        return m.matches()

    }

    private fun emailValidation(){
        enterMail.addTextChangedListener(object :TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
            {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {
                if(android.util.Patterns.EMAIL_ADDRESS.matcher(enterMail.text.toString()).matches())
                    saveMail.isEnabled = true
                else saveMail.isEnabled = false
                //                username.setError("Invalid Email")
            }
            override fun afterTextChanged(s: Editable?)
            {
            }
        })
    }
    private fun setOnButtonClicked()
    {
        addButton.setOnClickListener {
            startActivity(Intent(requireContext(), ActivityProfile::class.java))
        }
    }

}
