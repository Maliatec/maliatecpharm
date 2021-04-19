package com.maliatecpharm.activity.mainmenu.fragments

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.basgeekball.awesomevalidation.AwesomeValidation
import com.basgeekball.awesomevalidation.ValidationStyle
import com.basgeekball.awesomevalidation.utility.RegexTemplate
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.maliatecpharm.R
import com.maliatecpharm.activity.mainmenu.data.AppDataBase
import com.maliatecpharm.activity.mainmenu.data.UserDao
import com.maliatecpharm.activity.mainmenu.data.ProfileEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class FragmentAddProfile : Fragment(),
    DatePickerDialog.OnDateSetListener
{
    private val userDao: UserDao by lazy {
        AppDataBase.getDataBase(requireContext()).userDao()
    }

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
    private lateinit var textDate1: TextView
    val context = this
    private val REQUEST_CODE = 42

    var sDay = 0
    var sMonth = 0
    var sYear = 0
    var sSavedDay = 0
    var sSavedMonth = 0
    var sSavedYear = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        val view = inflater.inflate(R.layout.fragment_add_profile, container, false)


        gender = view.findViewById(R.id.textview_gender)
        genderSpinner = view.findViewById(R.id.spinner_genderSpinner)
        firstName = view.findViewById(R.id.edittext_firstName)
        lastName = view.findViewById(R.id.edittext_lastName)
//        takePictureBtn = view.findViewById(R.id.layout_image)
//        chooseImage = view.findViewById(R.id.imageview_picture)
        enterPhone = view.findViewById(R.id.edittext_phone)
        enterMail = view.findViewById(R.id.edittext_email)
        DateOfBirth = view.findViewById(R.id.textview_dateOfBirth)
        Height = view.findViewById(R.id.edittext_height)
        Weight = view.findViewById(R.id.edittext_weight)
        saveMyProfileBtn = view.findViewById(R.id.button_saveButtonn)
        textDate1 = view.findViewById(R.id.textview_textTime1)


        sexSpinner()
        //takePicture()
        pickSDate()
        onSaveClickListener()
        insertDataToDataBase()

        return view
    }

    private fun onSaveClickListener()
    {
        saveMyProfileBtn.setOnClickListener {
                insertDataToDataBase()
        }
    }

    private fun insertDataToDataBase()
    {
        if (validateInput())
        {

            val firstName = firstName.text.toString()
            val lastName = lastName.text.toString()

            if (inputCheck(firstName, lastName))
            {
                Toast.makeText(requireContext(), "Form validate", Toast.LENGTH_SHORT).show()
                val profile = ProfileEntity(firstName, lastName)

                lifecycleScope.launch(Dispatchers.IO) {
                    userDao.addUser(profile)
                }
                findNavController().popBackStack()
            }
        }
    }

    private fun inputCheck(firstName: String, lastName: String): Boolean
    {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName))
    }

    fun validateInput(): Boolean {
        if (firstName.text.toString().equals("")) {
            firstName.setError("Please Enter First Name")
            return false
        }
        if (lastName.text.toString().equals("")) {
            lastName.setError("Please Enter Last Name")
            return false
        }
        if (enterMail.text.toString().equals("")) {
            enterMail.setError("Please Enter Email")
            return false
        }
        if (enterPhone.text.toString().equals("")) {
            enterPhone.setError("Please Enter Contact No")
            return false
        }
        if (!isEmailValid(enterMail.text.toString())) {
            enterMail.setError("Please Enter Valid Email")
            return false
        }
        return true
    }

    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun sexSpinner()
    {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, GenderList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpinner.adapter = adapter
    }

//    private fun takePicture()
//    {
//        takePictureBtn.setOnClickListener {
//            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            startActivityForResult(takePictureIntent, REQUEST_CODE)
//        }
//    }

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
            DatePickerDialog(requireContext(), fromListener, sYear, sMonth, sDay).show()
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
