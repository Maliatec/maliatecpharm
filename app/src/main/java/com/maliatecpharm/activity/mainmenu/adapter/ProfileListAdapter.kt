package com.maliatecpharm.activity.mainmenu.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maliatecpharm.R
import com.maliatecpharm.activity.mainmenu.data.ProfileUiModel

class ProfileListAdapter : RecyclerView.Adapter<ProfileListAdapter.MyViewHolder>()
{
    private var userList = listOf<ProfileUiModel>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
         val firstName:TextView = itemView.findViewById(R.id.firstNametxt)
         val lastName:TextView = itemView.findViewById(R.id.lastNametxt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder
    {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val currentItem = userList[position]
        holder.firstName.text  = currentItem.firstName
        holder.lastName.text = currentItem.lastName
    }

    override fun getItemCount(): Int
    {
        return userList.size
    }

    fun updateList(profileList: List<ProfileUiModel>)
    {
        this.userList = profileList
        notifyDataSetChanged()
    }

    fun delete(adapterPosition: Int): Int
    {
        var id = 0
        val newList = userList.filterIndexed { index, item ->
            if (adapterPosition != index) true
            else {
                id = item.id
                false
            }
        }

        updateList(newList)
        return id
    }
}