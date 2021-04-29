package com.maliatecpharm.activity.mainmenu.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.maliatecpharm.R
import com.maliatecpharm.activity.mainmenu.adapter.AdapterItemInteraction
import com.maliatecpharm.activity.mainmenu.adapter.OnProfileClickListener
import com.maliatecpharm.activity.mainmenu.adapter.ProfileListAdapter
import com.maliatecpharm.activity.mainmenu.data.AppDataBase
import com.maliatecpharm.activity.mainmenu.data.UserDao
import com.maliatecpharm.activity.mainmenu.data.ProfileEntity
import com.maliatecpharm.activity.mainmenu.data.ProfileUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentListProfile : Fragment(), OnProfileClickListener
{
    val adapter by lazy {
        ProfileListAdapter(this)
    }
    private val userDao: UserDao by lazy {
        AppDataBase.getDataBase(requireContext()).userDao()
    }
    private lateinit var actionBtn: FloatingActionButton
    private lateinit var profileRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.fragment_list_profile, container, false)
        actionBtn = view.findViewById(R.id.fab)
        profileRecyclerView = view.findViewById(R.id.recyclerView_Profile)

        onBtnClicked()
        setUpSwipeToDelete()
        listRecyclerView()
        showProfiles()
        return view
    }
    private fun onBtnClicked()
    {
        actionBtn.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentListProfile_to_addProfileFragment)
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
                val profileId = adapter.delete(viewHolder.adapterPosition)
                lifecycleScope.launch(Dispatchers.IO) {
                    userDao.deleteProfileById(profileId)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(item)
        itemTouchHelper.attachToRecyclerView(profileRecyclerView)
    }

    private fun listRecyclerView()
    {
        profileRecyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        profileRecyclerView.adapter = adapter
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
    override fun onItemClick(profile: ProfileUiModel, position: Int)
    {
        val bundle = bundleOf("profileId" to profile.id)
        findNavController().navigate(R.id.action_fragmentListProfile_to_addProfileFragment,bundle)
    }
}
