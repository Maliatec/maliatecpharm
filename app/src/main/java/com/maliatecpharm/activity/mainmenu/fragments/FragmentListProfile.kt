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
import com.maliatecpharm.activity.mainmenu.activities.AdapterItemInteraction
import com.maliatecpharm.activity.mainmenu.adapter.ListAdapter
import com.maliatecpharm.activity.mainmenu.data.AppDataBase
import com.maliatecpharm.activity.mainmenu.data.UserDao
import com.maliatecpharm.activity.mainmenu.data.ProfileEntity
import com.maliatecpharm.activity.mainmenu.data.ProfileUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentListProfile : Fragment()
{
    val adapter by lazy {
        ListAdapter()
    }

    private val userDao: UserDao by lazy {
        AppDataBase.getDataBase(requireContext()).userDao()
    }

    private lateinit var fab: FloatingActionButton
    private lateinit var rv: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.fragment_list_profile, container, false)

        fab = view.findViewById(R.id.fab)
        rv = view.findViewById(R.id.recyclerView_Profile)

        setUpSwipeToDelete()
        onBtnClicked()
        listRecyclerView()
        showProfiles()
        return view
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
                val profileId = adapter.delete(viewHolder.adapterPosition)
                lifecycleScope.launch(Dispatchers.IO) {
                    userDao.deleteProfileById(profileId)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(item)
        itemTouchHelper.attachToRecyclerView(rv)
    }

    private fun onBtnClicked()
    {
        fab.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentListProfile_to_addFragment)
        }
    }

    private fun listRecyclerView()
    {
        rv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        rv.adapter = adapter
    }

    private fun showProfiles()
    {
        lifecycleScope.launch(Dispatchers.IO) {
            val profilesList: List<ProfileEntity> = userDao.readAllData()
            val profileModels: List<ProfileUiModel> = profilesList.map { userEntity ->
                userEntity.toUserUiModel()
            }

            withContext(Dispatchers.Main) {
                adapter.updateList(profileModels)
            }
        }
    }
}




