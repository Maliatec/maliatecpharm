package com.maliatecpharm.activity.mainmenu.Fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.maliatecpharm.R
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


class FragmentAppointment : Fragment(),
    TimePickerDialog.OnTimeSetListener
{

    private lateinit var doctorsNameSpinner: Spinner
    private lateinit var resultDoctorName: TextView
    private lateinit var btnTimePicker: Button
    private lateinit var appointmentDate: TextView
    private lateinit var doctorsSpecialitySpinner: Spinner
    private lateinit var mobileNbr: EditText
    private lateinit var validationNumber:TextView
    private lateinit var officeNbr: EditText
    private lateinit var officeNbrValidation: TextView
    private lateinit var email: EditText
    private lateinit var location: EditText
    private lateinit var saveBTN: Button


    private val DoctorsNameList = arrayOf(
         "Dr X", "Dr Y", "Dr Z", "Other"
    )

    private val DoctorsSpecialityList = arrayOf(
        "Allergy and Immunology",
        "Anesthesiology", "Dermatology", "Diagnostic radiology",
        "Emergency medicine", "Family medicine",
        "Internal medicine", "Medical genetics"
    )

    var Day = 0
    var Month = 0
    var Year = 0
    var Hour = 0
    var Minute = 0

    var SavedDay = 0
    var SavedMonth = 0
    var SavedYear = 0
    var SavedHour = 0
    var SavedMinute = 0

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View?
    {
        val view = inflater.inflate(R.layout.fragment_appointment, container, false)

        doctorsNameSpinner = view.findViewById(R.id.spinner_doctorsNameSpinner)
        btnTimePicker = view.findViewById(R.id.button_timePickerBtn1)
        appointmentDate = view.findViewById(R.id.textview_appointmentTime)
        resultDoctorName =view. findViewById(R.id.textview_resultName)
        doctorsSpecialitySpinner = view.findViewById(R.id.spinner_specialitySpinner)
        mobileNbr = view.findViewById(R.id.edittext_mobilePhone)
        validationNumber = view.findViewById(R.id.textview_validationNumberTextView)
        officeNbr = view.findViewById(R.id.edittext_officePhone)
        officeNbrValidation = view.findViewById(R.id.textview_validationOfficeNumberTextView)
        email = view.findViewById(R.id.edittext_email)
        location = view.findViewById(R.id.edittext_location)
        saveBTN = view.findViewById(R.id.button_saveButtonn)


        doctorsNameSpinner()
        specialitySpinner()
        //setOnBtnClicked()
        pickSDate()
        getAppointmentDateTimeCalendar()
        //resultNameSpinner()
      //  resultSpecialitySpinner()
        mobileValidation()
        phoneValidation()


//        view.setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_doctorsFragment_to_MoreFragment) }
        return view

    }

    private fun doctorsNameSpinner()
    {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, DoctorsNameList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        doctorsNameSpinner.adapter = adapter
    }

//    private fun resultNameSpinner()
//    {
//        doctorsNameSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
//        {
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
//            {
//                resultDoctorName.text = DoctorsNameList.get(position)
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?)
//            {
//                resultDoctorName.text = "Please select a name"
//            }
//        }
//    }

//    private fun setOnBtnClicked()
//    {
//        saveBTN.setOnClickListener {
//            startActivity(Intent(requireContext(), FragmentMore::class.java))
//        }
//    }

    private fun specialitySpinner()
    {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, DoctorsSpecialityList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        doctorsSpecialitySpinner.adapter = adapter
    }
//    private fun resultSpecialitySpinner()
//    {
//        doctorsSpecialitySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
//        {
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
//            {
//                resultSpecialityName.text =DoctorsSpecialityList.get(position)
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?)
//            {
//                resultSpecialityName.text = "Please select a speciality"
//            }
//        }

//    }

    private fun getAppointmentDateTimeCalendar()
    {
        val cal = Calendar.getInstance()
        Day = cal.get(Calendar.DAY_OF_MONTH)
        Month = cal.get(Calendar.MONTH)
        Year = cal.get(Calendar.YEAR)
        Hour = cal.get(Calendar.HOUR)
        Minute = cal.get(Calendar.MINUTE)
    }

    private fun pickSDate()
    {
        btnTimePicker.setOnClickListener {
            getAppointmentDateTimeCalendar()

            DatePickerDialog(requireContext(), fromListener, Year, Month, Day)
                .show()

        }
    }


    val fromListener = DatePickerDialog.OnDateSetListener { datePicker: DatePicker, year: Int, month: Int, dayOfMonth: Int ->

        SavedDay = dayOfMonth
        SavedMonth = month
        SavedYear = year

        getAppointmentDateTimeCalendar()

        TimePickerDialog(requireContext(), this, Hour, Minute, true).show()


    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int)
    {

        SavedHour = hourOfDay
        SavedMinute = minute

        appointmentDate.text = "Appointment's Date: \n$SavedDay - $SavedMonth - $SavedYear \nHour: $SavedHour Minute: $SavedMinute"

    }

    private fun mobileValidation()
    {
        mobileNbr.addTextChangedListener(object : TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
            {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {
                if (mobileValidate(mobileNbr.text.toString()))
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


    private fun phoneValidation()
    {
        officeNbr.addTextChangedListener(object : TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
            {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {
                if (phoneValidate(officeNbr.text.toString()))
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

    private fun phoneValidate(text: String?): Boolean
    {
        var p: Pattern = Pattern.compile("[0][0-9]{7}")
        var m: Matcher = p.matcher(text)
        return m.matches()

    }
}
