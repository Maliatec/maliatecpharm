package com.maliatecpharm.activity.mainmenu.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maliatecpharm.R
import com.maliatecpharm.activity.mainmenu.data.ProfileUiModel
import com.maliatecpharm.activity.mainmenu.uimodel.DoctorsUiModel
import org.w3c.dom.Text

class DoctorsAdapter(var clickListener: OnDoctorClickListener) : RecyclerView.Adapter<DoctorsAdapter.MyViewHolder>()
{
    private var doctorsList = listOf<DoctorsUiModel>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val drName: TextView = itemView.findViewById(R.id.drNametv)
        val spec: TextView = itemView.findViewById(R.id.spectv)
        val nbr: TextView = itemView.findViewById(R.id.nbrtv)
        val app: TextView = itemView.findViewById(R.id.apptv)

        fun initialize(doctorList: DoctorsUiModel, action: OnDoctorClickListener)
        {
            drName.text = doctorList.doctorsName
            spec.text = doctorList.spec
            nbr.text = doctorList.nbr
            app.text = doctorList.app

            itemView.setOnClickListener {
                action.onItemClick(doctorList, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder
    {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.doctors_row, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        holder.initialize(doctorsList.get(position), clickListener)
    }

    override fun getItemCount(): Int
    {
        return doctorsList.size
    }

    fun updateList(doctorList: List<DoctorsUiModel>)
    {
        this.doctorsList = doctorList
        notifyDataSetChanged()
    }
    fun delete(adapterPosition: Int): Int
    {
        var id = 0
        val newList = doctorsList.filterIndexed { index, item ->
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
    interface OnDoctorClickListener
    {
        fun onItemClick(doctor: DoctorsUiModel, position: Int)
    }

}