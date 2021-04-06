package com.maliatecpharm.activity.mainmenu.Activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.maliatecpharm.R

class ActivityVitalSigns:AppCompatActivity()
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



    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vitalsigns)


        enterCholesterol = findViewById(R.id.edittext_enterCholesterol)
        enterFitness = findViewById(R.id.edittext_enterFitness)
        enterGlucose = findViewById(R.id.edittext_enterGlucose)
        enterBloodPressure = findViewById(R.id.edittext_enterBloodPressure)
        enterPulse = findViewById(R.id.edittext_enterPulse)
        enterQualityOfLife =findViewById(R.id.edittext_qualityOfLife)
        takePictureBtn = findViewById(R.id.button_btnTakePic)
        chooseImage = findViewById(R.id.imageview_picture)
        saveButton = findViewById(R.id.button_saveButton)

        takePicture()

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