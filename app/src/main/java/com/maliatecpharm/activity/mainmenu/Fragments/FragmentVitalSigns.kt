package com.maliatecpharm.activity.mainmenu.Fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.maliatecpharm.R


class FragmentVitalSigns : Fragment()
{

    private lateinit var enterCholesterol: EditText
    private lateinit var enterFitness: EditText
    private lateinit var enterGlucose: EditText
    private lateinit var enterBloodPressure: EditText
    private lateinit var enterPulse: EditText
    private lateinit var enterQualityOfLife: EditText
    private lateinit var takePictureBtn: Button
    private lateinit var chooseImage: ImageView
    private val REQUEST_CODE = 42
    private lateinit var saveButton: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        val view = inflater.inflate(R.layout.fragment_vitalsigns, container, false)


        enterCholesterol = view.findViewById(R.id.edittext_enterCholesterol)
        enterFitness = view.findViewById(R.id.edittext_enterFitness)
        enterGlucose = view.findViewById(R.id.edittext_enterGlucose)
        enterBloodPressure = view.findViewById(R.id.edittext_enterBloodPressure)
        enterPulse = view.findViewById(R.id.edittext_enterPulse)
        enterQualityOfLife = view.findViewById(R.id.edittext_qualityOfLife)
        takePictureBtn = view.findViewById(R.id.button_btnTakePic)
        chooseImage = view.findViewById(R.id.imageview_picture)

        saveButton = view.findViewById(R.id.button_saveButton)
//
//        enterCholesterol.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cholesterol, 0, 0, 0)
//        enterFitness.setCompoundDrawablesWithIntrinsicBounds(R.drawable.fitness, 0, 0, 0)
//        enterGlucose.setCompoundDrawablesWithIntrinsicBounds(R.drawable.glucose, 0, 0, 0)
//        enterBloodPressure.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bloodpressure, 0, 0, 0)
//        enterPulse.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pulse, 0, 0, 0)
//        enterQualityOfLife.setCompoundDrawablesWithIntrinsicBounds(R.drawable.qualoflife, 0, 0, 0)
//

        takePicture()

        //        view.setOnClickListener{ Navigation.findNavController(view). navigate(R.id.action_MoreFragment_to_HomeFragment)}
        return view
    }

    private fun takePicture()
    {
        takePictureBtn.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePictureIntent, REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK)
        {
            val takenImage = data?.extras?.get("data") as Bitmap
            chooseImage.setImageBitmap(takenImage)
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}