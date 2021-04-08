package com.maliatecpharm.activity.mainmenu.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.maliatecpharm.R
import com.maliatecpharm.activity.mainmenu.activities.ActivityProfiles
import com.maliatecpharm.activity.mainmenu.database.ProfileDataBase
import com.maliatecpharm.activity.mainmenu.database.ProfilesAdapter
import kotlin.collections.ArrayList

class FragmentProfile : Fragment()

{
    private lateinit var fab: FloatingActionButton
    private lateinit var adapter: ProfilesAdapter
    private lateinit var deletedProfiles: String


    companion object {
        lateinit var dbHandler: ProfileDataBase
    }

    @SuppressLint("WrongConstant")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        dbHandler = ProfileDataBase(requireContext(), null, null, 1)
        fab = view.findViewById(R.id.fab)



        fab.setOnClickListener {
           val i = Intent(requireContext(), ActivityProfiles::class.java)
            startActivity(i)
        }


        adapter = ProfilesAdapter(requireContext(), ArrayList())
        val rv: RecyclerView = view.findViewById(R.id.recyclerView_Profile)
        rv.layoutManager = LinearLayoutManager(requireContext(), LinearLayout.VERTICAL, false)
        rv.adapter = adapter
        return view
    }




    override fun onResume() {

        super.onResume()
        val customersList = dbHandler.getProfiles(requireContext())
        adapter.addAll(customersList)
        adapter.notifyDataSetChanged()
    }


}


