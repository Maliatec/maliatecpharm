package com.maliatecpharm.activity.mainmenu.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profiles")
data class ProfileEntity(
    val firstName: String,
    val lastName: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
{
    fun toUserUiModel() = ProfileUiModel(
        id = id,
        firstName = firstName
    )
}