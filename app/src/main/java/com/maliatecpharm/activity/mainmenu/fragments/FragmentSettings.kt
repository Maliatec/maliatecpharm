package com.maliatecpharm.activity.mainmenu.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.maliatecpharm.R
import com.maliatecpharm.activity.mainmenu.activities.ActivityFeedback


class FragmentSettings: Fragment()
{
    private lateinit var notification :TextView
    private lateinit var whatIsNew: TextView
    private lateinit var feedback: TextView
    private lateinit var inviteAFriend: TextView
    private lateinit var about: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View?
    {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        notification = view.findViewById(R.id.textview_notification)
        whatIsNew = view.findViewById(R.id.textview_whatIsNew)
        feedback = view.findViewById(R.id.textview_feedback)
        inviteAFriend = view.findViewById(R.id.textview_inviteaFriend)
        about = view.findViewById(R.id.textview_about)

        setOnFeedbackClicked()
        return view
    }
    private fun setOnFeedbackClicked()
    {
        feedback.setOnClickListener {
             startActivity(Intent(requireContext(), ActivityFeedback::class.java))
        }
    }


}