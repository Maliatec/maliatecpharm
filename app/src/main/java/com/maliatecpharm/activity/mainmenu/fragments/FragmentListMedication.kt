package com.maliatecpharm.activity.mainmenu.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.maliatecpharm.R
import com.maliatecpharm.activity.mainmenu.activities.ActivityAddMedications
import com.maliatecpharm.activity.mainmenu.activities.AdapterItemInteraction
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
        val view = inflater.inflate(R.layout.fragment_add_medication, container, false)

        addButton = view.findViewById(R.id.floatingBtn)
        medicinsrv = view.findViewById(R.id.recyclerView_Medicins)

        listRecyclerView()
        onBtnClicked()
        showMedicins()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
    }
    override fun onPause()
    {
        super.onPause()
    }

    override fun onResume()
    {
        super.onResume()
    }

    private fun onBtnClicked()
    {
        addButton.setOnClickListener {
            startActivity(Intent(requireContext(), ActivityAddMedications::class.java))

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
            val medList: List<MedicineEntity> = medicineDao.readAll()
            val medModels: List<MedicinesUiModel> = medList.map { medicineEntity ->
                medicineEntity.toUserUiModel()
            }

            withContext(Dispatchers.Main) {
                adapter.updateList(medModels)
            }
        }
    }



}


