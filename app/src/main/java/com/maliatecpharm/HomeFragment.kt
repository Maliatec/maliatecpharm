package com.maliatecpharm

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
//import com.maliatecpharm.activity.Medications
import com.maliatecpharm.activity.mainmenu.AddMedicationsFragment

class HomeFragment : Fragment()
{


    private lateinit var emptyBtn: Button



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View?
    {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        emptyBtn = view.findViewById(R.id.emptyBtn)

        setOnBtnClicked()

        view.setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_HomeFragment_to_MedicationsFragment) }
        return view
    }

    private fun setOnBtnClicked()
    {
        emptyBtn.setOnClickListener {
            startActivity(Intent(requireContext(), AddMedicationsFragment::class.java))
        }
    }
    

}
