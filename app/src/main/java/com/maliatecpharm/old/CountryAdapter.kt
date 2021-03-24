package com.maliatecpharm

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maliatecpharm.old.MainActivity


class CountryAdapter(private val context: Context, private val countryInteractor: MainActivity)
    : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    private var countryList: List<Country> = emptyList()

    fun updateList(countryList: List<Country>){
        this.countryList = countryList
        notifyDataSetChanged()
    }

    //Needs to know what type of VIEW HOLDER TO INFLATE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val rootView: View = LayoutInflater.from(context).inflate(R.layout.country_view_holder, null, false)
        val lp = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        rootView.layoutParams = lp
        return CountryViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = countryList[position]
       holder.putCountry(country, position)
    }

    override fun getItemCount(): Int = countryList.size

    inner class CountryViewHolder constructor(private val view: View) : RecyclerView.ViewHolder(view){

        private val countryNameTv: TextView = view.findViewById(R.id.countryTv)

        fun putCountry(country: Country, position: Int){
            countryNameTv.text = country.name
            view.setOnClickListener {
                countryInteractor.onCountryClicked(country, position)
            }
        }
    }

    interface CountryInteractor {
        fun onCountryClicked(country: Country, position: Int)
    }
}

data class Country(val name: String)