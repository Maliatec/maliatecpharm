package com.maliatecpharm.activity.mainmenu.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.maliatecpharm.R
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import java.util.*

class FragmentAppointment : Fragment(),
    TimePickerDialog.OnTimeSetListener
{

    private lateinit var doctorsNameSpinner: SearchableSpinner
    private lateinit var btnTimePicker: Button
    private lateinit var appointmentDate: TextView
    private lateinit var doctorsSpecialitySpinner: SearchableSpinner
    private lateinit var mobileNbr: EditText
    private lateinit var validationNumber: TextView
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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View?
    {
        val view = inflater.inflate(R.layout.fragment_appointment, container, false)

        doctorsNameSpinner = view.findViewById(R.id.spinner_doctorsNameSpinner)
        btnTimePicker = view.findViewById(R.id.button_timePickerBtn1)
        appointmentDate = view.findViewById(R.id.textview_appointmentTime)
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
        pickSDate()
        getAppointmentDateTimeCalendar()



        //        view.setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_doctorsFragment_to_MoreFragment) }
        return view

    }

    private fun doctorsNameSpinner()
    {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, DoctorsNameList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        doctorsNameSpinner.adapter = adapter
    }



    private fun specialitySpinner()
    {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, DoctorsSpecialityList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        doctorsSpecialitySpinner.adapter = adapter
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


    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int)
    {

        SavedHour = hourOfDay
        SavedMinute = minute

        appointmentDate.text = "Appointment's Date: \n$SavedDay - $SavedMonth - $SavedYear \nAt: $SavedHour:$SavedMinute"

    }

}