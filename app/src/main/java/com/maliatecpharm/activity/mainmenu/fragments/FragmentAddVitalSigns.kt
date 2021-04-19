package com.maliatecpharm.activity.mainmenu.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.maliatecpharm.R
import com.maliatecpharm.activity.mainmenu.data.AppDataBase
import com.maliatecpharm.activity.mainmenu.data.ProfileEntity
import com.maliatecpharm.activity.mainmenu.data.VitalEntity
import com.maliatecpharm.activity.mainmenu.data.VitalSignsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FragmentAddVitalSigns : Fragment()
{
    private val vitalDao: VitalSignsDao by lazy {
        AppDataBase.getDataBase(requireContext()).vitalDao()
    }

    private lateinit var enterName: EditText
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
        val view = inflater.inflate(R.layout.fragment_vital_signs, container, false)
        enterCholesterol = view.findViewById(R.id.edittext_enterCholesterol)
        enterFitness =view. findViewById(R.id.edittext_enterFitness)
        enterGlucose = view.findViewById(R.id.edittext_enterGlucose)
        enterBloodPressure =view. findViewById(R.id.edittext_enterBloodPressure)
        enterPulse = view.findViewById(R.id.edittext_enterPulse)
        enterQualityOfLife =view.findViewById(R.id.edittext_qualityOfLife)
        takePictureBtn = view.findViewById(R.id.button_btnTakePic)
        chooseImage = view.findViewById(R.id.imageview_picture)
        saveButton =view. findViewById(R.id.button_saveButton)
        enterName = view.findViewById(R.id.edittext_firstName)

        takePicture()
        onSaveClickListener()

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

    private fun onSaveClickListener()
    {
        saveButton.setOnClickListener {
            insertDataToDataBase()
        }
    }

    private fun insertDataToDataBase()
    {
        if (validateInput())
        {

            val firstName = enterName.text.toString()

            if (inputCheck(firstName))
            {
                Toast.makeText(requireContext(), "Form validate", Toast.LENGTH_SHORT).show()
                val vital = VitalEntity(firstName)

                lifecycleScope.launch(Dispatchers.IO) {
                    vitalDao.addVital(vital)
                }
                findNavController().popBackStack()
            }
        }
    }

    private fun inputCheck(firstName: String): Boolean
    {
        return !(TextUtils.isEmpty(firstName))
    }

    fun validateInput(): Boolean
    {
        if (enterName.text.toString().equals(""))
        {
            enterName.setError("Please Enter First Name")
            return false
        }
        return true
    }

}