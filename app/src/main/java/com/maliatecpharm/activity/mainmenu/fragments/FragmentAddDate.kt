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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maliatecpharm.R
import com.maliatecpharm.activity.mainmenu.adapter.AlarmCountAdapter
import com.maliatecpharm.activity.mainmenu.adapter.Day
import com.maliatecpharm.activity.mainmenu.adapter.DayNameAdapter
import com.maliatecpharm.activity.mainmenu.data.*
import com.maliatecpharm.activity.mainmenu.uimodel.AlarmCount
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class FragmentAddDate : Fragment(),
    DayNameAdapter.DayNameInteractor,
    AlarmCountAdapter.AlarmCountInteractor,
    DatePickerDialog.OnDateSetListener
{
    private val selectedDayIds: MutableList<Int> = mutableListOf(1)

    private val calendarDao: CalendarDao by lazy {
        AppDataBase.getDataBase(requireContext()).calendarDao()
    }

    private var calendarEntity = CalendarEntity("", "", "")

    private val dayNameList = listOf<Day>(
        Day(id = 1, name = "Sunday"),
        Day(id = 2, name = "Monday"),
        Day(id = 3, name = "Tuesday"),
        Day(id = 4, name = "Wednesday"),
        Day(id = 5, name = "Thursday"),
        Day(id = 6, name = "Friday"),
        Day(id = 7, name = "Saturday")
    )
    private val daynameAdapter by lazy {
        DayNameAdapter(context = requireContext(), dayNameInteractor = this)
    }

    private val alarmCountAdapter by lazy {
        AlarmCountAdapter(context = requireContext(), interactor = this)
    }

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

    private lateinit var tvSelectedDays: TextView
    private lateinit var dayRecyclerView: RecyclerView
    private lateinit var dayCountRecyclerView: RecyclerView
    private lateinit var confirmButton: Button
    private lateinit var checkBox: CheckBox
    private lateinit var btn1Date: Button
    private lateinit var textDate1: TextView
    private lateinit var btn2Date: Button
    private lateinit var textDate2: TextView
    private lateinit var timesSpinner: Spinner
    private lateinit var alarmRecyclerView: RecyclerView

    var sDay = 0
    var sMonth = 0
    var sYear = 0
    var fDay = 0
    var fMonth = 0
    var fYear = 0
    var sSavedDay = 0
    var sSavedMonth = 0
    var sSavedYear = 0
    var fSavedDay = 0
    var fSavedMonth = 0
    var fSavedYear = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        val view = inflater.inflate(R.layout.fragment_add_date, container, false)

        tvSelectedDays = view.findViewById(R.id.textview_selectedDays)
        timesSpinner = view.findViewById(R.id.spinner_timeSpinner)
        confirmButton = view.findViewById(R.id.button_confirmbtn)
        alarmRecyclerView = view.findViewById(R.id.recyclerview_alarmCount)
        dayRecyclerView = view.findViewById(R.id.recylerview_day)
        dayCountRecyclerView = view.findViewById(R.id.recylerview_dayCount)
        checkBox = view.findViewById(R.id.checkbox_repeat)
        btn1Date = view.findViewById(R.id.button_timePickerBtn1)
        textDate1 = view.findViewById(R.id.textview_textTime1)
        btn2Date = view.findViewById(R.id.button_timePickerbtn2)
        textDate2 = view.findViewById(R.id.textview_textTime2)

      //  onAddClickListener()
        populateAlarmCountRecycleView()
        populateTimeSpinner()
        onSaveClickListener()
        popRecyclerView()
        pickSDate()
        pickFDate()
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        val dateId = arguments?.getInt("dateId") ?: -1
        calendarDao.getDateLiveData(dateId).observe(viewLifecycleOwner) {

            if (it != null)
            {
                calendarEntity = it
                tvSelectedDays.setText(it.day)
                textDate1.setText(it.startdate)
                textDate2.setText(it.enddate)
            }
        }
    }
