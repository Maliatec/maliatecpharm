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
import com.maliatecpharm.R
import org.w3c.dom.Text
import java.io.File
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
    private lateinit var Phone: EditText
    private lateinit var validationNumber: Button
    private lateinit var Mail: EditText
    private lateinit var DateOfBirth: TextView
    private lateinit var Height: EditText
    private lateinit var Weight: EditText
    private lateinit var saveMyProfileBtn: Button
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
        Phone = view.findViewById(R.id.edittext_phone)
        validationNumber = view.findViewById(R.id.button_validationNumberBtn)
        Mail = view.findViewById(R.id.edittext_email)
        DateOfBirth = view.findViewById(R.id.textview_dateOfBirth)
        Height = view.findViewById(R.id.edittext_height)
        Weight = view.findViewById(R.id.edittext_weight)
        saveMyProfileBtn = view.findViewById(R.id.button_saveButtonn)
        textDate1 = view.findViewById(R.id.textview_dateOfBirthday)

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
        takePicture()
        phoneValidation()

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
        Phone.addTextChangedListener(object : TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
            {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {
                if (mobileValidate(Phone.text.toString()))
                {
                    validationNumber.isEnabled = true
                }
                else
                    Phone.error = "Invalid mobile number"
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
}
