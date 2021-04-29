package com.maliatecpharm.activity.mainmenu.fragments

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maliatecpharm.R
import com.maliatecpharm.activity.mainmenu.activities.ActivityAddDiagnosis
import com.maliatecpharm.activity.mainmenu.adapter.*
import com.maliatecpharm.activity.mainmenu.data.AppDataBase
import com.maliatecpharm.activity.mainmenu.data.MedicineDao
import com.maliatecpharm.activity.mainmenu.data.MedicineEntity
import com.maliatecpharm.activity.mainmenu.uimodel.InstructionsUIModel
import com.maliatecpharm.activity.mainmenu.uimodel.MedicationTypeUIModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class FragmentAddMedication : Fragment(),
    MedicationTypeAdapter.MedicationTypeInteractor,
    AdapterView.OnItemSelectedListener
{
    private val medicineDao: MedicineDao by lazy {
        AppDataBase.getDataBase(requireContext()).medicineDao()
    }

    private val medicationTypeAdapter by lazy {
        MedicationTypeAdapter(context = requireContext(), medicationTypeInteractor = this)
    }

    private var medicineEntity = MedicineEntity("",
        "", "")

    private val instructionsAdapter by lazy {
        InstructionsAdapter(context = requireContext()).apply {
            onMedicationInstructionClicked = { clickedItem ->
                val updatedList = instructionsList.map { item ->
                    item.copy(colorRes = getColor(selected = item.id == clickedItem.id))
                }
                updateList(updatedList)
            }
        }
    }
    private val instructionsList = listOf(
        InstructionsUIModel(id = 1, name = "No instructions", colorRes = R.color.teal_200),
        InstructionsUIModel(id = 2, name = "Before eating"),
        InstructionsUIModel(id = 3, name = "After eating"),
        InstructionsUIModel(id = 4, name = "While eating")
    )

    private val medicationTypeList = listOf(
        MedicationTypeUIModel(id = 1, name = "Syrup", medicationImageRes = R.drawable.image, colorRes = R.color.teal_200),
        MedicationTypeUIModel(id = 2, name = "Capsule", medicationImageRes = R.drawable.images),
        MedicationTypeUIModel(id = 3, name = "Syringe", medicationImageRes = R.drawable.syringue),
        MedicationTypeUIModel(id = 4, name = "Tablet", medicationImageRes = R.drawable.tablet),
    )
    private val pillsList = arrayOf(
        "Pill(s)", "CC", "MI", "Gr", "Mg",
        "Drop(s)", "Piece(s)", "Puff(s)", "Unit(s)",
        "Teaspoon", "Patch", "Mcg", "lu", "Meq", "Cartoon", "Spray"
    )

    private val medicinesList = mutableListOf<String>(
        "",
        "Cyclophosphamide",
        "Panadol", "Paracetamol", "Aspirin",
        "Aspicot", "Prozac", "Dareq", "Oradus",
        "Advil", "EuroFer", "Other"
    )

    private val conditionsList = mutableListOf<String>(
        "",
        "Cancer", "Heart Disease", "Kidney problems",
        "Pulmonary Disease", "Rhumatism", "Bone Problems", "Immunity Problems",
        "Eyes Problems"
    )

    private lateinit var pillsSpinner: Spinner
    private lateinit var medicationName: TextView
    private lateinit var diagnosis: TextView
    private lateinit var diagnosisSpinner: Spinner
    private lateinit var condition: EditText
    private lateinit var dosage: TextView
    private lateinit var enterDosage: EditText
    private lateinit var medicationTypeTv: TextView
    private lateinit var instructionsTv: TextView
    private lateinit var instructionsRecyclerView: RecyclerView
    private lateinit var medicationTypeRecyclerView: RecyclerView
    private lateinit var medicinesSpinner: Spinner
    private lateinit var medicineName: EditText
    val context = this
    private lateinit var addImage: ImageView
    private lateinit var addDiagImage: ImageView
    private lateinit var saveBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View?
    {
        val view = inflater.inflate(R.layout.fragment_add_medication, container, false)

        pillsSpinner = view.findViewById(R.id.spinner_pillsSpinner)
        medicationName = view.findViewById(R.id.textview_medicationName)
        diagnosis = view.findViewById(R.id.textview_nameOfDisease)
        diagnosisSpinner = view.findViewById(R.id.spinner_diagnosisSpinner)
        condition = view.findViewById(R.id.diagnosis)
        dosage = view.findViewById(R.id.textview_dosage)
        enterDosage = view.findViewById(R.id.edittext_dosage)
        instructionsTv = view.findViewById(R.id.textview_instructions)
        medicationTypeTv = view.findViewById(R.id.textview_medicationType)
        instructionsRecyclerView = view.findViewById(R.id.recyclerview_instructions)
        medicationTypeRecyclerView = view.findViewById(R.id.recyclerview_medicationType)
        medicinesSpinner = view.findViewById(R.id.spinner_medicinesList)
        medicineName = view.findViewById(R.id.medicineName)
        addImage = view.findViewById(R.id.addImage)
        addDiagImage = view.findViewById(R.id.addDiagnosisImage)
        saveBtn = view.findViewById(R.id.Save)
        setupTextViews()
        medSpinner()
        diagnosisSpinner()
        populateInstructionsRecycleView()
        populateTypeRecycleView()
        medicineNameSpinner()
        onSaveClickListener()
        onImageClickListener()
        onImageeClickListener()
        linkMedicinesSpinnerToEditText()
        linkDiagnosisSpinnerToEditText()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        val medId = arguments?.getInt("medId") ?: -1
        medicineDao.getMedLiveData(medId).observe(viewLifecycleOwner) {

            if (it != null)
            {
                medicineEntity = it
                medicationName.text = it.name
                enterDosage.setText(it.dosage)
                diagnosis.setText(it.diagnosis)
            }
        }
    }

    private fun onImageClickListener()
    {
        addImage.setOnClickListener {
            findNavController().navigate(R.id.action_addMedicationFragment_to_fragmentAddMedicine)
        }
    }

    private fun onImageeClickListener()
    {
        addDiagImage.setOnClickListener {
            startActivity(Intent(requireContext(), ActivityAddDiagnosis::class.java))
        }
    }

    private fun onSaveClickListener()
    {
        saveBtn.setOnClickListener {
            insertDataToDataBase()
            findNavController().navigate(R.id.action_addMedicationFragment_to_fragmentAddMedicine)
        }
    }

    private fun insertDataToDataBase()
    {
        val medicineName = medicineName.text.toString()
        val dosage = enterDosage.text.toString()
        val diagnosis = condition.text.toString()

        if (inputCheck(medicineName, dosage, diagnosis))
        {
            val medicine = MedicineEntity(medicineName, dosage, diagnosis)
            lifecycleScope.launch(Dispatchers.IO) {
                medicineDao.addMedicine(medicine)
            }
            Toast.makeText(requireContext(), "Medicine Added ", Toast.LENGTH_SHORT).show()
        }
        else
            Toast.makeText(requireContext(), "Please fill medicine name ", Toast.LENGTH_SHORT).show()
    }

    private fun inputCheck(name: String, dosage: String, diagnosis: String): Boolean
    {
        return !(TextUtils.isEmpty(name) && TextUtils.isEmpty(dosage) && TextUtils.isEmpty(diagnosis)
                )
    }

    private fun setupTextViews()
    {
        dosage.text = getString(R.string.dosage)
        instructionsTv.text = getString(R.string.instructions)
        medicationTypeTv.text = getString(R.string.medication_type)
        medicationName.text = getString(R.string.medication_title)
    }

    private fun medSpinner()
    {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, pillsList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        pillsSpinner.adapter = adapter
    }

    private fun medicineNameSpinner()
    {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, medicinesList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        medicinesSpinner.adapter = adapter
    }

    private fun linkMedicinesSpinnerToEditText()
    {
        medicinesSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                medicineName.setText(medicinesSpinner.selectedItem.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?)
            {
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
    {
    }

    override fun onNothingSelected(parent: AdapterView<*>?)
    {
    }

    private fun diagnosisSpinner()
    {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, conditionsList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        diagnosisSpinner.adapter = adapter
    }

    private fun linkDiagnosisSpinnerToEditText()
    {
        diagnosisSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                condition.setText(diagnosisSpinner.selectedItem.toString())
            }
            override fun onNothingSelected(parent: AdapterView<*>?)
            {
            }
        }
    }
    private fun populateInstructionsRecycleView()
    {
        instructionsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = instructionsAdapter
            instructionsAdapter.updateList(instructionsList)
        }
    }

    private fun populateTypeRecycleView()
    {
        medicationTypeRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = medicationTypeAdapter
            medicationTypeAdapter.updateList(medicationTypeList)
        }
    }

    override fun onMedicationTypeClicked(medicationType: MedicationTypeUIModel)
    {
        val updatedList = medicationTypeList.map { item ->
            item.copy(colorRes = getColor(selected = item.id == medicationType.id))
        }
        medicationTypeAdapter.updateList(updatedList)
    }
    private fun getColor(selected: Boolean) = if (selected) R.color.teal_200 else R.color.white

}

