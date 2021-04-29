 package com.maliatecpharm.activity.mainmenu.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.maliatecpharm.R
import com.maliatecpharm.activity.mainmenu.uimodel.MedicationTypeUIModel

class MedicationTypeAdapter(
    private val context: Context,
    private val medicationTypeInteractor: MedicationTypeInteractor
) :
    RecyclerView.Adapter<MedicationTypeAdapter.MedicationTypeViewHolder>() {
    private var medicationTypeList: List<MedicationTypeUIModel> = emptyList()
    fun updateList(medTypeList: List<MedicationTypeUIModel>) {
        this.medicationTypeList = medTypeList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicationTypeViewHolder {
        val type: View =
            LayoutInflater.from(context).inflate(R.layout.viewholder_medicationtype, null, false)
        return MedicationTypeViewHolder(type)
    }
    override fun onBindViewHolder(holder: MedicationTypeViewHolder, position: Int) {
        val medicationType = medicationTypeList[position]
        holder.bindData(medicationType)
    }

    override fun getItemCount() = medicationTypeList.size
    inner class MedicationTypeViewHolder constructor(private val view: View) :
        RecyclerView.ViewHolder(view) {
        private val typeTv: TextView = view.findViewById(R.id.textview_medication)
        private val medicationIv: ImageView = view.findViewById(R.id.imageview_medication)
        private val innerFrame: FrameLayout = view.findViewById(R.id.inner_layout)

        fun bindData(medicationType: MedicationTypeUIModel) {
            typeTv.text = medicationType.name
            medicationIv.setBackgroundResource(medicationType.medicationImageRes)
            val color = ContextCompat.getColor(context, medicationType.colorRes)
            innerFrame.setBackgroundColor(color)
            typeTv.setBackgroundColor(color)

            view.setOnClickListener {
                medicationTypeInteractor.onMedicationTypeClicked(medicationType)
            }
        }
    }
    interface MedicationTypeInteractor {
        fun onMedicationTypeClicked(medicationType: MedicationTypeUIModel)
    }
}


