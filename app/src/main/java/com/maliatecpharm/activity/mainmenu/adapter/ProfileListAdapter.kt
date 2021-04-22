package com.maliatecpharm.activity.mainmenu.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maliatecpharm.R
import com.maliatecpharm.activity.mainmenu.data.ProfileUiModel

class ProfileListAdapter( var clickListener: OnProfileClickListener) : RecyclerView.Adapter<ProfileListAdapter.MyViewHolder>()
{
    private var profileList = listOf<ProfileUiModel>()

    class MyViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView)
    {
         val firstName:TextView = itemView.findViewById(R.id.firstNametxt)
         val lastName:TextView = itemView.findViewById(R.id.lastNametxt)

        fun initialize(profileList:ProfileUiModel, action:OnProfileClickListener)
        {
            firstName.text = profileList.firstName
            lastName.text = profileList.lastName

            itemView.setOnClickListener {
                action.onItemClick(profileList,adapterPosition)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder
    {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.profile_row, parent, false))
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
//        val currentItem = userList[position]
//        holder.firstName.text  = currentItem.firstName
//        holder.lastName.text = currentItem.lastName

        holder.initialize(profileList.get(position), clickListener)
    }

    override fun getItemCount(): Int
    {
        return profileList.size
    }

    fun updateList(profileList: List<ProfileUiModel>)
    {
        this.profileList = profileList
        notifyDataSetChanged()
    }

    fun delete(adapterPosition: Int): Int
    {
        var id = 0
        val newList = profileList.filterIndexed { index, item ->
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

interface OnProfileClickListener
{
fun onItemClick(profile : ProfileUiModel, position: Int)

}