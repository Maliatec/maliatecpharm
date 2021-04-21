package com.maliatecpharm.activity.mainmenu.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.maliatecpharm.R

class FragmentAddMedicine : Fragment()
{
    private lateinit var addMedication: Button
    private lateinit var medicineName: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        val view = inflater.inflate(R.layout.fragment_add_medicine, container, false)

        addMedication = view.findViewById(R.id.button_addBtn)
        medicineName = view.findViewById(R.id.edittext_nameOfMedicine)

        onAddClickListener()

        return view
    }

    private fun onAddClickListener()
    {
        addMedication.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentAddMedicine_to_addMedicationFragment)
        }
    }


}