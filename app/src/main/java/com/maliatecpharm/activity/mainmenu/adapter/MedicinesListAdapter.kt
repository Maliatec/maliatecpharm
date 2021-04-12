package com.maliatecpharm.activity.mainmenu.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maliatecpharm.R
import com.maliatecpharm.activity.mainmenu.data.MedicinesUiModel

class MedicinesListAdapter: RecyclerView.Adapter<MedicinesListAdapter.MyViewHolder>()
{
    private var medicineList = listOf<MedicinesUiModel>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val medName: TextView = itemView.findViewById(R.id.nameTv)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder
    {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_medicine, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val currentItem = medicineList[position]
        holder.medName.text = currentItem.Name

    }

    override fun getItemCount(): Int
    {
        return medicineList.size
    }

    fun updateList(medList: List<MedicinesUiModel>)
    {
        this.medicineList = medList
        notifyDataSetChanged()
    }

    fun delete(adapterPosition: Int): Int
    {
        var id = 0
        val newList = medicineList.filterIndexed { index, item ->
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