package com.maliatecpharm.activity.mainmenu.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.maliatecpharm.activity.mainmenu.uimodel.DoctorsUiModel

@Entity(tableName = "doctors", indices = arrayOf(Index(value = arrayOf("nbr"), unique = true)))
data class DoctorsEntity
    (
    val drName: String,
    val spec: String,
    val nbr:String = "",
    val app: String = "",
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
    )
    {
        fun toUserUiModel() = DoctorsUiModel(
            id = id,
            doctorsName = drName,
            spec = spec,
            nbr = nbr,
            app = app

        )
    }
