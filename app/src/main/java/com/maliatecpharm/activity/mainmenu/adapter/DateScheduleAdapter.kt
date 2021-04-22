package com.maliatecpharm.activity.mainmenu.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maliatecpharm.R
import com.maliatecpharm.activity.mainmenu.data.CalendarUiModel

class DateScheduleAdapter(var clickListener: OnDateClickListener) : RecyclerView.Adapter<DateScheduleAdapter.MyViewHolder>()
{
    private var dateList = listOf<CalendarUiModel>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {

        val day: TextView = itemView.findViewById(R.id.dayTv)
        val startDate: TextView = itemView.findViewById(R.id.startTv)
        val endDate: TextView = itemView.findViewById(R.id.endTv)

        fun initialize(dateList: CalendarUiModel, action: OnDateClickListener)
        {
            day.text = dateList.day
            startDate.text = dateList.startdate
            endDate.text = dateList.enddate

            itemView.setOnClickListener {
                action.onItemClick(dateList, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder
    {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_schedule_date, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        //   val currentItem = dateList[position]
        //        holder.day.text = currentItem.day
        //        holder.startDate.text = currentItem.startdate
        //        holder.endDate.text = currentItem.enddate

        holder.initialize(dateList.get(position), clickListener)
    }

    override fun getItemCount(): Int
    {
        return dateList.size
    }

    fun updateList(schedulelist: List<CalendarUiModel>)
    {
        this.dateList = schedulelist
        notifyDataSetChanged()
    }

    fun delete(adapterPosition: Int): Int
    {
        var id = 0
        val newList = dateList.filterIndexed { index, item ->
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
    interface OnDateClickListener
    {
        fun onItemClick(calendar: CalendarUiModel, position: Int)

    }
