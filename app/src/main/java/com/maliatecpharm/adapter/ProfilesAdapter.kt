package com.maliatecpharm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maliatecpharm.R
import com.maliatecpharm.uimodel.Profiles

class ProfilesAdapter(mCtx: Context, private val profilesList: ArrayList<Profiles>) :
    RecyclerView.Adapter<ProfilesAdapter.ViewHolder>()
{
    val mCtx: Context = mCtx

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val txtFirstName: TextView = itemView.findViewById(R.id.firstName)
        val txtLastName: TextView = itemView.findViewById(R.id.lastName)

    }

    fun delete (position: Int)
    {
        profilesList.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.profileslayoutas, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
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

