package com.maliatecpharm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.maliatecpharm.R

class AlarmCountAdapter(
    private val context: Context
) :
    RecyclerView.Adapter<AlarmCountAdapter.AlarmCountViewHolder>() {

    private var alarmCountList: List<Unit> = emptyList()

    fun updateList(count: Int = 0) {
        alarmCountList = if (count == 0) emptyList() else (0..count-1).map {}
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlarmCountViewHolder {
        val rootView: View =
            LayoutInflater.from(context).inflate(R.layout.alarm_count, null, false)

        return AlarmCountViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: AlarmCountViewHolder, position: Int) {
        holder.bindData()
    }

    override fun getItemCount() = alarmCountList.size

    inner class AlarmCountViewHolder constructor(private val view: View) :
        RecyclerView.ViewHolder(view) {

        private val setAnAlarm: EditText = view.findViewById(R.id.edittext_alarm)

        fun bindData() {
            val text = setAnAlarm.text
        }
    }
}