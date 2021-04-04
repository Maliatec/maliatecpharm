package com.maliatecpharm.activity.mainmenu.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.maliatecpharm.R

class FragmentHome : Fragment()
{


    private lateinit var emptyBtn: Button



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View?
    {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        emptyBtn = view.findViewById(R.id.button_emptyBtn)

        setOnBtnClicked()

//        view.setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_HomeFragment_to_MedicationsFragment) }
        return view
    }

    private fun setOnBtnClicked()
    {
        emptyBtn.setOnClickListener {
           // startActivity(Intent(requireContext(), AddMedications::class.java))
        }
    }
    

}
