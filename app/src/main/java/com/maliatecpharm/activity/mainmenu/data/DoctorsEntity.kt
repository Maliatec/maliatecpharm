package com.maliatecpharm.activity.mainmenu.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maliatecpharm.activity.mainmenu.uimodel.DoctorsUiModel

@Entity(tableName = "doctors")

data class DoctorsEntity
    (
    val drName: String,
    val spec: String,
    val nbr:String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
    )
    {
        fun toUserUiModel() = DoctorsUiModel(
            id = id,
            doctorsName = drName,
            spec = spec,
            nbr = nbr
        )
    }
