package com.maliatecpharm.old


import android.os.Bundle
import android.util.Log

import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maliatecpharm.Country
import com.maliatecpharm.CountryAdapter
import com.maliatecpharm.R


class MainActivity : AppCompatActivity(), CountryAdapter.CountryInteractor {

    private lateinit var countrySpinner: Spinner

    private lateinit var countryRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()
        populateSpinner()
        populateRecyclerView()
    }

    private fun setupViews() {
        countrySpinner = findViewById(R.id.spinner)
        countryRecyclerView = findViewById(R.id.countryList)
    }

    private fun populateRecyclerView() {
        countryRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            //setHasFixedSize(true)

            val countryList = listOf<Country>(
                    Country(name = "Lebanon"),
                    Country(name = "Palestine"),
                    Country(name = "France"),
                    Country(name = "Morocco"),
                    Country(name = "China")
            )

            val countryAdapter = CountryAdapter(context = this@MainActivity, this@MainActivity)
            adapter = countryAdapter
            countryAdapter.updateList(countryList)
        }
    }

    private fun populateSpinner() {

        val languages = arrayOf("English", "French", "Spanish",
                "Hindi", "Russian", "Telugu", "Chinese", "German",
                "Portuguese", "Arabic", "Dutch", "Urdu", "Italian",
                "Tamil", "Persian", "Turkish", "Other")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        countrySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.d("Spinner", position.toString())

                val language = languages[position]
                Log.d("Spinner", language)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d("Spinner", "onNothingSelected")
            }
        }

        countrySpinner.adapter = adapter
    }

    override fun onCountryClicked(country: Country, position: Int) {
        Toast.makeText(this, country.name + " " + position, Toast.LENGTH_LONG).show()
    }
}
