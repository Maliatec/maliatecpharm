package com.maliatecpharm.activity.mainmenu.fragments

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.maliatecpharm.R

class FragmentMedFriend : Fragment()
{
    private lateinit var firstNameEmergnecyContact: EditText
    private lateinit var lastNameEmergnecyContact: EditText
    private lateinit var genderEmergnecyContact: TextView
    private lateinit var genderSpinnerEmergnecyContact: Spinner
    private lateinit var phoneEmergnecyContact: EditText
    private lateinit var emailEmergnecyContact: EditText
    private lateinit var saveButtonEmergnecyContact: Button
    private val GenderList = arrayOf(
        "Male", "Female"
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.fragment_medfriend, container, false)
        firstNameEmergnecyContact = view.findViewById(R.id.edittext_firstNameEmergencyContact)
        lastNameEmergnecyContact = view.findViewById(R.id.edittext_lastNameEmergencyContact)
        genderEmergnecyContact = view.findViewById(R.id.textview_genderEmergencyContact)
        genderSpinnerEmergnecyContact = view.findViewById(R.id.spinner_genderSpinnerEmergencyContact)
        phoneEmergnecyContact = view.findViewById(R.id.edittext_phoneEmergencyContact)
        emailEmergnecyContact = view.findViewById(R.id.edittext_emailEmergencyContact)
        saveButtonEmergnecyContact = view.findViewById(R.id.button_saveButtonEmergencyContact)

        genderSpinner()
        onSaveClickListener()
        return view
    }

    private fun onSaveClickListener()
    {
        saveButtonEmergnecyContact.setOnClickListener {
          if (validateInput())
            findNavController().navigate(R.id.action_fragmentMedFriend_to_fragmentlistfriend)

        }
    }

    private fun genderSpinner()
    {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, GenderList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpinnerEmergnecyContact.adapter = adapter
    }
    fun validateInput(): Boolean {
        if (firstNameEmergnecyContact.text.toString().equals("")) {
            firstNameEmergnecyContact.setError("Please Enter First Name")
            return false
        }
        if (lastNameEmergnecyContact.text.toString().equals("")) {
            lastNameEmergnecyContact.setError("Please Enter Last Name")
            return false
        }
        if (emailEmergnecyContact.text.toString().equals("")) {
            emailEmergnecyContact.setError("Please Enter Email")
            return false
        }

        if (phoneEmergnecyContact.text.toString().equals("")) {
            phoneEmergnecyContact.setError("Please Enter Contact No")
            return false
        }

        if (!isEmailValid(emailEmergnecyContact.text.toString())) {
            emailEmergnecyContact.setError("Please Enter Valid Email")
            return false
        }
        return true
    }
    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
