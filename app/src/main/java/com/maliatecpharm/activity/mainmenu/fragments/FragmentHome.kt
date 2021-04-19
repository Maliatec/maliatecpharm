package com.maliatecpharm.activity.mainmenu.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.maliatecpharm.R
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.HorizontalCalendarListener
import devs.mulham.horizontalcalendar.HorizontalCalendarView
import java.util.*


class FragmentHome : Fragment()
{
    private lateinit var emptyBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View?
    {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
//        emptyBtn = view.findViewById(R.id.button_emptyBtn)

        val endDate = Calendar.getInstance()
        endDate.add(Calendar.MONTH, 1)

        val startDate = Calendar.getInstance()
        startDate.add(Calendar.MONTH, -1)

        val horizontalCalendar: HorizontalCalendar = HorizontalCalendar.Builder(view, R.id.calendarView)
            .startDate(startDate.time)
            .endDate(endDate.time)
            .build()

        horizontalCalendar.calendarListener = object : HorizontalCalendarListener()
        {
            override fun onDateSelected(date: Date?, position: Int)
            {
                //do something
            }
        }

        horizontalCalendar.calendarListener = object : HorizontalCalendarListener()
        {
            override fun onDateSelected(date: Date?, position: Int)
            {
            }
            override fun onCalendarScroll(
                calendarView: HorizontalCalendarView,
                dx: Int, dy: Int
            )
            {
            }
            override fun onDateLongClicked(date: Date?, position: Int): Boolean
            {
                return true
            }
        }


        //   setOnBtnClicked()

//        view.setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_HomeFragment_to_MedicationsFragment) }
        return view
    }

//    private fun setOnBtnClicked()
//    {
//        emptyBtn.setOnClickListener {
//           // startActivity(Intent(requireContext(), AddMedications::class.java))
//        }
//    }


}
