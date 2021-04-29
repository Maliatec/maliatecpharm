package com.maliatecpharm.activity.mainmenu.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.maliatecpharm.R
import com.maliatecpharm.activity.mainmenu.adapter.*
import com.maliatecpharm.activity.mainmenu.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentListMedication : Fragment(), OnDateClickListener, OnMedClickListener
{
    val medadapter by lazy {
        MedicinesListAdapter(this)
    }

    val adapter by lazy {
        DateScheduleAdapter(this)
    }

    private val medicineDao: MedicineDao by lazy {
        AppDataBase.getDataBase(requireContext()).medicineDao()
    }
    private val calendarDao: CalendarDao by lazy {
        AppDataBase.getDataBase(requireContext()).calendarDao()
    }

    private lateinit var addButton: FloatingActionButton
    private lateinit var medicinsrv: RecyclerView
    private lateinit var datesrv: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View?
    {
        val view = inflater.inflate(R.layout.fragment_medication_list, container, false)

        addButton = view.findViewById(R.id.floatingBtn)
        medicinsrv = view.findViewById(R.id.recyclerView_Medicins)
        datesrv = view.findViewById(R.id.recyclerView_Date)

        medListRecyclerView()
        dateListRecyclerView()
        onBtnClicked()
        showMedicins()
        showDate()
        setUpSwipeToDelete()
        SwipeToDelete()
        return view
    }

    private fun onBtnClicked()
    {
        addButton.setOnClickListener {
            findNavController().navigate(R.id.action_MedicationsFragment_to_addMedicationFragment)
        }
    }
    private fun medListRecyclerView()
    {
        medicinsrv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        medicinsrv.adapter = medadapter
    }
    private fun dateListRecyclerView()
    {
        datesrv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        datesrv.adapter = adapter
    }
    private fun showMedicins()
    {
        lifecycleScope.launch(Dispatchers.IO) {
            val medList: List<MedicineEntity> = medicineDao.readAllMedicins()
            val medModels: List<MedicinesUiModel> = medList.map { medicineEntity ->
                medicineEntity.toUserUiModel()
            }
            withContext(Dispatchers.Main) {
                medadapter.updateList(medModels)
            }
        }
    }
    private fun showDate()
    {
        lifecycleScope.launch(Dispatchers.IO) {
            val dateList: List<CalendarEntity> = calendarDao.readAllDates()
            val dateModels: List<CalendarUiModel> = dateList.map { calendarEntity ->
                calendarEntity.toUserUiModel()
            }
            withContext(Dispatchers.Main) {
                adapter.updateList(dateModels)
            }
        }
    }
    private fun setUpSwipeToDelete()
    {
        val item = object : AdapterItemInteraction(
            context = requireContext(),
            dragDir = 0,
            swipeDir = ItemTouchHelper.LEFT
        )
        {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int)
            {
                val medId = medadapter.delete(viewHolder.adapterPosition)
                lifecycleScope.launch(Dispatchers.IO) {
                    medicineDao.deleteMedById(medId)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(item)
        itemTouchHelper.attachToRecyclerView(medicinsrv)
    }

    private fun SwipeToDelete()
    {
        val item = object : AdapterItemInteraction(
            context = requireContext(),
            dragDir = 0,
            swipeDir = ItemTouchHelper.LEFT
        )
        {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int)
            {
                val dateId = adapter.delete(viewHolder.adapterPosition)
                lifecycleScope.launch(Dispatchers.IO) {
                    calendarDao.deleteDateById(dateId)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(item)
        itemTouchHelper.attachToRecyclerView(datesrv)
        }

    override fun onItemClick(calendar: CalendarUiModel, position: Int)
    {
        val bundle = bundleOf("dateId" to calendar.id)
        findNavController().navigate(R.id.action_MedicationsFragment_to_fragmentAddMedicine,bundle)
    }

    override fun onItemClicked(medicin: MedicinesUiModel, position: Int)
    {
        val bundle = bundleOf("medId" to medicin.id)
        findNavController().navigate(R.id.action_MedicationsFragment_to_addMedicationFragment,bundle)
    }
}




