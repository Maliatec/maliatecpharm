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
import com.maliatecpharm.activity.mainmenu.adapter.AdapterItemInteraction
import com.maliatecpharm.activity.mainmenu.adapter.OnVitalClickListener
import com.maliatecpharm.activity.mainmenu.adapter.VitalSignsAdapter
import com.maliatecpharm.activity.mainmenu.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentListVitalSigns : Fragment(), OnVitalClickListener
{
    val adapter by lazy {
        VitalSignsAdapter(this)
    }
    private val vitalDao: VitalSignsDao by lazy {
        AppDataBase.getDataBase(requireContext()).vitalDao()
    }
    private lateinit var addButton: FloatingActionButton
    private lateinit var vitalRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        val view = inflater.inflate(R.layout.fragment_vitalsigns, container, false)
        addButton = view.findViewById(R.id.button_addButton)
        vitalRecyclerView = view.findViewById(R.id.recyclerView_Vitalsigns)
        setOnButtonClicked()
        setUpSwipeToDelete()
        vitalRecyclerView()
        showVitals()
        return view
    }
    private fun setOnButtonClicked()
    {
        addButton.setOnClickListener {
            findNavController().navigate(R.id.action_vitalSignsFragment_to_addVitalSignsFragment)
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
                val vitalId = adapter.delete(viewHolder.adapterPosition)
                lifecycleScope.launch(Dispatchers.IO) {
                    vitalDao.deleteVitalById(vitalId)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(item)
        itemTouchHelper.attachToRecyclerView(vitalRecyclerView)
    }
    private fun vitalRecyclerView()
    {
        vitalRecyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        vitalRecyclerView.adapter = adapter
    }

    private fun showVitals()
    {
        lifecycleScope.launch(Dispatchers.IO) {
            val vitalList: List<VitalEntity> = vitalDao.readAllData()
            val vitalModels: List<VitalUiModel> = vitalList.map { vitalEntity ->
                vitalEntity.toUserUiModel()
            }
            withContext(Dispatchers.Main) {
                adapter.updateList(vitalModels)
            }
        }
    }
    override fun onItemClick(vital: VitalUiModel, position: Int)
    {
        val bundle = bundleOf("vitalId" to vital.id)
        findNavController().navigate(R.id.action_vitalSignsFragment_to_addVitalSignsFragment,bundle)
    }
}
