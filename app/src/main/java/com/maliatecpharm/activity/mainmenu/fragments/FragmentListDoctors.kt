package com.maliatecpharm.activity.mainmenu.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

class FragmentListDoctors : Fragment()
{
    private lateinit var addButton: FloatingActionButton
    private lateinit var doctorsrv: RecyclerView

    val adapter by lazy {
        DoctorsAdapter()
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
        showDoctors()
        setUpSwipeToDelete()

        return view
    }
    //    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    //    {
    //        super.onViewCreated(view, savedInstanceState)
    //    }
    //    override fun onPause()
    //    {
    //        super.onPause()
    //    }
    //    override fun onResume()
    //    {
    //        super.onResume()
    //    }

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
        lifecycleScope.launch(Dispatchers.IO) {
            val drList: List<DoctorsEntity> = doctorDao.readAllDoctors()
            val drUpdatedList: List<DoctorsUiModel> = drList.map { doctorsEntity ->
                doctorsEntity.toUserUiModel()
            }

            withContext(Dispatchers.Main) {
                adapter.updateList(drUpdatedList)
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
                val drId = adapter.delete(viewHolder.adapterPosition)
                lifecycleScope.launch(Dispatchers.IO) {
                    doctorDao.deleteDoctorById(drId)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(item)
        itemTouchHelper.attachToRecyclerView(doctorsrv)
    }
}