package com.maliatecpharm.activity.mainmenu.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.maliatecpharm.R
import com.maliatecpharm.activity.mainmenu.adapter.AdapterItemInteraction
import com.maliatecpharm.activity.mainmenu.adapter.DoctorsAdapter
import com.maliatecpharm.activity.mainmenu.data.*
import com.maliatecpharm.activity.mainmenu.uimodel.DoctorsUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentListDoctors : Fragment(), DoctorsAdapter.OnDoctorClickListener
{
    private lateinit var addButton: FloatingActionButton
    private lateinit var doctorsrv: RecyclerView

    val adapter by lazy {
        DoctorsAdapter(this)
    }

    private val doctorDao: DoctorsDao by lazy {
        AppDataBase.getDataBase(requireContext()).doctorDao()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View?
    {
        val view = inflater.inflate(R.layout.fragment_listof_doctors, container, false)
        addButton = view.findViewById(R.id.floatingButton)
        doctorsrv = view.findViewById(R.id.recyclerView_Doctors)

        onBtnClicked()
        doctorsListRecyclerView()
        setUpSwipeToDelete()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        showDoctors()
    }
    private fun onBtnClicked()
    {
        addButton.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentListDoctors_to_fragmentAddDoctors)
        }
    }
    private fun doctorsListRecyclerView()
    {
        doctorsrv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        doctorsrv.adapter = adapter
    }

    private fun showDoctors()
    {
        val doctorsLiveData: LiveData<List<DoctorsEntity>> = doctorDao.readAllDoctors()
        doctorsLiveData.observe(viewLifecycleOwner, {
            val drUpdatedList: List<DoctorsUiModel> = it.map { doctorsEntity ->
                doctorsEntity.toUserUiModel()
            }
            adapter.updateList(drUpdatedList)
        })
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
                val drId = adapter.delete(viewHolder.adapterPosition)
                lifecycleScope.launch(Dispatchers.IO) {
                    doctorDao.deleteDoctorById(drId)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(item)
        itemTouchHelper.attachToRecyclerView(doctorsrv)
    }
    override fun onItemClick(doctor: DoctorsUiModel, position: Int)
    {
        val bundle = bundleOf("doctorId" to doctor.id)
        findNavController().navigate(R.id.action_fragmentListDoctors_to_fragmentAddDoctors,bundle)
    }
}