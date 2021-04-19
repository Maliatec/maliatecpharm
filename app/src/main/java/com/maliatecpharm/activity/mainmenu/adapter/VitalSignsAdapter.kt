package com.maliatecpharm.activity.mainmenu.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maliatecpharm.R
import com.maliatecpharm.activity.mainmenu.data.VitalUiModel

class VitalSignsAdapter: RecyclerView.Adapter<VitalSignsAdapter.MyViewHolder>()
{
    private var vitalList = listOf<VitalUiModel>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val firstName: TextView = itemView.findViewById(R.id.firstNametxt)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder
    {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.vital_row, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val currentItem = vitalList[position]
        holder.firstName.text = currentItem.firstName

    }
    override fun getItemCount(): Int
    {
        return vitalList.size
    }

    fun updateList(vitalList: List<VitalUiModel>)
    {
        this.vitalList = vitalList
        notifyDataSetChanged()
    }

    fun delete(adapterPosition: Int): Int
    {
        var id = 0
        val newList = vitalList.filterIndexed { index, item ->
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