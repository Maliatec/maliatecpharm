package com.maliatecpharm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.maliatecpharm.R


class DayNameAdapter(

    private val context: Context,
    private val dayNameInteractor: DayNameInteractor
) :
    RecyclerView.Adapter<DayNameAdapter.DAYNAMEViewHolder>()
{

    private var dayNameList: List<Day> = emptyList()


    fun updateList(dayNameList: List<Day>)
    {
        this.dayNameList = dayNameList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DayNameAdapter.DAYNAMEViewHolder
    {
        val type: View =
            LayoutInflater.from(context).inflate(R.layout.day_name_viewholder, null, false)

        return DAYNAMEViewHolder(type)
    }

    override fun onBindViewHolder(holder: DayNameAdapter.DAYNAMEViewHolder, position: Int)
    {
        val dayName = dayNameList[position]
        holder.bindData(dayName)
    }

    override fun getItemCount() = dayNameList.size


    inner class DAYNAMEViewHolder constructor(private val view: View) :
        RecyclerView.ViewHolder(view)
    {

        private val nameTv: TextView = view.findViewById(R.id.textview_dayname)

        fun bindData(dayname: Day)
        {

            nameTv.text = dayname.name

            val color = ContextCompat.getColor(context, dayname.colorRes)
            nameTv.setBackgroundColor(color)


            view.setOnClickListener {
                dayNameInteractor.onDayClicked(dayname)

            }
        }


        }
    interface DayNameInteractor
    {
        fun onDayClicked(day: Day)
    }
}

data class Day(
    val id: Int,
    val name: String,


    @ColorRes val colorRes: Int = R.color.white
)







