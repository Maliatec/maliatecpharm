package com.maliatecpharm.activity


import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.maliatecpharm.R
import com.maliatecpharm.activity.mainmenu.Doctors
import com.maliatecpharm.activity.mainmenu.MainMenuActivity
import com.maliatecpharm.adapter.*
import com.maliatecpharm.uimodel.AlarmCount
import com.maliatecpharm.uimodel.InstructionsUIModel
import com.maliatecpharm.uimodel.MedicationTypeUIModel
import java.util.*


class Medications : AppCompatActivity(),
    InstructionsAdapter.InstructionsInteractor,
    MedicationTypeAdapter.MedicationTypeInteractor,
    DayNameAdapter.DayNameInteractor,
    AlarmCountAdapter.AlarmCountInteractor,
    TimePickerDialog.OnTimeSetListener, AdapterView.OnItemSelectedListener
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
        DayNameAdapter(context = this@Medications, dayNameInteractor = this@Medications)
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
        MedicationTypeUIModel(id = 3, name = "Syringe",medicationImageRes =  R.drawable.syringue),
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

    private val MedicinesList = arrayOf(
        "Panadol", "Paracetamol", "Aspirin",
        "Aspicot", "Prozac", "Dareq", "Oradus",
        "Advil", "EuroFer", "Other"
    )

    private lateinit var pillsSpinner: Spinner
    private lateinit var medicationName: TextView
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
    private lateinit var medicineName: TextView


    var sDay = 0
    var sMonth = 0
    var sYear = 0
    var sHour = 0
    var sMinute = 0

    var sSavedDay = 0
    var sSavedMonth = 0
    var sSavedYear = 0
    var sSavedHour = 0
    var sSavedMinute = 0

    var fDay = 0
    var fMonth = 0
    var fYear = 0
    var fHour = 0
    var fMinute = 0

    var fSavedDay = 0
    var fSavedMonth = 0
    var fSavedYear = 0
    var fSavedHour = 0
    var fSavedMinute = 0


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.medications_layout)



        setupViews()
        setupTextViews()
        medSpinner()
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


    }

    private fun setupViews()
    {
        pillsSpinner = findViewById(R.id.spinner_pills)
        medicationName = findViewById(R.id.textview_medication_name)
        dosage = findViewById(R.id.textview_dosage)
        instructionsTv = findViewById(R.id.textview_instructions)
        medicationTypeTv = findViewById(R.id.textview_medication_type)
        instructionsRecyclerView = findViewById(R.id.recyclerview_instructions)
        medicationTypeRecyclerView = findViewById(R.id.recyclerview_medication_type)
        reminderSwitch = findViewById(R.id.switch_reminder)
        reminderTv = findViewById(R.id.textview_reminder)
        tvSelectedDays = findViewById(R.id.TVSelectedDays)
        timesSpinner = findViewById(R.id.spinner_time)
        atTv = findViewById(R.id.Text_View_At)
        onText = findViewById(R.id.TextView_ON)
        confirmButton = findViewById(R.id.btnConfirm)
        alarmRecyclerView = findViewById(R.id.recyclerview_alarm_count)
        dayRecyclerView = findViewById(R.id.Recylerview_day)
        dayCountRecyclerView = findViewById(R.id.Recylerview_day_count)
        checkBox = findViewById(R.id.repeat_Check_Box)
        btn1Date = findViewById(R.id.btn1_timePicker)
        textDate1 = findViewById(R.id.tv_textTime1)
        btn2Date = findViewById(R.id.btn2_timePicker)
        textDate2 = findViewById(R.id.tv_textTime2)
        medicinesSpinner = findViewById(R.id.medicinesList)
        medicineName = findViewById(R.id.nameOfMedicine)


    }





    private fun setupTextViews()
    {
        dosage.text = getString(R.string.dosage)
        instructionsTv.text = getString(R.string.instructions)
        medicationTypeTv.text = getString(R.string.medication_type)
        medicationName.text = getString(R.string.medication_title)
        reminderSwitch.text = getString(R.string.switch_reminder_text)

    }

    private fun medSpinner()
    {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, pillsList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        pillsSpinner.adapter = adapter
    }

    private fun medicineNameSpinner()
    {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, MedicinesList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        medicinesSpinner.adapter = adapter

        medicinesSpinner.onItemSelectedListener = this
    }
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
    {
        val text: String = parent?.getItemAtPosition(position).toString()
        medicineName.text = text
    }

    override fun onNothingSelected(parent: AdapterView<*>?)
    {

    }


    private fun populateInstructionsRecycleView()
    {
        instructionsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@Medications, RecyclerView.HORIZONTAL, false)
            adapter = instructionsAdapter
            instructionsAdapter.updateList(instructionsList)
        }
    }

    private fun populateTypeRecycleView()
    {
        medicationTypeRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@Medications, RecyclerView.HORIZONTAL, false)
            adapter = medicationTypeAdapter
            medicationTypeAdapter.updateList(medicationTypeList)
        }
    }

    private fun populateAlarmCountRecycleView()
    {
        alarmRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@Medications, RecyclerView.HORIZONTAL, false)
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
                val timeClickedText = times[position]
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
            layoutManager = LinearLayoutManager(this@Medications, RecyclerView.HORIZONTAL, false)
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
        val dummyText = "Elie"
        val newList = alarmsCountList
            .first { item -> item.id == lastClickedAlarmCount }
            .timeList.apply { set(position, dummyText) }

        alarmCountAdapter.updateList(newList)
    }


    private fun setOnButtonClicked()
    {
        confirmButton.setOnClickListener {
            startActivity(Intent(this, MainMenuActivity::class.java))
        }
    }


    private fun getSDateTimeCalendar()
    {
        val cal = Calendar.getInstance()
        sDay = cal.get(Calendar.DAY_OF_MONTH)
        sMonth = cal.get(Calendar.MONTH)
        sYear = cal.get(Calendar.YEAR)
        sHour = cal.get(Calendar.HOUR)
        sMinute = cal.get(Calendar.MINUTE)
    }

    private fun getFDateTimeCalendar()
    {
        val cal = Calendar.getInstance()
        fDay = cal.get(Calendar.DAY_OF_MONTH)
        fMonth = cal.get(Calendar.MONTH)
        fYear = cal.get(Calendar.YEAR)
        fHour = cal.get(Calendar.HOUR)
        fMinute = cal.get(Calendar.MINUTE)
    }

    private fun pickSDate()
    {
        btn1Date.setOnClickListener {
            getSDateTimeCalendar()

            DatePickerDialog(this, fromListener, sYear, sMonth, sDay)
                .show()
        }
    }

    private fun pickFDate()
    {
        btn2Date.setOnClickListener {
            getFDateTimeCalendar()

            DatePickerDialog(this, toListener, fYear, fMonth, fDay)
                .show()
        }
    }

    val fromListener = OnDateSetListener { datePicker: DatePicker, year: Int, month: Int, dayOfMonth: Int ->

        sSavedDay = dayOfMonth
        sSavedMonth = month
        sSavedYear = year

        getSDateTimeCalendar()

        TimePickerDialog(this, this, sHour, sMinute, true).show()

    }

    val toListener = OnDateSetListener { datePicker: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
        fSavedDay = dayOfMonth
        fSavedMonth = month
        fSavedYear = year

        getFDateTimeCalendar()

        TimePickerDialog(this, this, fHour, fMinute, true).show()

    }


    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int)
    {
        sSavedHour = hourOfDay
        sSavedMinute = minute

        textDate1.text = "Starting Date: \n$sSavedDay - $sSavedMonth - $sSavedYear \nHour: $sSavedHour Minute: $sSavedMinute"

        fSavedHour = hourOfDay
        fSavedMinute = minute

        textDate2.text = "Starting Date: \n$fSavedDay - $fSavedMonth - $fSavedYear \nHour: $fSavedHour Minute: $fSavedMinute"



    }




}



