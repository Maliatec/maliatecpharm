//package com.maliatecpharm.adapter
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.maliatecpharm.R
//import com.maliatecpharm.activity.MaliaActivity
//
//class CalendarAdapter(
//    private val context: Context,
//    private val calendarInteractor: MaliaActivity
//) :
//
//    RecyclerView.Adapter<CalendarAdapter.CalendarTypeViewHolder>()
//{
//
//    private var calendarTimeList: List<CalendarTime> = emptyList()
//
//
//    fun updateList(calendarTimeList: List<CalendarTime>)
//    {
//        this.calendarTimeList = calendarTimeList
//        notifyDataSetChanged()
//    }
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarTypeViewHolder
//    {
//        val time: View =
//            LayoutInflater.from(context).inflate(R.layout.calendar_view_holder, null, false)
//
//        return CalendarTypeViewHolder(time)
//    }
//
//    override fun onBindViewHolder(holder: CalendarTypeViewHolder, position: Int)
//    {
//        val timeSelected = calendarTimeList[position]
//        holder.bindData(timeSelected)
//    }
//
//    override fun getItemCount() =calendarTimeList.size
//
//
//
//    inner class CalendarTypeViewHolder(time: View):
//        RecyclerView.ViewHolder(time)
//    {
//        private val timeTv: TextView = time.findViewById(R.id.textview_Time)
//
//
//        fun bindData(timeType: CalendarTime) {
//
//            timeTv.text = timeType.month
//
//            timeTv.setOnClickListener {
//               calendarInteractor.ontimeClicked(timeType)
//            }
//        }
//
//    }
//
//
//    interface CalendarInteractor
//    {
//        fun ontimeClicked(timeType:CalendarTime)
//    }
//
//
//}
//
//class CalendarTime(
//    val day: String,
//    val month: String,
//    val year: Int
//)