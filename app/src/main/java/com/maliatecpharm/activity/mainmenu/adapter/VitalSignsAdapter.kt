package com.maliatecpharm.activity.mainmenu.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maliatecpharm.R
import com.maliatecpharm.activity.mainmenu.data.ProfileUiModel
import com.maliatecpharm.activity.mainmenu.data.VitalUiModel

class VitalSignsAdapter (var clickListener: OnVitalClickListener): RecyclerView.Adapter<VitalSignsAdapter.MyViewHolder>()
{
    private var vitalList = listOf<VitalUiModel>()
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val time: TextView = itemView.findViewById(R.id.timetxt)
        val cholesterol: TextView = itemView.findViewById(R.id.cholesterol)
        val fitness: TextView = itemView.findViewById(R.id.fitness)
        val glucose: TextView = itemView.findViewById(R.id.glucose)
        val bloodpressure: TextView = itemView.findViewById(R.id.bloodpressure)

        fun initialize(vitalList:VitalUiModel, action:OnVitalClickListener)
        {
            time.text = vitalList.time
            cholesterol.text = vitalList.cholesterol
            fitness.text = vitalList.fitness
            glucose.text = vitalList.glucose
            bloodpressure.text = vitalList.bloodpressure

            itemView.setOnClickListener {
                action.onItemClick(vitalList,adapterPosition)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder
    {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.vital_row, parent, false))
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        holder.initialize(vitalList.get(position), clickListener)
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
interface OnVitalClickListener
{
    fun onItemClick(vital: VitalUiModel, position: Int)
}