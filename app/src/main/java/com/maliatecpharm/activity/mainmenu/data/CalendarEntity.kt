package com.maliatecpharm.activity.mainmenu.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "calendar")

data class CalendarEntity
    (
    val day: String,
    val startdate: String,
    val enddate: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)
{
    fun toUserUiModel() = CalendarUiModel(
        id = id,
        day = day,
        startdate = startdate,
        enddate = enddate,

        )
}