package com.maliatecpharm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView


class VitalSignsFragment : Fragment()
{

    private lateinit var enterCholesterol: EditText
    private lateinit var enterFitness: EditText
    private lateinit var enterGlucose: EditText
    private lateinit var enterBloodPressure: EditText
    private lateinit var enterPulse: EditText
    private lateinit var enterQualityOfLife: EditText
    private lateinit var saveButton: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        val view = inflater.inflate(R.layout.fragment_vital_signs, container, false)


        enterCholesterol = view.findViewById(R.id.enterCholesterol_edittext)
        enterFitness = view.findViewById(R.id.enterFitness_edittext)
        enterGlucose = view.findViewById(R.id.enterGlucose_edittext)
        enterBloodPressure = view.findViewById(R.id.enterBloodPressure_edittext)
        enterPulse = view.findViewById(R.id.enterPulse_edittext)
        enterQualityOfLife = view.findViewById(R.id.QualityOfLife_edittext)
        saveButton = view.findViewById(R.id.saveBut)
//
//        enterCholesterol.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cholesterol, 0, 0, 0)
//        enterFitness.setCompoundDrawablesWithIntrinsicBounds(R.drawable.fitness, 0, 0, 0)
//        enterGlucose.setCompoundDrawablesWithIntrinsicBounds(R.drawable.glucose, 0, 0, 0)
//        enterBloodPressure.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bloodpressure, 0, 0, 0)
//        enterPulse.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pulse, 0, 0, 0)
//        enterQualityOfLife.setCompoundDrawablesWithIntrinsicBounds(R.drawable.qualoflife, 0, 0, 0)
//



        //        view.setOnClickListener{ Navigation.findNavController(view). navigate(R.id.action_MoreFragment_to_HomeFragment)}
        return view
    }

}