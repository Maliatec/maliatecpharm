package com.maliatecpharm.activity.mainmenu

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.maliatecpharm.DoctorsFragment
import com.maliatecpharm.R
import com.maliatecpharm.activity.Medications
import com.maliatecpharm.activity.Profile
import com.maliatecpharm.adapter.*
import com.maliatecpharm.adapter.DayNameAdapter.DayNameInteractor
import com.maliatecpharm.uimodel.AlarmCount
import com.maliatecpharm.uimodel.InstructionsUIModel
import com.maliatecpharm.uimodel.MedicationTypeUIModel
import java.util.*


class AddMedicationsFragment : Fragment()
{
    private lateinit var addButton: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View?
    {
        val view = inflater.inflate(R.layout.add_fragment_medication, container, false)


        addButton  = view.findViewById(R.id.addButton)
        setOnButtonClicked()

        view.setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_MedicationsFragment_to_doctorsFragment) }
        return view
    }
    private fun setOnButtonClicked()
    {
        addButton.setOnClickListener {
            startActivity(Intent(requireContext(), DoctorsFragment::class.java))
        }
    }
}