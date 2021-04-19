package com.maliatecpharm.activity.mainmenu.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vital_signs")
data class VitalEntity(
    val firstName: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
{
    fun toUserUiModel() = VitalUiModel(
        id = id,
        firstName = firstName,
    )
}