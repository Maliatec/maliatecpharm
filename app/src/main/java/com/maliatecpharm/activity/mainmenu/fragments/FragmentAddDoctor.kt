package com.maliatecpharm.activity.mainmenu.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.maliatecpharm.R
import com.maliatecpharm.activity.mainmenu.data.AppDataBase
import com.maliatecpharm.activity.mainmenu.data.DoctorsDao
import com.maliatecpharm.activity.mainmenu.data.DoctorsEntity
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class FragmentAddDoctor : Fragment(),
    TimePickerDialog.OnTimeSetListener
    {
        private val doctorDao: DoctorsDao by lazy {
            AppDataBase.getDataBase(requireContext()).doctorDao()
        }

        private lateinit var doctorsNameSpinner: SearchableSpinner
        private lateinit var btnTimePicker: Button
        private lateinit var appointmentDate: TextView
        private lateinit var doctorsSpecialitySpinner: SearchableSpinner
        private lateinit var mobileNbr: EditText
        private lateinit var officeNbr: EditText
        private lateinit var email: EditText
        private lateinit var location: EditText
        private lateinit var saveBTN: Button
        private lateinit var enterDrName: EditText
        private lateinit var drSpec: EditText

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
        savedInstanceState: Bundle?
    ): View?
    {
       val view =  inflater.inflate(R.layout.fragment_add_doctor, container, false)

        enterDrName = view.findViewById(R.id.enterDrName)
        doctorsNameSpinner =  view.findViewById(R.id.spinner_doctorsNameSpinner)
        drSpec = view.findViewById(R.id.enterDrSpec)
        btnTimePicker =  view.findViewById(R.id.button_timePickerBtn1)
        appointmentDate =  view.findViewById(R.id.textview_appointmentTime)
        doctorsSpecialitySpinner =  view.findViewById(R.id.spinner_specialitySpinner)
        mobileNbr =  view.findViewById(R.id.edittext_mobilePhone)
        officeNbr =  view.findViewById(R.id.edittext_officePhone)
        email =  view.findViewById(R.id.edittext_email)
        location = view.findViewById(R.id.edittext_location)
        saveBTN =  view.findViewById(R.id.button_saveButtonn)

        doctorsNameSpinner()
        specialitySpinner()
        pickSDate()
        onSaveClickListener()
        getAppointmentDateTimeCalendar()
        linkSpecsSpinnerToEditText()
        linkNamesSpinnerToEditText()
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

        private fun onSaveClickListener()
        {
            saveBTN.setOnClickListener {
                insertDrDataToDataBase()
            }
        }

        private fun insertDrDataToDataBase()
        {
            val doctorName = enterDrName.text.toString()
            val spec =drSpec.text.toString()
            val nbr = mobileNbr.text.toString()

            if (inputCheck(doctorName, spec,nbr))
            {
                val doctor = DoctorsEntity(doctorName, spec,nbr)
                lifecycleScope.launch(Dispatchers.IO) {
                    doctorDao.addDoctor(doctor)
                }
                Toast.makeText(requireContext(), "Doctor name added. ", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
            else
                Toast.makeText(requireContext(), "Please enter a doctor name. ", Toast.LENGTH_SHORT).show()
        }

        private fun inputCheck(DrName: String, spec:String, nbr:String): Boolean
        {
            return !(TextUtils.isEmpty(DrName) && TextUtils.isEmpty(spec) && TextUtils.isEmpty(nbr))
        }

        private fun linkNamesSpinnerToEditText()
        {
            doctorsNameSpinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener
            {

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
                {
                    enterDrName.setText(doctorsNameSpinner.selectedItem.toString())
                }

                override fun onNothingSelected(parent: AdapterView<*>?)
                {
                }
            }
        }

        private fun linkSpecsSpinnerToEditText()
        {
            doctorsSpecialitySpinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener
            {

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
                {
                    drSpec.setText(doctorsSpecialitySpinner.selectedItem.toString())
                }

                override fun onNothingSelected(parent: AdapterView<*>?)
                {
                }
            }
        }

    }
