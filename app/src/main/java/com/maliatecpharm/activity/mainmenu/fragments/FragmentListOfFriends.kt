package com.maliatecpharm.activity.mainmenu.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.maliatecpharm.R
import com.maliatecpharm.activity.mainmenu.adapter.AdapterItemInteraction
import com.maliatecpharm.activity.mainmenu.adapter.MedFriendAdapter
import com.maliatecpharm.activity.mainmenu.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentListOfFriends : Fragment()
{
    val adapter by lazy {
        MedFriendAdapter()
    }

    private val friendDao: FriendDao by lazy {
        AppDataBase.getDataBase(requireContext()).friendDao()
    }

    private lateinit var actionBtn: FloatingActionButton
    private lateinit var friendRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        val view = inflater.inflate(R.layout.fragment_list_friends, container, false)

        actionBtn = view.findViewById(R.id.fab)
        friendRecyclerView = view.findViewById(R.id.recyclerView_medfriend)

        setUpSwipeToDelete()
        onBtnClicked()
        friendlistRecyclerView()
        showFriends()

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
                val friendId = adapter.delete(viewHolder.adapterPosition)
                lifecycleScope.launch(Dispatchers.IO) {
                    friendDao.deleteFriendById(friendId)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(item)
        itemTouchHelper.attachToRecyclerView(friendRecyclerView)
    }

    private fun onBtnClicked()
    {
        actionBtn.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentlistfriend_to_fragmentMedFriend)
        }
    }

    private fun friendlistRecyclerView()
    {
        friendRecyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        friendRecyclerView.adapter = adapter
    }

    private fun showFriends()
    {
        lifecycleScope.launch(Dispatchers.IO) {
            val friendsList: List<FriendEntity> = friendDao.readAllData()
            val profileModels: List<FriendUiModel> = friendsList.map { friendEntity ->
                friendEntity.toUserUiModel()
            }
            withContext(Dispatchers.Main) {
                adapter.updateList(profileModels)
            }
        }
    }
}