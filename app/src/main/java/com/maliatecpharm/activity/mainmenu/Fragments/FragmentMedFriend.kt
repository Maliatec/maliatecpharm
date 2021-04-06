package com.maliatecpharm.activity.mainmenu.Fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.maliatecpharm.R
import java.util.regex.Matcher
import java.util.regex.Pattern

class FragmentMedFriend : Fragment()
{
    private lateinit var firstNameEmergnecyContact: EditText
    private lateinit var lastNameEmergnecyContact: EditText
    private lateinit var genderEmergnecyContact: TextView
    private lateinit var genderSpinnerEmergnecyContact: Spinner
    private lateinit var phoneEmergnecyContact: EditText
    private lateinit var validationPhoneButtonEmergnecyContact: TextView
    private lateinit var emailEmergnecyContact: EditText
    private lateinit var emailValidationEmergnecyContact: TextView
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
        validationPhoneButtonEmergnecyContact = view.findViewById(R.id.textview_validationNumberTextViewEmergencyContact)
        emailEmergnecyContact = view.findViewById(R.id.edittext_emailEmergencyContact)
        emailValidationEmergnecyContact = view.findViewById(R.id.textview_valdiationEmergencyContactEmailTextView)
        saveButtonEmergnecyContact = view.findViewById(R.id.button_saveButtonEmergencyContact)


        genderSpinner()
        phoneValidation()
        emailValidation()

        return view
    }

    private fun genderSpinner()
    {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, GenderList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpinnerEmergnecyContact.adapter = adapter
    }

    private fun phoneValidation()
    {
        phoneEmergnecyContact.addTextChangedListener(object : TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
            {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {
                if (mobileValidate(phoneEmergnecyContact.text.toString()))
                {
                    validationPhoneButtonEmergnecyContact.isEnabled = true
                }
                else
                    validationPhoneButtonEmergnecyContact.isEnabled = false
            }

            override fun afterTextChanged(s: Editable?)
            {
            }
        })
    }

    private fun mobileValidate(text: String?): Boolean
    {
        var p: Pattern = Pattern.compile("[0-8][0-9]{7}")
        var m: Matcher = p.matcher(text)
        return m.matches()

    }

    private fun emailValidation(){
        emailEmergnecyContact.addTextChangedListener(object :TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
            {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {
                if(android.util.Patterns.EMAIL_ADDRESS.matcher(emailEmergnecyContact.text.toString()).matches())
                    emailValidationEmergnecyContact.isEnabled = true
                else emailValidationEmergnecyContact.isEnabled = false
                //                username.setError("Invalid Email")
            }
            override fun afterTextChanged(s: Editable?)
            {
            }
        })
    }

}
