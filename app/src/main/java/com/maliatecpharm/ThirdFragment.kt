package com.maliatecpharm

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.Navigation
import com.maliatecpharm.activity.MaliaActivity
import com.maliatecpharm.activity.mainmenu.FirstFragment


class ThirdFragment : Fragment()
{
    private val sexList = arrayOf(
        "Male", "Female"
    )

    private lateinit var nameTv: TextView
    private lateinit var nameEt: EditText
    private lateinit var sexTv: TextView
    private lateinit var sexSpinner: Spinner
    private lateinit var ageTv: TextView
    private lateinit var ageEt: EditText
    private lateinit var saveBtn: Button




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        val view= inflater.inflate (R.layout.infos, container, false)

        nameTv = view.findViewById(R.id.Name_textview)
        nameEt = view.findViewById(R.id.nameEditText)
        sexTv = view.findViewById(R.id.Sex_textview)
        sexSpinner = view.findViewById(R.id.sexSpinner)
        ageTv = view.findViewById(R.id.Agetextview)
        ageEt = view.findViewById(R.id.ageEditText)
        saveBtn = view.findViewById(R.id.saveButton)

        sexSpinner()
        setOnBtnClicked()

      view.setOnClickListener{ Navigation.findNavController(view). navigate(R.id.action_ThirdFragment_to_FirstFragment)}
        return view
    }
    private fun sexSpinner()
    {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, sexList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sexSpinner.adapter = adapter
    }

    private fun setOnBtnClicked()
    {
        saveBtn.setOnClickListener {
            startActivity(Intent(requireContext(), MaliaActivity::class.java))
        }
    }
}