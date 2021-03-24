package com.maliatecpharm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.maliatecpharm.R

class DayCountAdapter (private val context: Context,


):
    RecyclerView.Adapter<DayCountAdapter.DayCountViewHolder>() {

    private var dayCountList: List<Unit> = emptyList()

    fun updateList(count: Int = 0) {
        dayCountList = if (count == 0) emptyList() else (0..count-1).map {}
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayCountViewHolder {
        val rootView: View =
            LayoutInflater.from(context).inflate(R.layout.day_count, null, false)

        return DayCountViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: DayCountViewHolder, position: Int) {
        holder.bindData()
    }

    override fun getItemCount() = dayCountList.size


   class DayCountViewHolder constructor(private val view: View) :
       RecyclerView.ViewHolder(view) {

       private val setADay : EditText = view. findViewById(R.id.edit_text_day)

       fun bindData() {
           val text = setADay.text

       }
    }



}




