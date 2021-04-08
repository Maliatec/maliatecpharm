package com.maliatecpharm.activity.mainmenu.database

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maliatecpharm.R

class ProfilesAdapter(mCtx: Context, val profilesList: ArrayList<Profiles>) :
    RecyclerView.Adapter<ProfilesAdapter.ViewHolder>()
{
    val mCtx: Context = mCtx

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val txtFirstName: TextView = itemView.findViewById(R.id.firstName)
        val txtLastName: TextView = itemView.findViewById(R.id.lastName)
        val updateBtn: Button = itemView.findViewById(R.id.btnUpdate)
        val deleteBtn: Button = itemView.findViewById(R.id.btnDelete)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfilesAdapter.ViewHolder
    {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.profileslayoutas, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ProfilesAdapter.ViewHolder, position: Int)
    {
        val profiles: Profiles = profilesList[position]

        holder.txtFirstName.text = profiles.firstName
        holder.txtLastName.text = profiles.lastName
    }

    override fun getItemCount(): Int
    {
        return profilesList.size
    }

    fun addAll(lst: ArrayList<Profiles>)
    {
        profilesList.addAll(lst)
    }


}

