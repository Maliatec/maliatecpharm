package com.maliatecpharm.activity.mainmenu.data

import android.text.Editable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profiles")
data class ProfileEntity(
    val firstName: String,
    val lastName: String,
    val mail: String,
    val phone: String,
    val weight:String,
    val height:String,
    val age:String,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
{
    fun toUserUiModel() = ProfileUiModel(
        id = id,
        firstName = firstName,
        lastName = lastName,
        mail = mail,
        phone = phone,
        height = height,
        weight = weight,
        age = age,

    )
}