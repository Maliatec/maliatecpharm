package com.maliatecpharm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maliatecpharm.R

class AlarmCountAdapter(
    private val context: Context,
    private val interactor: AlarmCountInteractor
) :
    RecyclerView.Adapter<AlarmCountAdapter.AlarmCountViewHolder>() {

    private var alarmCountList: List<String> = emptyList()

    fun updateList(alarmCountList: List<String>) {
        this.alarmCountList = alarmCountList
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
        val time = alarmCountList[position]
        holder.bindData(time, position)
    }

    override fun getItemCount() = alarmCountList.size

    inner class AlarmCountViewHolder constructor(private val view: View) :
        RecyclerView.ViewHolder(view) {

        private val setAnAlarm: EditText = view.findViewById(R.id.textview_time)

        fun bindData(time: String, position: Int) {
            setAnAlarm.setText(time)
            setAnAlarm.setOnClickListener{
                interactor.onAlarmTimeClicked(position)
            }
        }
    }

    interface AlarmCountInteractor{
        fun onAlarmTimeClicked(position: Int)
    }
}