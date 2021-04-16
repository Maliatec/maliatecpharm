package com.maliatecpharm.activity.mainmenu.data

import android.widget.Spinner
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicines")

data class MedicineEntity(
    val name: String,
    val dosage: String,
    val diagnosis: String ,
    val day:String,
    val startdate:String,
val enddate: String,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
{
    fun toUserUiModel() = MedicinesUiModel(
        id = id,
        name = name,
        dosage = dosage,
        diagnosis = diagnosis,
        day = day,
       startdate =startdate,
        enddate = enddate,

    )
}