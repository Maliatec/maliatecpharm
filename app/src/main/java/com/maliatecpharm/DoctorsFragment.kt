package com.maliatecpharm

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import java.util.*


class DoctorsFragment : Fragment(),
    TimePickerDialog.OnTimeSetListener
{

    private lateinit var doctorsNameSpinner: Spinner
    private lateinit var resultDoctorName: TextView
    private lateinit var resultSpecialityName: TextView
    private lateinit var btnTimePicker: Button
    private lateinit var appointmentDate: TextView
    private lateinit var doctorsSpecialitySpinner: Spinner
    private lateinit var mobileNbr: EditText
    private lateinit var officeNbr: EditText
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View?
    {
        val view = inflater.inflate(R.layout.fragment_doctors, container, false)

        doctorsNameSpinner = view.findViewById(R.id.doctorsNameSpinner)
        btnTimePicker = view.findViewById(R.id.btn1_timePicker)
        appointmentDate = view.findViewById(R.id.tv_AppointmentTime)
        resultDoctorName =view. findViewById(R.id.resultName_textview)

        doctorsSpecialitySpinner = view.findViewById(R.id.specialitySpinner)
        resultSpecialityName =view. findViewById(R.id.resultSpeciality_textview)
        mobileNbr = view.findViewById(R.id.mobilePhone)
        officeNbr = view.findViewById(R.id.officePhone)
        email = view.findViewById(R.id.email)
        location = view.findViewById(R.id.location)
        saveBTN = view.findViewById(R.id.saveButtonn)

        officeNbr.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_phone_enabled_24, 0, 0, 0)
        mobileNbr.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_contact_phone_24, 0, 0, 0)
        email.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_contact_mail_24, 0, 0, 0)
        location.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_add_location_24, 0, 0, 0)

        doctorsNameSpinner()
        specialitySpinner()
        setOnBtnClicked()
        pickSDate()
        getAppointmentDateTimeCalendar()
        resultNameSpinner()
        resultSpecialitySpinner()


//        view.setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_doctorsFragment_to_MoreFragment) }
        return view

    }

    private fun doctorsNameSpinner()
    {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, DoctorsNameList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        doctorsNameSpinner.adapter = adapter
    }

    private fun resultNameSpinner()
    {
        doctorsNameSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                resultDoctorName.text = DoctorsNameList.get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?)
            {
                resultDoctorName.text = "Please select a name"
            }
        }
    }

    private fun setOnBtnClicked()
    {
        saveBTN.setOnClickListener {
            startActivity(Intent(requireContext(), MoreFragment::class.java))
        }
    }

    private fun specialitySpinner()
    {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, DoctorsSpecialityList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        doctorsSpecialitySpinner.adapter = adapter
    }
    private fun resultSpecialitySpinner()
    {
        doctorsSpecialitySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                resultSpecialityName.text =DoctorsSpecialityList.get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?)
            {
                resultSpecialityName.text = "Please select a speciality"
            }
        }

    }

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

        //appointmentDate.text = "$SavedDay - $SavedMonth - $SavedYear \nHour: $SavedHour Minute: $SavedMinute"

    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int)
    {

        SavedHour = hourOfDay
        SavedMinute = minute

        appointmentDate.text = "Appointment's Date: \n$SavedDay - $SavedMonth - $SavedYear \nHour: $SavedHour Minute: $SavedMinute"

    }

}
