package com.maliatecpharm.activity.mainmenu

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.maliatecpharm.R
import com.maliatecpharm.activity.MaliaActivity

class FirstFragment : Fragment()
{
    private lateinit var text1: TextView
    private lateinit var btn1: Button
    private lateinit var text2: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View?
    {
        val view= inflater.inflate (R.layout.medications_layout, container, false)

        text1 = view.findViewById(R.id.text1)
        btn1 = view.findViewById(R.id.btn1)
        text2 = view.findViewById(R.id.text2)

        setOnBtnClicked()
        view.setOnClickListener{ Navigation.findNavController(view). navigate(R.id.NavigateToSecondFragment)}

        return view
    }


    private fun setOnBtnClicked()
    {
        btn1.setOnClickListener {
            startActivity(Intent(requireContext(), MaliaActivity::class.java))
        }
    }

}