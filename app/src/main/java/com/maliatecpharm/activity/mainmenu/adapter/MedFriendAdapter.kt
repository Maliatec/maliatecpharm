package com.maliatecpharm.activity.mainmenu.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maliatecpharm.R
import com.maliatecpharm.activity.mainmenu.data.FriendUiModel

class MedFriendAdapter: RecyclerView.Adapter<MedFriendAdapter.MyViewHolder>()
{
    private var friendList = listOf<FriendUiModel>()
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val firstName: TextView = itemView.findViewById(R.id.firstNametxt)
        val lastName: TextView = itemView.findViewById(R.id.lastNametxt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder
    {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.friend_row, parent, false))
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val currentItem = friendList[position]
        holder.firstName.text = currentItem.firstName
        holder.lastName.text = currentItem.lastName
    }
    override fun getItemCount(): Int
    {
        return friendList.size
    }
    fun updateList(friendList: List<FriendUiModel>)
    {
        this.friendList = friendList
        notifyDataSetChanged()
    }
    fun delete(adapterPosition: Int): Int
    {
        var id = 0
        val newList = friendList.filterIndexed { index, item ->
            if (adapterPosition != index) true
            else
            {
                id = item.id
                false
            }
        }
        updateList(newList)
        return id
    }
}