//    private fun onAddClickListener()
//    {
//        addMedication.setOnClickListener {
//            findNavController().navigate(R.id.action_fragmentAddMedicine_to_addMedicationFragment)
//        }
//    }

    private fun populateAlarmCountRecycleView()
    {
        alarmRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = alarmCountAdapter
        }
    }
    private fun populateTimeSpinner()
    {
        val times = alarmsCountList.map { item -> item.text }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, times)
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

    private fun onSaveClickListener()
    {
        confirmButton.setOnClickListener {
            insertDataToDataBase()
            findNavController().navigate(R.id.action_fragmentAddMedicine_to_MedicationsFragment)
        }
    }
    private fun insertDataToDataBase()
    {
        val day = tvSelectedDays.text.toString()
        val start = textDate1.text.toString()
        val end = textDate2.text.toString()

            if (inputCheck(day, start, end))
        {
            val date = CalendarEntity( day, start, end)
            lifecycleScope.launch(Dispatchers.IO) {
               calendarDao.addDate(date)
            }
            Toast.makeText(requireContext(), "Date Added ", Toast.LENGTH_SHORT).show()
            //findNavController().popBackStack()
            //           findNavController().navigate(R.id.action_MedicationsFragment_to_blankFragment)
        }
        else
            Toast.makeText(requireContext(), "Please fill date ", Toast.LENGTH_SHORT).show()
    }
    private fun inputCheck( day: String, start: String, end: String): Boolean
    {
        return !(TextUtils.isEmpty(day) && TextUtils.isEmpty(start) && TextUtils.isEmpty(end))
    }

    private fun popRecyclerView()
    {
        dayRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = daynameAdapter
            daynameAdapter.updateList(dayNameList)
        }
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
            requireContext(),
            { _, hour, minute -> updateList(hour, minute) },
            0,
            0, true
        )
            .show()
    }
    private fun getSDateCalendar()
    {
        val cal = Calendar.getInstance()
        sDay = cal.get(Calendar.DAY_OF_MONTH)
        sMonth = cal.get(Calendar.MONTH)
        sYear = cal.get(Calendar.YEAR)
    }
    private fun getFDateCalendar()
    {
        val cal = Calendar.getInstance()
        fDay = cal.get(Calendar.DAY_OF_MONTH)
        fMonth = cal.get(Calendar.MONTH)
        fYear = cal.get(Calendar.YEAR)
    }
    private fun pickSDate()
    {
        btn1Date.setOnClickListener {
            getSDateCalendar()
            DatePickerDialog(requireContext(), fromListener, sYear, sMonth, sDay).show()
        }
    }
    private fun pickFDate()
    {
        btn2Date.setOnClickListener {
            getSDateCalendar()
            DatePickerDialog(requireContext(), toListener, sYear, sMonth, sDay).show()
        }
    }
    private val fromListener = DatePickerDialog.OnDateSetListener { datePicker: DatePicker, year: Int, month: Int, dayOfMonth: Int ->

        sSavedDay = dayOfMonth
        sSavedMonth = month
        sSavedYear = year
        getSDateCalendar()
        textDate1.text = "From $sSavedDay - $sSavedMonth - $sSavedYear"
    }
    private val toListener = DatePickerDialog.OnDateSetListener { datePicker: DatePicker, year: Int, month: Int, dayOfMonth: Int ->

        fSavedDay = dayOfMonth
        fSavedMonth = month
        fSavedYear = year
        getFDateCalendar()
        textDate2.text = "To $fSavedDay - $fSavedMonth - $fSavedYear"
    }
    override fun onDateSet(datePicker: DatePicker?, year: Int, month: Int, dayOfMonth: Int)
    {
        sSavedDay = dayOfMonth
        sSavedMonth = month
        sSavedYear = year
        textDate2.text = "$fSavedDay - $fSavedMonth - $fSavedYear"
    }
}