package com.maliatecpharm.activity.mainmenu.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.maliatecpharm.R
import com.maliatecpharm.activity.mainmenu.activities.ActivityMedications

class FragmentAddMedication : Fragment()
{
    private lateinit var addButton: FloatingActionButton
    private lateinit var medicinsrv: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View?
    {
        val view = inflater.inflate(R.layout.fragment_addmedication, container, false)


        addButton = view.findViewById(R.id.floatingBtn)
        medicinsrv = view.findViewById(R.id.recyclerView_Medicins)
        onBtnClicked()
        return view
    }

    private fun onBtnClicked()
    {
        addButton.setOnClickListener {
            val i = Intent(requireContext(), ActivityMedications::class.java)
            startActivity(i)
        }
    }
}