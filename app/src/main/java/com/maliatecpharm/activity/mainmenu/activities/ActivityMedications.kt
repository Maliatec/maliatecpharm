package com.maliatecpharm.activity.mainmenu.activities

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.maliatecpharm.R
import com.maliatecpharm.activity.mainmenu.database.Medication
import com.maliatecpharm.activity.mainmenu.database.MedicationsDataBase
import com.maliatecpharm.adapter.*
import com.maliatecpharm.uimodel.AlarmCount
import com.maliatecpharm.uimodel.InstructionsUIModel
import com.maliatecpharm.uimodel.MedicationTypeUIModel
import java.util.*

class ActivityMedications : AppCompatActivity(),
    InstructionsAdapter.InstructionsInteractor,
    MedicationTypeAdapter.MedicationTypeInteractor,
    DayNameAdapter.DayNameInteractor,
    AlarmCountAdapter.AlarmCountInteractor,
    AdapterView.OnItemSelectedListener
{

    private val medicationTypeAdapter by lazy {
        MedicationTypeAdapter(context = this, medicationTypeInteractor = this)
    }

    private val alarmCountAdapter by lazy {
        AlarmCountAdapter(context = this, interactor = this)
    }

    private val instructionsAdapter by lazy {
        InstructionsAdapter(context = this, instructionsInteractor = this)
    }

    private val daynameAdapter by lazy {
        DayNameAdapter(context = this@ActivityMedications, dayNameInteractor = this@ActivityMedications)
    }


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

    var lastClickedAlarmCount = 1

    private var alarmsCountList = listOf(
        AlarmCount(1, "Once a day", mutableListOf("02:00")),
        AlarmCount(2, "2 times a day", mutableListOf("08:00", "20:00")),
        AlarmCount(3, "3 times a day", mutableListOf("08:00", "14:00", "20:00")),
        AlarmCount(4, "4 times a day", mutableListOf("08:00", "12:00", "16:00", "20:00")),
        AlarmCount(5, "5 times a day", mutableListOf("08:00", "11:00", "14:00", "17:00", "20:00")),
        AlarmCount(6, "6 times a day", mutableListOf("08:00", "10:00", "11:00")),
        AlarmCount(7, "7 times a day", mutableListOf("08:00", "20:00")),
    )

    private val pillsList = arrayOf(
        "Pill(s)", "CC", "MI", "Gr", "Mg",
        "Drop(s)", "Piece(s)", "Puff(s)", "Unit(s)",
        "Teaspoon", "Patch", "Mcg", "lu", "Meq", "Cartoon", "Spray"
    )

    private val selectedDayIds: MutableList<Int> = mutableListOf(1)

    private val dayNameList = listOf<Day>(
        Day(id = 1, name = "Sunday", colorRes = R.color.teal_200),
        Day(id = 2, name = "Monday"),
        Day(id = 3, name = "Tuesday"),
        Day(id = 4, name = "Wednesday"),
        Day(id = 5, name = "Thursday"),
        Day(id = 6, name = "Friday"),
        Day(id = 7, name = "Saturday")
    )

    private val medicinesList = arrayOf(
        "Cyclophosphamide",
        "Panadol", "Paracetamol", "Aspirin",
        "Aspicot", "Prozac", "Dareq", "Oradus",
        "Advil", "EuroFer", "Other"
    )

    private val conditionsList = arrayOf(
        "Cancer", "Heart Disease", "Kidney problems",
        "Pulmonary Disease", "Rhumatism", "Bone Problems", "Immunity Problems",
        "Eyes Problems"
    )


    private lateinit var pillsSpinner: Spinner
    private lateinit var medicationName: TextView
    private lateinit var diagnosis: TextView
    private lateinit var conditionsSpinner: Spinner
    private lateinit var condition: TextView
    private lateinit var dosage: TextView
    private lateinit var medicationTypeTv: TextView
    private lateinit var instructionsTv: TextView
    private lateinit var instructionsRecyclerView: RecyclerView
    private lateinit var medicationTypeRecyclerView: RecyclerView
    private lateinit var alarmRecyclerView: RecyclerView
    private lateinit var reminderSwitch: SwitchMaterial
    private lateinit var timesSpinner: Spinner
    private lateinit var reminderTv: TextView
    private lateinit var tvSelectedDays: TextView
    private lateinit var atTv: TextView
    private lateinit var dayRecyclerView: RecyclerView
    private lateinit var dayCountRecyclerView: RecyclerView
    private lateinit var onText: TextView
    private lateinit var confirmButton: Button
    private lateinit var checkBox: CheckBox
    private lateinit var btn1Date: Button
    private lateinit var textDate1: TextView
    private lateinit var btn2Date: Button
    private lateinit var textDate2: TextView
    private lateinit var medicinesSpinner: Spinner
    private lateinit var medicineName: EditText
    val context = this
    private lateinit var addBtn: Button


    var sHour = 0
    var sMinute = 0

    var sSavedDay = 0
    var sSavedMonth = 0
    var sSavedYear = 0
    var sSavedHour = 0
    var sSavedMinute = 0

    var fSavedDay = 0
    var fSavedMonth = 0
    var fSavedYear = 0
    var fSavedHour = 0
    var fSavedMinute = 0

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medications)

        setupViews()
        setupTextViews()
        medSpinner()
        conditionSpinner()
        populateInstructionsRecycleView()
        populateTypeRecycleView()
        populateAlarmCountRecycleView()
        populateTimeSpinner()
        setReminderSwitchListener()
        popRecyclerView()
        setOnButtonClicked()
        pickSDate()
        pickFDate()
        medicineNameSpinner()
        onAddBtnClickListener()



    }

    private fun setupViews()
    {
        pillsSpinner = findViewById(R.id.spinner_pillsSpinner)
        medicationName = findViewById(R.id.textview_medicationName)
        addBtn = findViewById(R.id.button_addBtn)
        diagnosis = findViewById(R.id.textview_nameOfDiseaseTextview)
        conditionsSpinner = findViewById(R.id.spinner_conditionsSpinner)
        condition = findViewById(R.id.textview_nameOfCondition)
        dosage = findViewById(R.id.textview_dosage)
        instructionsTv = findViewById(R.id.textview_instructions)
        medicationTypeTv = findViewById(R.id.textview_medicationType)
        instructionsRecyclerView = findViewById(R.id.recyclerview_instructions)
        medicationTypeRecyclerView = findViewById(R.id.recyclerview_medicationType)
        reminderSwitch = findViewById(R.id.switch_reminder)
        reminderTv = findViewById(R.id.textview_reminder)
        tvSelectedDays = findViewById(R.id.textview_selectedDays)
        timesSpinner = findViewById(R.id.spinner_timeSpinner)
        atTv = findViewById(R.id.textview_At)
        onText = findViewById(R.id.textview_On)
        confirmButton = findViewById(R.id.button_confirmbtn)
        alarmRecyclerView = findViewById(R.id.recyclerview_alarmCount)
        dayRecyclerView = findViewById(R.id.recylerview_day)
        dayCountRecyclerView = findViewById(R.id.recylerview_dayCount)
        checkBox = findViewById(R.id.checkbox_repeat)
        btn1Date = findViewById(R.id.button_timePickerBtn1)
        textDate1 = findViewById(R.id.textview_dateOfBirthday)
        btn2Date = findViewById(R.id.button_timePickerbtn2)
        textDate2 = findViewById(R.id.textview_textTime2)
        medicinesSpinner = findViewById(R.id.spinner_medicinesList)
        medicineName = findViewById(R.id.edittext_nameOfMedicine)


    }


    private fun setupTextViews()
    {
        dosage.text = getString(R.string.dosage)
        instructionsTv.text = getString(R.string.instructions)
        medicationTypeTv.text = getString(R.string.medication_type)
        medicationName.text = getString(R.string.medication_title)
        reminderSwitch.text = getString(R.string.switch_reminder_text)
    }

    private fun onAddBtnClickListener()
    {
        addBtn.setOnClickListener {

            if (medicineName.text.toString().isNotEmpty())
            {
                var user = Medication(medicineName.text.toString())
                var db = MedicationsDataBase(context)
                db.insertDATA(user)

            }
            else Toast.makeText(context, "please fill all data", Toast.LENGTH_SHORT).show()
        }

    }

    private fun medSpinner()
    {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, pillsList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        pillsSpinner.adapter = adapter
    }

    private fun medicineNameSpinner()
    {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, medicinesList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        medicinesSpinner.adapter = adapter
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
    {

    }

    override fun onNothingSelected(parent: AdapterView<*>?)
    {

    }

    private fun conditionSpinner()
    {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, conditionsList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        conditionsSpinner.adapter = adapter
    }

    private fun populateInstructionsRecycleView()
    {
        instructionsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ActivityMedications, RecyclerView.HORIZONTAL, false)
            adapter = instructionsAdapter
            instructionsAdapter.updateList(instructionsList)
        }
    }

    private fun populateTypeRecycleView()
    {
        medicationTypeRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ActivityMedications, RecyclerView.HORIZONTAL, false)
            adapter = medicationTypeAdapter
            medicationTypeAdapter.updateList(medicationTypeList)
        }
    }

    private fun populateAlarmCountRecycleView()
    {
        alarmRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ActivityMedications, RecyclerView.HORIZONTAL, false)
            adapter = alarmCountAdapter
        }
    }


    private fun populateTimeSpinner()
    {
        val times = alarmsCountList.map { item -> item.text }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, times)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        timesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            )
            {
                val timeClickedText = times[position] // example: 3 times a day
                // search in original times to find a match between an item and the text clicked
                val itemClicked = alarmsCountList.first { item -> item.text == timeClickedText }
                lastClickedAlarmCount = itemClicked.id
                alarmCountAdapter.updateList(itemClicked.timeList)
            }

            override fun onNothingSelected(parent: AdapterView<*>?)
            {
            }
        }
        timesSpinner.adapter = adapter
    }


    private fun setReminderSwitchListener()
    {
        reminderSwitch.setOnCheckedChangeListener { _, isChecked ->
            val visibility = if (isChecked) View.VISIBLE else View.GONE

            //
            reminderTv.visibility = visibility
            timesSpinner.visibility = visibility
            atTv.visibility = visibility
            alarmRecyclerView.visibility = visibility
            onText.visibility = visibility
            dayRecyclerView.visibility = visibility
            dayCountRecyclerView.visibility = visibility
            checkBox.visibility = visibility
            btn1Date.visibility = visibility
            textDate1.visibility = visibility
            btn2Date.visibility = visibility
            textDate2.visibility = visibility


        }
    }

    private fun popRecyclerView()
    {
        dayRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ActivityMedications, RecyclerView.HORIZONTAL, false)
            adapter = daynameAdapter
            daynameAdapter.updateList(dayNameList)
        }
    }

    override fun onInstructionClicked(instructionsUIModel: InstructionsUIModel)
    {
        val updatedList = instructionsList.map { item ->
            item.copy(colorRes = getColor(selected = item.id == instructionsUIModel.id))
        }

        instructionsAdapter.updateList(updatedList)

        //                instructionsList.forEach { item ->
        //                    if (item.id == instructionsUIModel.id) {
        //                        item.selected = !item.selected
        //                    }
        //                }
        //
        //                instructionsAdapter.updateList(instructionsList)
        //            }
        //
        //            private fun getSelectedInstructions() = instructionsList
        //                .filter { item -> item.selected }
        //                .map { item -> item.id }

    }

    override fun onMedicationTypeClicked(medicationType: MedicationTypeUIModel)
    {
        val updatedList = medicationTypeList.map { item ->
            item.copy(colorRes = getColor(selected = item.id == medicationType.id))
        }

        medicationTypeAdapter.updateList(updatedList)
    }

    private fun getColor(selected: Boolean) = if (selected) R.color.teal_200 else R.color.white


    override fun onDayClicked(day: Day)
    {
        if (selectedDayIds.contains(day.id))
        {
            selectedDayIds.remove(day.id)
        }
        else
        {
            selectedDayIds.add(day.id)
        }

        val updatedList = dayNameList.map { item ->
            item.copy(colorRes = getColor(selected = selectedDayIds.contains(item.id)))
        }

        daynameAdapter.updateList(updatedList)

        val selectedDaysText = dayNameList
            .filter { item -> selectedDayIds.contains(item.id) }
            .joinToString { item -> item.name }

        tvSelectedDays.text = selectedDaysText
    }


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

    override fun onAlarmTimeClicked(position: Int)
    {
        fun updateList(hour: Int, minute: Int)
        {
            val text = "$hour:$minute"
            val newList = alarmsCountList
                .first { item -> item.id == lastClickedAlarmCount }
                .timeList.apply { set(position, text) }

            alarmCountAdapter.updateList(newList)
        }

        TimePickerDialog(
            this,
            { _, hour, minute -> updateList(hour, minute) },
            0,
            0, true
        )
            .show()
    }


    private fun setOnButtonClicked()
    {
        confirmButton.setOnClickListener {
            //startActivity(Intent(this, Doctors::class.java))
        }
    }

    private fun pickSDate()
    {
        btn1Date.setOnClickListener {

            val cal = Calendar.getInstance()
            val sDay = cal.get(Calendar.DAY_OF_MONTH)
            val sMonth = cal.get(Calendar.MONTH)
            val sYear = cal.get(Calendar.YEAR)

            DatePickerDialog(this, fromListener, sYear, sMonth, sDay)
                .show()
        }
    }

    private fun pickFDate()
    {
        btn2Date.setOnClickListener {

            val cal = Calendar.getInstance()
            val fDay = cal.get(Calendar.DAY_OF_MONTH)
            val fMonth = cal.get(Calendar.MONTH)
            val fYear = cal.get(Calendar.YEAR)

            DatePickerDialog(this, toListener, fYear, fMonth, fDay)
                .show()
        }
    }

    val fromListener = OnDateSetListener { datePicker: DatePicker, year: Int, month: Int, dayOfMonth: Int ->

        sSavedDay = dayOfMonth
        sSavedMonth = month
        sSavedYear = year

        val cal = Calendar.getInstance()
        sHour = cal.get(Calendar.HOUR)
        sMinute = cal.get(Calendar.MINUTE)
        TimePickerDialog(this, fromTimeListener, sHour, sMinute, true).show()
    }

    val toListener = OnDateSetListener { datePicker: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
        fSavedDay = dayOfMonth
        fSavedMonth = month
        fSavedYear = year


        val cal = Calendar.getInstance()
        val fHour = cal.get(Calendar.HOUR)
        val fMinute = cal.get(Calendar.MINUTE)

        TimePickerDialog(this, toTimeListener, fHour, fMinute, true).show()

    }

    val fromTimeListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
        sSavedHour = hourOfDay
        sSavedMinute = minute
        textDate1.text = "Starting Date: \n$sSavedDay - $sSavedMonth - $sSavedYear \nHour: $sSavedHour Minute: $sSavedMinute"
    }

    val toTimeListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
        fSavedHour = hourOfDay
        fSavedMinute = minute
        textDate2.text = "Starting Date: \n$fSavedDay - $fSavedMonth - $fSavedYear \nHour: $fSavedHour Minute: $fSavedMinute"
    }
}


/// Regex

//val emailRegex = "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"
//val elieEmail = "Elie@gmail.com"
//
//val valid = elieEmail.isEmailValid()
//val valid2 = isValidEmail(elieEmail)
//
//// Extension funtion
//fun String.isEmailValid(): Boolean = matches(Regex(emailRegex))
//fun isValidEmail(email: String): Boolean = email.matches(Regex(emailRegex))
