package com.maliatecpharm

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.Navigation
import com.maliatecpharm.activity.Profile


class MoreFragment : Fragment()
{
    private lateinit var moretv: TextView
    private lateinit var moreemptyBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View?
    {
        val view= inflater.inflate (R.layout.fragment_more, container, false)
        moretv = view.findViewById(R.id.moreTextView)
        moreemptyBtn = view.findViewById(R.id.moreEmptyBtn)

        setOnBtnClicked()


//        view.setOnClickListener{ Navigation.findNavController(view). navigate(R.id.action_MoreFragment_to_HomeFragment)}
        return view
}
private fun setOnBtnClicked()
{
    moreemptyBtn.setOnClickListener {
        startActivity(Intent(requireContext(), Profile::class.java))
    }
}
}
