package com.maliatecpharm.activity.mainmenu.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.maliatecpharm.R
import com.maliatecpharm.activity.mainmenu.activities.ActivityVitalSigns


class FragmentVitalSigns : Fragment()
{
    private lateinit var addButton: FloatingActionButton


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        val view = inflater.inflate(R.layout.fragment_vitalsigns, container, false)
        addButton = view.findViewById(R.id.button_addButton)
        setOnButtonClicked()

        //        view.setOnClickListener{ Navigation.findNavController(view). navigate(R.id.action_MoreFragment_to_HomeFragment)}
        return view
    }

    private fun setOnButtonClicked()
    {
        addButton.setOnClickListener {
            startActivity(Intent(requireContext(), ActivityVitalSigns::class.java))
        }
    }
}
