package com.maliatecpharm.activity.mainmenu.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.maliatecpharm.R
import com.maliatecpharm.activity.mainmenu.data.AppDataBase
import com.maliatecpharm.activity.mainmenu.data.DoctorsDao
import com.maliatecpharm.activity.mainmenu.data.DoctorsEntity
import com.maliatecpharm.activity.mainmenu.data.ProfileEntity
import com.maliatecpharm.activity.mainmenu.uimodel.DoctorsUiModel
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class FragmentAddDoctor : Fragment(),
    TimePickerDialog.OnTimeSetListener
{
    private val doctorDao: DoctorsDao by lazy {
        AppDataBase.getDataBase(requireContext()).doctorDao()
    }

    private lateinit var btnTimePicker: TextView
    private lateinit var appointmentDate: TextView
    private lateinit var doctorsSpecialitySpinner: SearchableSpinner
    private lateinit var mobileNbr: EditText
    private lateinit var officeNbr: EditText
    private lateinit var email: EditText
    private lateinit var location: EditText
    private lateinit var saveBTN: Button
    private lateinit var enterDrName: EditText
    private lateinit var drSpec: EditText
    private lateinit var doctorLiveData: LiveData<List<DoctorsEntity>>
    private var doctorEntity = DoctorsEntity("", "", "", "")
    private val DoctorsSpecialityList = mutableListOf<String>(
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
        val view = inflater.inflate(R.layout.fragment_add_doctor, container, false)

        enterDrName = view.findViewById(R.id.enterDrName)
        drSpec = view.findViewById(R.id.enterDrSpec)
        btnTimePicker = view.findViewById(R.id.button_timePickerBtn1)
        appointmentDate = view.findViewById(R.id.textview_appointmentTime)
        doctorsSpecialitySpinner = view.findViewById(R.id.spinner_specialitySpinner)
        mobileNbr = view.findViewById(R.id.edittext_mobilePhone)
        officeNbr = view.findViewById(R.id.edittext_officePhone)
        email = view.findViewById(R.id.edittext_email)
        location = view.findViewById(R.id.edittext_location)
        saveBTN = view.findViewById(R.id.button_saveButtonn)
        specialitySpinner()
        pickSDate()
        onSaveClickListener()
        getAppointmentDateTimeCalendar()
        linkSpinnerToDoctorsDb()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        val doctorId = arguments?.getInt("doctorId") ?: -1
        doctorDao.getDoctorsLiveData(doctorId).observe(viewLifecycleOwner) {

            if (it != null)
            {
                doctorEntity = it
                enterDrName.setText(it.drName)
                drSpec.setText(it.spec)
                mobileNbr.setText(it.nbr)
                appointmentDate.setText(it.app)
            }
        }
    }

    private fun linkSpinnerToDoctorsDb()
    {
        doctorLiveData = doctorDao.readAllDoctors()
        doctorLiveData.observe(viewLifecycleOwner, { dbDoctors ->
            val drUpdatedList = dbDoctors.map { doctorsEntity ->
                doctorsEntity.spec
            }

            val doctorsAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                drUpdatedList
            ).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
            doctorsSpecialitySpinner.adapter = doctorsAdapter
        })
    }

    private fun specialitySpinner()
    {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, DoctorsSpecialityList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        doctorsSpecialitySpinner.adapter = adapter

        doctorsSpecialitySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                Toast.makeText(requireContext(), "You selected${parent?.getItemAtPosition(position).toString()}",
                    Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?)
            {
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
        if (validateInput())
        {
            val doctorName = enterDrName.text.toString()
            val spec = drSpec.text.toString()
            val nbr = mobileNbr.text.toString()
            val app = appointmentDate.text.toString()

            if (inputCheck(doctorName, spec, nbr, app))
            {
                val doctor = DoctorsEntity(doctorName, spec, nbr, app)
                lifecycleScope.launch(Dispatchers.IO) {
                    doctorDao.addDoctor(doctor)
                    withContext(Dispatchers.Main) {
                        findNavController().popBackStack()
                    }
                }
            }
        }
        else
            Toast.makeText(requireContext(), "Please enter doctor info. ", Toast.LENGTH_SHORT).show()
    }

    private fun inputCheck(
        DrName: String, spec: String, nbr: String, app: String,
    ): Boolean
    {
        return !(TextUtils.isEmpty(DrName) && TextUtils.isEmpty(spec)
                && TextUtils.isEmpty(nbr) && TextUtils.isEmpty(app))
    }

    fun validateInput(): Boolean
    {
        if (enterDrName.text.toString().equals(""))
        {
            enterDrName.setError("Please Enter First Name")
            return false
        }
        if (drSpec.text.toString().equals(""))
        {
            drSpec.setError("Please Enter Last Name")
            return false
        }
        if (mobileNbr.text.toString().equals(""))
        {
            mobileNbr.setError("Please Enter Contact No")
            return false
        }
        return true
    }
}


