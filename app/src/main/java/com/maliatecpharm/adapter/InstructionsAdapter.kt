package com.maliatecpharm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.TimePicker
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.maliatecpharm.R
import com.maliatecpharm.uimodel.InstructionsUIModel

class InstructionsAdapter(
    private val context: Context,
    private val instructionsInteractor: InstructionsInteractor
) :
    RecyclerView.Adapter<InstructionsAdapter.MedViewHolder>() {

    private var instructionsUIModelList: List<InstructionsUIModel> = emptyList()

    fun updateList(instructionsUIModelList: List<InstructionsUIModel>) {
        this.instructionsUIModelList = instructionsUIModelList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MedViewHolder {
        val rootView: View =
            LayoutInflater.from(context).inflate(R.layout.instruction_view_holder, null, false)

        return MedViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: MedViewHolder, position: Int) {
        val instructions = instructionsUIModelList[position]
        holder.bindData(instructions)
    }

    override fun getItemCount() = instructionsUIModelList.size

    inner class MedViewHolder constructor(private val view: View) : RecyclerView.ViewHolder(view) {

        private val instTv: TextView = view.findViewById(R.id.textview_instruction)

        fun bindData(instructionsUIModel: InstructionsUIModel) {
            instTv.text = instructionsUIModel.name

            val color = ContextCompat.getColor(context,instructionsUIModel.colorRes)
            instTv.setBackgroundColor(color)

            view.setOnClickListener {
                instructionsInteractor.onInstructionClicked(instructionsUIModel)
            }
        }
    }

    interface InstructionsInteractor {
        fun onInstructionClicked(instructionsUIModel: InstructionsUIModel)

    }
}