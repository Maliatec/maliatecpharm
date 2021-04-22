package com.maliatecpharm.activity.mainmenu.fragments

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.maliatecpharm.R
import com.maliatecpharm.activity.mainmenu.activities.ActivityAddDiagnosis
import com.maliatecpharm.activity.mainmenu.activities.ActivityMainMenu
import com.maliatecpharm.activity.mainmenu.adapter.*
import com.maliatecpharm.activity.mainmenu.data.AppDataBase
import com.maliatecpharm.activity.mainmenu.data.MedicineDao
import com.maliatecpharm.activity.mainmenu.data.MedicineEntity
import com.maliatecpharm.activity.mainmenu.data.ProfileEntity
import com.maliatecpharm.activity.mainmenu.uimodel.AlarmCount
import com.maliatecpharm.activity.mainmenu.uimodel.InstructionsUIModel
import com.maliatecpharm.activity.mainmenu.uimodel.MedicationTypeUIModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class FragmentAddMedication : Fragment(),
    MedicationTypeAdapter.MedicationTypeInteractor,
    //    DayNameAdapter.DayNameInteractor,
    //    AlarmCountAdapter.AlarmCountInteractor,
    AdapterView.OnItemSelectedListener
//  DatePickerDialog.OnDateSetListener
{
    private val medicineDao: MedicineDao by lazy {
        AppDataBase.getDataBase(requireContext()).medicineDao()
    }

    private val medicationTypeAdapter by lazy {
        MedicationTypeAdapter(context = requireContext(), medicationTypeInteractor = this)
    }

    private var medicineEntity = MedicineEntity("",
        "", "")


    //    private val alarmCountAdapter by lazy {
    //        AlarmCountAdapter(context = requireContext(), interactor = this)
    //    }

    private val instructionsAdapter by lazy {
        InstructionsAdapter(context = requireContext()).apply {
            onMedicationInstructionClicked = { clickedItem ->
                val updatedList = instructionsList.map { item ->
                    item.copy(colorRes = getColor(selected = item.id == clickedItem.id))
                }
                updateList(updatedList)
            }
        }
    }

    //    private val daynameAdapter by lazy {
    //        DayNameAdapter(context = requireContext(), dayNameInteractor = this)
    //    }


    private val instructionsList = listOf(
        InstructionsUIModel(id = 1, name = "No instructions", colorRes = R.color.teal_200),
        InstructionsUIModel(id = 2, name = "Before eating"),
        InstructionsUIModel(id = 3, name = "After eating"),
        InstructionsUIModel(id = 4, name = "While eating")
    )

    private val medicationTypeList = listOf(
        MedicationTypeUIModel(id = 1, name = "Syrup", medicationImageRes = R.drawable.image, colorRes = R.color.teal_200),
        MedicationTypeUIModel(id = 2, name = "Capsule", medicationImageRes = R.drawable.images),
        MedicationTypeUIModel(id = 3, name = "Syringe", medicationImageRes = R.drawable.syringue),
        MedicationTypeUIModel(id = 4, name = "Tablet", medicationImageRes = R.drawable.tablet),

        )

    //    var lastClickedAlarmCount = 1
    //
    //    private var alarmsCountList = listOf(
    //        AlarmCount(1, "Once a day", mutableListOf("02:00")),
    //        AlarmCount(2, "2 times a day", mutableListOf("08:00", "20:00")),
    //        AlarmCount(3, "3 times a day", mutableListOf("08:00", "14:00", "20:00")),
    //        AlarmCount(4, "4 times a day", mutableListOf("08:00", "12:00", "16:00", "20:00")),
    //        AlarmCount(5, "5 times a day", mutableListOf("08:00", "11:00", "14:00", "17:00", "20:00")),
    //        AlarmCount(6, "6 times a day", mutableListOf("08:00", "10:00", "11:00")),
    //        AlarmCount(7, "7 times a day", mutableListOf("08:00", "20:00")),
    //    )

    private val pillsList = arrayOf(
        "Pill(s)", "CC", "MI", "Gr", "Mg",
        "Drop(s)", "Piece(s)", "Puff(s)", "Unit(s)",
        "Teaspoon", "Patch", "Mcg", "lu", "Meq", "Cartoon", "Spray"
    )

    //    private val selectedDayIds: MutableList<Int> = mutableListOf(1)
    //
    //    private val dayNameList = listOf<Day>(
    //        Day(id = 1, name = "Sunday", colorRes = R.color.teal_200),
    //        Day(id = 2, name = "Monday"),
    //        Day(id = 3, name = "Tuesday"),
    //        Day(id = 4, name = "Wednesday"),
    //        Day(id = 5, name = "Thursday"),
    //        Day(id = 6, name = "Friday"),
    //        Day(id = 7, name = "Saturday")
    //    )

    private val medicinesList = mutableListOf<String>(
        "Cyclophosphamide",
        "Panadol", "Paracetamol", "Aspirin",
        "Aspicot", "Prozac", "Dareq", "Oradus",
        "Advil", "EuroFer", "Other"
    )

    private val conditionsList = mutableListOf<String>(
        "Cancer", "Heart Disease", "Kidney problems",
        "Pulmonary Disease", "Rhumatism", "Bone Problems", "Immunity Problems",
        "Eyes Problems"
    )

    private lateinit var pillsSpinner: Spinner
    private lateinit var medicationName: TextView
    private lateinit var diagnosis: TextView
    private lateinit var diagnosisSpinner: Spinner
    private lateinit var condition: TextView
    private lateinit var dosage: TextView
    private lateinit var enterDosage: EditText
    private lateinit var medicationTypeTv: TextView
    private lateinit var instructionsTv: TextView
    private lateinit var instructionsRecyclerView: RecyclerView
    private lateinit var medicationTypeRecyclerView: RecyclerView
    private lateinit var medicinesSpinner: Spinner
    private lateinit var medicineName: EditText
    val context = this
    private lateinit var addImage: ImageView
    private lateinit var addDiagImage: ImageView
    private lateinit var saveBtn: Button

    //    private lateinit var alarmRecyclerView: RecyclerView
    //  private lateinit var reminderSwitch: SwitchMaterial
    //    private lateinit var timesSpinner: Spinner
    // private lateinit var reminderTv: TextView
    //    private lateinit var tvSelectedDays: TextView
    //    private lateinit var atTv: TextView
    //    private lateinit var dayRecyclerView: RecyclerView
    //    private lateinit var dayCountRecyclerView: RecyclerView
    //    private lateinit var onText: TextView
    //    private lateinit var confirmButton: Button
    //    private lateinit var checkBox: CheckBox
    //    private lateinit var btn1Date: Button
    //    private lateinit var textDate1: TextView
    //    private lateinit var btn2Date: Button
    //    private lateinit var textDate2: TextView
    //    private lateinit var time1: TextView
    //    private lateinit var time2: TextView
    //    private lateinit var getMedName: String
    //    private lateinit var getDiagnosis: String
    //    var sDay = 0
    //    var sMonth = 0
    //    var sYear = 0
    //    var fDay = 0
    //    var fMonth = 0
    //    var fYear = 0
    //    var sSavedDay = 0
    //    var sSavedMonth = 0
    //    var sSavedYear = 0
    //
    //    var fSavedDay = 0
    //    var fSavedMonth = 0
    //    var fSavedYear = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View?
    {
        val view = inflater.inflate(R.layout.fragment_add_medication, container, false)

        pillsSpinner = view.findViewById(R.id.spinner_pillsSpinner)
        medicationName = view.findViewById(R.id.textview_medicationName)
        diagnosis = view.findViewById(R.id.textview_nameOfDiseaseTextview)
        diagnosisSpinner = view.findViewById(R.id.spinner_diagnosisSpinner)
        condition = view.findViewById(R.id.diagnosis)
        dosage = view.findViewById(R.id.textview_dosage)
        enterDosage = view.findViewById(R.id.edittext_dosage)
        instructionsTv = view.findViewById(R.id.textview_instructions)
        medicationTypeTv = view.findViewById(R.id.textview_medicationType)
        instructionsRecyclerView = view.findViewById(R.id.recyclerview_instructions)
        medicationTypeRecyclerView = view.findViewById(R.id.recyclerview_medicationType)
        medicinesSpinner = view.findViewById(R.id.spinner_medicinesList)
        medicineName = view.findViewById(R.id.medicineName)
        addImage = view.findViewById(R.id.addImage)
        addDiagImage = view.findViewById(R.id.addDiagnosisImage)
        saveBtn = view.findViewById(R.id.Save)
        setupTextViews()
        medSpinner()
        diagnosisSpinner()
        populateInstructionsRecycleView()
        populateTypeRecycleView()
        medicineNameSpinner()
        onSaveClickListener()
        onImageClickListener()
        onImageeClickListener()
        linkMedicinesSpinnerToEditText()
        linkDiagnosisSpinnerToEditText()

        //        reminderSwitch = view.findViewById(R.id.switch_reminder)
        //        reminderTv = view.findViewById(R.id.textview_reminder)
        //        tvSelectedDays = view.findViewById(R.id.textview_selectedDays)
        //        timesSpinner = view.findViewById(R.id.spinner_timeSpinner)
        //        atTv = view.findViewById(R.id.textview_At)
        //        onText = view.findViewById(R.id.textview_On)
        //        confirmButton = view.findViewById(R.id.button_confirmbtn)
        //        alarmRecyclerView = view.findViewById(R.id.recyclerview_alarmCount)
        //        dayRecyclerView = view.findViewById(R.id.recylerview_day)
        //        dayCountRecyclerView = view.findViewById(R.id.recylerview_dayCount)
        //        checkBox = view.findViewById(R.id.checkbox_repeat)
        //        btn1Date = view.findViewById(R.id.button_timePickerBtn1)
        //        textDate1 = view.findViewById(R.id.textview_textTime1)
        //        btn2Date = view.findViewById(R.id.button_timePickerbtn2)
        //        textDate2 = view.findViewById(R.id.textview_textTime2)
        //        time1 = view.findViewById(R.id.textview_Time1)
        //        time2 = view.findViewById(R.id.textview_Time2)
        //        populateAlarmCountRecycleView()
        //        populateTimeSpinner()
        //        setReminderSwitchListener()
        //        popRecyclerView()
        //          setOnButtonClicked()
        //        pickSDate()
        //        pickFDate()
        //  linkMedicinesSpinnerToEditText()
        //   linkDiagnosisSpinnerToEditText()
        //        addNewMedication()
        //        addDiagnosis()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        val medId = arguments?.getInt("medId") ?: -1
        medicineDao.getMedLiveData(medId).observe(viewLifecycleOwner) {

            if (it != null)
            {
                medicineEntity = it
                medicationName.setText(it.name)
                enterDosage.setText(it.dosage)
                condition.setText(it.diagnosis)
            }
        }
    }

    private fun onImageClickListener()
    {
        addImage.setOnClickListener {
            findNavController().navigate(R.id.action_addMedicationFragment_to_fragmentAddMedicine)
        }
    }

    private fun onImageeClickListener()
    {
        addDiagImage.setOnClickListener {
            startActivity(Intent(requireContext(), ActivityAddDiagnosis::class.java))
        }
    }


    private fun onSaveClickListener()
    {
        saveBtn.setOnClickListener {
            insertDataToDataBase()
            findNavController().navigate(R.id.action_addMedicationFragment_to_fragmentAddMedicine)
        }
    }

    private fun insertDataToDataBase()
    {
        val medicineName = medicineName.text.toString()
        val dosage = enterDosage.text.toString()
        val diagnosis = condition.text.toString()

        if (inputCheck(medicineName, dosage, diagnosis))
        {
            val medicine = MedicineEntity(medicineName, dosage, diagnosis)
            lifecycleScope.launch(Dispatchers.IO) {
                medicineDao.addMedicine(medicine)
            }
            Toast.makeText(requireContext(), "Medicine Added ", Toast.LENGTH_SHORT).show()
            //  findNavController().popBackStack()
            // finish()
            //           findNavController().navigate(R.id.action_MedicationsFragment_to_blankFragment)
        }
        else
            Toast.makeText(requireContext(), "Please fill medicine name ", Toast.LENGTH_SHORT).show()
    }

    private fun inputCheck(name: String, dosage: String, diagnosis: String): Boolean
    {
        return !(TextUtils.isEmpty(name) && TextUtils.isEmpty(dosage) && TextUtils.isEmpty(diagnosis)
                )
    }

    private fun setupTextViews()
    {
        dosage.text = getString(R.string.dosage)
        instructionsTv.text = getString(R.string.instructions)
        medicationTypeTv.text = getString(R.string.medication_type)
        medicationName.text = getString(R.string.medication_title)
    }

    private fun medSpinner()
    {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, pillsList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        pillsSpinner.adapter = adapter
    }

    private fun medicineNameSpinner()
    {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, medicinesList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        medicinesSpinner.adapter = adapter
    }

    private fun linkMedicinesSpinnerToEditText()
    {
        medicinesSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                medicineName.setText(medicinesSpinner.selectedItem.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?)
            {
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
    {
    }

    override fun onNothingSelected(parent: AdapterView<*>?)
    {
    }

    private fun diagnosisSpinner()
    {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, conditionsList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        diagnosisSpinner.adapter = adapter
    }

    private fun linkDiagnosisSpinnerToEditText()
    {
        diagnosisSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                condition.setText(diagnosisSpinner.selectedItem.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?)
            {
            }
        }
    }

    private fun populateInstructionsRecycleView()
    {
        instructionsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = instructionsAdapter
            instructionsAdapter.updateList(instructionsList)
        }
    }

    private fun populateTypeRecycleView()
    {
        medicationTypeRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = medicationTypeAdapter
            medicationTypeAdapter.updateList(medicationTypeList)
        }
    }

    override fun onMedicationTypeClicked(medicationType: MedicationTypeUIModel)
    {
        val updatedList = medicationTypeList.map { item ->
            item.copy(colorRes = getColor(selected = item.id == medicationType.id))
        }
        medicationTypeAdapter.updateList(updatedList)
    }

    private fun getColor(selected: Boolean) = if (selected) R.color.teal_200 else R.color.white

    //    private fun addNewMedication()
    //    {
    //        addMedication.setOnClickListener {
    //            getMedName = medicineName.toString()
    //            medicinesList.add(getMedName)
    //            Toast.makeText(requireContext(), "Medication Added", Toast.LENGTH_LONG).show()
    //        }
    //    }
    //
    //    private fun addDiagnosis()
    //    {
    //        addDiagnosis.setOnClickListener {
    //            getDiagnosis = condition.toString()
    //            conditionsList.add(getDiagnosis)
    //            Toast.makeText(requireContext(), "Diagnosis Added", Toast.LENGTH_LONG).show()
    //        }
    //    }

    //    private fun populateAlarmCountRecycleView()
    //    {
    //        alarmRecyclerView.apply {
    //            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
    //            adapter = alarmCountAdapter
    //        }
    //    }
    //
    //    private fun populateTimeSpinner()
    //    {
    //        val times = alarmsCountList.map { item -> item.text }
    //        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, times)
    //        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    //        timesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
    //        {
    //            override fun onItemSelected(
    //                parent: AdapterView<*>?,
    //                view: View?,
    //                position: Int,
    //                id: Long
    //            )
    //            {
    //                val timeClickedText = times[position] // example: 3 times a day
    //                // search in original times to find a match between an item and the text clicked
    //                val itemClicked = alarmsCountList.first { item -> item.text == timeClickedText }
    //                lastClickedAlarmCount = itemClicked.id
    //                alarmCountAdapter.updateList(itemClicked.timeList)
    //            }
    //            override fun onNothingSelected(parent: AdapterView<*>?)
    //            {
    //            }
    //        }
    //        timesSpinner.adapter = adapter
    //    }

    //    private fun setReminderSwitchListener()
    //    {
    //        reminderSwitch.setOnCheckedChangeListener { _, isChecked ->
    //            val visibility = if (isChecked) View.VISIBLE else View.GONE
    //
    //            //
    //            reminderTv.visibility = visibility
    //            timesSpinner.visibility = visibility
    //            atTv.visibility = visibility
    //            alarmRecyclerView.visibility = visibility
    //            onText.visibility = visibility
    //            dayRecyclerView.visibility = visibility
    //            dayCountRecyclerView.visibility = visibility
    //            checkBox.visibility = visibility
    //            btn1Date.visibility = visibility
    //            textDate1.visibility = visibility
    //            btn2Date.visibility = visibility
    //            textDate2.visibility = visibility
    //        }
    //    }

    //    private fun popRecyclerView()
    //    {
    //        dayRecyclerView.apply {
    //            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
    //            adapter = daynameAdapter
    //            daynameAdapter.updateList(dayNameList)
    //        }
    //    }


    //    override fun onDayClicked(day: Day)
    //    {
    //        if (selectedDayIds.contains(day.id))
    //        {
    //            selectedDayIds.remove(day.id)
    //        }
    //        else
    //        {
    //            selectedDayIds.add(day.id)
    //        }
    //
    //        val updatedList = dayNameList.map { item ->
    //            item.copy(colorRes = getColor(selected = selectedDayIds.contains(item.id)))
    //        }
    //
    //        daynameAdapter.updateList(updatedList)
    //
    //        val selectedDaysText = dayNameList
    //            .filter { item -> selectedDayIds.contains(item.id) }
    //            .joinToString { item -> item.name }
    //
    //        tvSelectedDays.text = selectedDaysText
    //    }

    //        dayNameList.forEach { item ->
    //            if (item.id == day.id)
    //            {
    //                item.Selected = !item.Selected
    //            }
    //        }
    //
    //        fun getSelectedDays() = dayNameList
    //            .filter { item -> item.Selected }
    //            .map { item -> item.id }
    //
    //
    //        val updatedList = dayNameList.map { item ->
    //            item.copy(colorRes = getColor(selected = item.id == day.id))
    //        }
    //        daynameAdapter.updateList(dayNameList)
    //
    //        tvSelectedDays.text = day.name
    //
    //    }

    //    override fun onAlarmTimeClicked(position: Int)
    //    {
    //        fun updateList(hour: Int, minute: Int)
    //        {
    //            val text = "$hour:$minute"
    //            val newList = alarmsCountList
    //                .first { item -> item.id == lastClickedAlarmCount }
    //                .timeList.apply { set(position, text) }
    //
    //            alarmCountAdapter.updateList(newList)
    //        }
    //
    //        TimePickerDialog(
    //            requireContext(),
    //            { _, hour, minute -> updateList(hour, minute) },
    //            0,
    //            0, true
    //        )
    //            .show()
    //    }
    //    private fun setOnButtonClicked()
    //    {
    //        confirmButton.setOnClickListener {
    //            //startActivity(Intent(this, Doctors::class.java))
    //        }
    //    }

    //    private fun getSDateCalendar()
    //    {
    //        val cal = Calendar.getInstance()
    //        sDay = cal.get(Calendar.DAY_OF_MONTH)
    //        sMonth = cal.get(Calendar.MONTH)
    //        sYear = cal.get(Calendar.YEAR)
    //    }
    //
    //    private fun getFDateCalendar()
    //    {
    //        val cal = Calendar.getInstance()
    //        fDay = cal.get(Calendar.DAY_OF_MONTH)
    //        fMonth = cal.get(Calendar.MONTH)
    //        fYear = cal.get(Calendar.YEAR)
    //    }
    //
    //
    //    private fun pickSDate()
    //    {
    //        btn1Date.setOnClickListener {
    //            getSDateCalendar()
    //            DatePickerDialog(requireContext(), fromListener, sYear, sMonth, sDay).show()
    //        }
    //    }
    //
    //    private fun pickFDate()
    //    {
    //        btn2Date.setOnClickListener {
    //            getSDateCalendar()
    //            DatePickerDialog(requireContext(), toListener, sYear, sMonth, sDay).show()
    //        }
    //    }
    //
    //    private val fromListener = DatePickerDialog.OnDateSetListener { datePicker: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
    //
    //        sSavedDay = dayOfMonth
    //        sSavedMonth = month
    //        sSavedYear = year
    //        getSDateCalendar()
    //        textDate1.text = "From $sSavedDay - $sSavedMonth - $sSavedYear"
    //    }
    //
    //    private val toListener = DatePickerDialog.OnDateSetListener { datePicker: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
    //
    //        fSavedDay = dayOfMonth
    //        fSavedMonth = month
    //        fSavedYear = year
    //        getFDateCalendar()
    //        textDate2.text = "To $fSavedDay - $fSavedMonth - $fSavedYear"
    //    }
    //
    //    override fun onDateSet(datePicker: DatePicker?, year: Int, month: Int, dayOfMonth: Int)
    //    {
    //        sSavedDay = dayOfMonth
    //        sSavedMonth = month
    //        sSavedYear = year
    //        textDate2.text = "$fSavedDay - $fSavedMonth - $fSavedYear"
    //    }

}

