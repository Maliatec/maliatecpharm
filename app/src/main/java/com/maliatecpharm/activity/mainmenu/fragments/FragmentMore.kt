package com.maliatecpharm.activity.mainmenu.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.maliatecpharm.R
import com.maliatecpharm.activity.mainmenu.activities.ActivityProfile


class FragmentMore : Fragment()
{
    private lateinit var moreemptyBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View?
    {
        val view = inflater.inflate(R.layout.fragment_more, container, false)

        moreemptyBtn = view.findViewById(R.id.button_moreEmptyBtn)
        setOnBtnClicked()
        return view
    }

    private fun setOnBtnClicked()
    {
        moreemptyBtn.setOnClickListener {
            startActivity(Intent(requireContext(), ActivityProfile::class.java))
        }
    }
}
