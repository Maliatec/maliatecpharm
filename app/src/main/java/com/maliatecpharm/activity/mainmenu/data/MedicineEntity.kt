package com.maliatecpharm.activity.mainmenu.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicines")

data class MedicineEntity
    (
    val name: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
{
    fun toUserUiModel() = MedicinesUiModel(
        id = id,
        Name = name

    )
}