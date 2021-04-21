package com.maliatecpharm.activity.mainmenu.data

import android.media.Image
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vital_signs")
data class VitalEntity(
    val time: String,
    val cholesterol:String,
    val fitness:String,
    val glucose:String,
    val bloodpressure:String,
    val pulse:String,
    val physicalact:String,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
{
    fun toUserUiModel() = VitalUiModel(
        id = id,
        time = time,
        cholesterol = cholesterol,
        fitness = fitness,
        glucose = glucose,
        bloodpressure = bloodpressure,
        pulse = pulse,
        physicalact = physicalact,

    )
}