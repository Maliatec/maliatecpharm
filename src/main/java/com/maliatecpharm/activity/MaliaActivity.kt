package com.maliatecpharm.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maliatecpharm.R
import com.maliatecpharm.adapter.*
import com.maliatecpharm.uimodel.AlarmCount
import com.maliatecpharm.uimodel.InstructionsUIModel
import com.maliatecpharm.uimodel.MedicationTypeUIModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial
import java.util.*


class MaliaActivity : AppCompatActivity(),
    InstructionsAdapter.InstructionsInteractor,
    MedicationTypeAdapter.MedicationTypeInteractor,
    DayNameAdapter.DayNameInteractor
{


    private val medicationTypeAdapter by lazy {
        MedicationTypeAdapter(context = this, medicationTypeInteractor = this)
    }

    private val alarmCountAdapter by lazy {
        AlarmCountAdapter(context = this)
    }

    private val dayCountAdapter by lazy {
        DayCountAdapter(context = this)
    }

    private val instructionsAdapter by lazy {
        InstructionsAdapter(context = this, instructionsInteractor = this)
    }

    private val daynameAdapter by lazy {
        DayNameAdapter(context = this@MaliaActivity, dayNameInteractor = this@MaliaActivity)
    }


    private val instructionsList = listOf(
        InstructionsUIModel(id = 1, name = "No instructions", colorRes = R.color.teal_200),
        InstructionsUIModel(id = 2, name = "Before eating"),
        InstructionsUIModel(id = 3, name = "After eating"),
        InstructionsUIModel(id = 4, name = "While eating")
    )

    private val medicationTypeList = listOf(
        MedicationTypeUIModel(id = 1, name = "Liquid", R.drawable.image, R.color.teal_200),
        MedicationTypeUIModel(id = 2, name = "Pill", R.drawable.images),
        MedicationTypeUIModel(id = 3, name = "Syringe", R.drawable.syringue),
        MedicationTypeUIModel(id = 4, name = "Tablet", R.drawable.tablet),
        MedicationTypeUIModel(id = 5, name = "Other", R.drawable.cross),
    )

    private val timesList = listOf(
        AlarmCount(1, "Once a day", 1),
        AlarmCount(2, "2 times a day", 2),
        AlarmCount(3, "3 times a day", 3),
        AlarmCount(4, "4 times a day", 4),
        AlarmCount(5, "5 times a day", 5),
        AlarmCount(6, "6 times a day", 6),
        AlarmCount(7, "7 times a day", 7),
    )

    private val pillsList = arrayOf(
        "Pill(s)", "CC", "MI", "Gr", "Mg",
        "Drop(s)", "Piece(s)", "Puff(s)", "Unit(s)",
        "Teaspoon", "Patch", "Mcg", "lu", "Meq", "Cartoon", "Spray",
    )

    private val selectedDayIds: MutableList<String> = mutableListOf("Sunday")

    private val dayNameList = listOf<Day>(
        Day(id = 1, name = "Sunday", R.color.teal_200),
        Day(id = 2, name = "Monday"),
        Day(id = 3, name = "Tuesday"),
        Day(id = 4, name = "Wednesday"),
        Day(id = 5, name = "Thursday"),
        Day(id = 6, name = "Friday"),
        Day(id = 7, name = "Saturday")
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
    private lateinit var simpleCalendarView: CalendarView
    private lateinit var simple1CalendarView: CalendarView
    private lateinit var confirmButton: MaterialButton
    private lateinit var checkBox :CheckBox
    private lateinit var startTv: TextView
    private lateinit var endTv: TextView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_malia)
       


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
        populateDayCountRecycleView()



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
        simpleCalendarView = findViewById(R.id.simpleCalendarView)
        simple1CalendarView = findViewById(R.id.simple1CalendarView)
        dayCountRecyclerView = findViewById(R.id.Recylerview_day_count)
        checkBox = findViewById(R.id.repeat_Check_Box)
        startTv = findViewById(R.id.start_Text_View)
        endTv = findViewById(R.id.end_Text_View)

    }

    private fun setupTextViews()
    {
        dosage.text = getString(R.string.dosage)
        instructionsTv.text = getString(R.string.instructions)
        medicationTypeTv.text = getString(R.string.medication_type)
        medicationName.text = getString(R.string.medication_title)
        reminderSwitch.text = getString(R.string.switch_reminder_text)

                simpleCalendarView.focusedMonthDateColor (Color.RED)
                simpleCalendarView.unfocusedMonthDateColor(Color.BLUE)
                simpleCalendarView.selectedWeekBackgroundColor (Color.RED)
                simpleCalendarView.weekSeparatorLineColor(Color.GREEN)


        simple1CalendarView.focusedMonthDateColor (Color.RED)
        simple1CalendarView.unfocusedMonthDateColor(Color.BLUE)
        simple1CalendarView.selectedWeekBackgroundColor (Color.RED)
        simple1CalendarView.weekSeparatorLineColor(Color.GREEN)


    }

    private fun medSpinner()
    {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, pillsList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        pillsSpinner.adapter = adapter
    }


    private fun populateInstructionsRecycleView()
    {
        instructionsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MaliaActivity, RecyclerView.HORIZONTAL, false)
            adapter = instructionsAdapter
            instructionsAdapter.updateList(instructionsList)
        }
    }

    private fun populateTypeRecycleView()
    {
        medicationTypeRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MaliaActivity, RecyclerView.HORIZONTAL, false)
            adapter = medicationTypeAdapter
            medicationTypeAdapter.updateList(medicationTypeList)
        }
    }

    private fun populateAlarmCountRecycleView()
    {
        alarmRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MaliaActivity, RecyclerView.HORIZONTAL, false)
            adapter = alarmCountAdapter
            alarmCountAdapter.updateList(0)
        }
    }

    private fun populateDayCountRecycleView()
    {
        dayCountRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MaliaActivity, RecyclerView.HORIZONTAL, false)
            adapter = dayCountAdapter
            dayCountAdapter.updateList(0)
        }

    }


    private fun populateTimeSpinner()
    {
        val times = timesList.map { item -> item.text }
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
                val count = timesList.first { item -> item.text == timeClickedText }.count
                alarmCountAdapter.updateList(count)
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

            reminderTv.visibility = visibility
            timesSpinner.visibility = visibility
            atTv.visibility = visibility
            alarmRecyclerView.visibility = visibility
            onText.visibility = visibility
            dayRecyclerView.visibility = visibility
            dayCountRecyclerView.visibility = visibility
            checkBox.visibility = visibility
            startTv.visibility = visibility
            endTv.visibility = visibility


        }
    }

    private fun popRecyclerView()
    {
        dayRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MaliaActivity, RecyclerView.HORIZONTAL, false)
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
        if (selectedDayIds.contains(day.name))
        {
            selectedDayIds.remove(day.name)
        }
        else
        {
            selectedDayIds.add(day.name)
        }

        val updatedList = dayNameList.map { item ->
            item.copy(colorRes = getColor(selected = selectedDayIds.contains(item.name)))
        }

        daynameAdapter.updateList(updatedList)

        tvSelectedDays.text = selectedDayIds.toString()

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

    //


    private fun setOnButtonClicked()
    {
        confirmButton.setOnClickListener {
            startActivity(Intent(this, MedicationTypeAdapter::class.java))
        }

    }


}

private fun CalendarView.selectedWeekBackgroundColor(RED: Int)
{

}

private fun CalendarView.weekSeparatorLineColor(GREEN: Int)
{

}

private fun CalendarView.unfocusedMonthDateColor(BLUE: Int)
{

}

private fun CalendarView.focusedMonthDateColor(RED: Int)
{

}

