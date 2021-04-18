package com.maliatecpharm.activity.mainmenu.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.maliatecpharm.R
import com.maliatecpharm.activity.mainmenu.adapter.AdapterItemInteraction
import com.maliatecpharm.activity.mainmenu.activities.MainActivityViewModel
import com.maliatecpharm.activity.mainmenu.adapter.MedicinesListAdapter
import com.maliatecpharm.activity.mainmenu.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentListMedication : Fragment()
{
    val adapter by lazy {
        MedicinesListAdapter()
    }

    private val medicineDao: MedicineDao by lazy {
        AppDataBase.getDataBase(requireContext()).medicineDao()
    }

    private lateinit var addButton: FloatingActionButton
    private lateinit var medicinsrv: RecyclerView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View?
    {
        val view = inflater.inflate(R.layout.fragment_medication_list, container, false)

        addButton = view.findViewById(R.id.floatingBtn)
        medicinsrv = view.findViewById(R.id.recyclerView_Medicins)


        listRecyclerView()
        onBtnClicked()
        showMedicins()
        setUpSwipeToDelete()

        return view
    }

    private fun onBtnClicked()
    {
        addButton.setOnClickListener {
            findNavController().navigate(R.id.action_MedicationsFragment_to_addMedicationFragment)
        }
    }

    private fun listRecyclerView()
    {
        medicinsrv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        medicinsrv.adapter = adapter
    }

    private fun showMedicins()
    {
        lifecycleScope.launch(Dispatchers.IO) {
            val medList: List<MedicineEntity> = medicineDao.readAllMedicins()
            val medModels: List<MedicinesUiModel> = medList.map { medicineEntity ->
                medicineEntity.toUserUiModel()
            }

            withContext(Dispatchers.Main) {
                adapter.updateList(medModels)
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
                val medId = adapter.delete(viewHolder.adapterPosition)
                lifecycleScope.launch(Dispatchers.IO) {
                    medicineDao.deleteMedById(medId)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(item)
        itemTouchHelper.attachToRecyclerView(medicinsrv)
    }

}




