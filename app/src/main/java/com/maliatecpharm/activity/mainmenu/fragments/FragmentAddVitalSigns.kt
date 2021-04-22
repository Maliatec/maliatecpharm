package com.maliatecpharm.activity.mainmenu.fragments

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
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
import com.maliatecpharm.activity.mainmenu.data.VitalEntity
import com.maliatecpharm.activity.mainmenu.data.VitalSignsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class FragmentAddVitalSigns : Fragment(),
    DatePickerDialog.OnDateSetListener
{
    private val vitalDao: VitalSignsDao by lazy {
        AppDataBase.getDataBase(requireContext()).vitalDao()
    }

    private var vitalEntity = VitalEntity(
        "", "",
        "", "", "", "",
        "",
    )
    private lateinit var entertime: TextView
    private lateinit var enterCholesterol: EditText
    private lateinit var enterFitness: EditText
    private lateinit var enterGlucose: EditText
    private lateinit var enterBloodPressure: EditText
    private lateinit var enterPulse: EditText
    private lateinit var enterQualityOfLife: EditText
    private lateinit var takePictureBtn: TextView
    private lateinit var chooseImage: ImageView
    private val REQUEST_CODE = 42
    private lateinit var saveButton: Button
    private lateinit var uploadDate: TextView
    var Day = 0
    var Month = 0
    var Year = 0
    var SavedDay = 0
    var SavedMonth = 0
    var SavedYear = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View?
    {
        val view = inflater.inflate(R.layout.fragment_vital_signs, container, false)
        enterCholesterol = view.findViewById(R.id.edittext_enterCholesterol)
        enterFitness = view.findViewById(R.id.edittext_enterFitness)
        enterGlucose = view.findViewById(R.id.edittext_enterGlucose)
        enterBloodPressure = view.findViewById(R.id.edittext_enterBloodPressure)
        enterPulse = view.findViewById(R.id.edittext_enterPulse)
        enterQualityOfLife = view.findViewById(R.id.edittext_qualityOfLife)
        takePictureBtn = view.findViewById(R.id.textview_btnTakePic)
        chooseImage = view.findViewById(R.id.imageview_picture)
        saveButton = view.findViewById(R.id.button_saveButton)
        entertime = view.findViewById(R.id.et_time)
        //uploadDate = view.findViewById(R.id.textview_uploadTime)

        takePicture()
        pickSDate()
        onSaveClickListener()
        getAppointmentDateTimeCalendar()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        val vitalId = arguments?.getInt("vitalId") ?: -1
        vitalDao.getVitalLiveData(vitalId).observe(viewLifecycleOwner) {
            if (it != null)
            {
                vitalEntity = it
                entertime.text = it.time
                enterCholesterol.setText(it.cholesterol)
                enterFitness.setText(it.fitness)
                enterGlucose.setText(it.glucose)
                enterPulse.setText(it.pulse)
                enterBloodPressure.setText(it.bloodpressure)
                enterQualityOfLife.setText(it.physicalact)
            }
        }
    }

    private fun insertDataToDataBase()
    {
        if (validateInput())
        {
            val date = entertime.text.toString()
            val cholesterol = enterCholesterol.text.toString()
            val fitness = enterFitness.text.toString()
            val glucose = enterGlucose.text.toString()
            val bloodpressure = enterBloodPressure.text.toString()
            val pulse = enterPulse.text.toString()
            val physicalact = enterQualityOfLife.text.toString()

            if (inputCheck(date, cholesterol, fitness, glucose, bloodpressure, pulse, physicalact))
            {
                Toast.makeText(requireContext(), "Form validate", Toast.LENGTH_SHORT).show()
                val vital = VitalEntity(date, cholesterol, fitness, glucose, bloodpressure, pulse, physicalact)

                lifecycleScope.launch(Dispatchers.IO) {
                    vitalDao.addVital(vital)
                }
                findNavController().popBackStack()
            }
        }
    }

    private fun inputCheck(
        date: String, cholesterol: String, fitness: String, glucose: String,
        bloodpressure: String, pulse: String, physicalact: String,
    ): Boolean
    {
        return !(TextUtils.isEmpty(date) && (TextUtils.isEmpty(cholesterol))
                && (TextUtils.isEmpty(fitness)) && (TextUtils.isEmpty(glucose))
                && (TextUtils.isEmpty(bloodpressure)) && (TextUtils.isEmpty(pulse))
                && (TextUtils.isEmpty(physicalact)))
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

    private fun onSaveClickListener()
    {
        saveButton.setOnClickListener {
            insertDataToDataBase()
        }
    }

    private fun validateInput(): Boolean
    {
        if (entertime.text.toString().equals(""))
        {
            entertime.setError("Please Enter Date")
            return false
        }
        return true
    }

    private fun getAppointmentDateTimeCalendar()
    {
        val cal = Calendar.getInstance()
        Day = cal.get(Calendar.DAY_OF_MONTH)
        Month = cal.get(Calendar.MONTH)
        Year = cal.get(Calendar.YEAR)
    }

    private fun pickSDate()
    {
        entertime.setOnClickListener {
            getAppointmentDateTimeCalendar()

            DatePickerDialog(requireContext(), fromListener, Year, Month, Day)
                .show()
        }
    }

    private val fromListener = DatePickerDialog.OnDateSetListener { datePicker: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
        SavedDay = dayOfMonth
        SavedMonth = month
        SavedYear = year
        getAppointmentDateTimeCalendar()
        entertime.text = "$SavedDay - $SavedMonth - $SavedYear"
    }

    override fun onDateSet(datePicker: DatePicker?, year: Int, month: Int, dayOfMonth: Int)
    {
        SavedDay = dayOfMonth
        SavedMonth = month
        SavedYear = year
        entertime.text = "$SavedDay - $SavedMonth - $SavedYear"
    }
}