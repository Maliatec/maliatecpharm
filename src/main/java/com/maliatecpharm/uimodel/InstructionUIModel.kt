package com.maliatecpharm.uimodel

import androidx.annotation.ColorRes
import com.maliatecpharm.R

data class InstructionsUIModel(
    val id: Int,
    val name: String,

    @ColorRes val colorRes: Int = R.color.white
)