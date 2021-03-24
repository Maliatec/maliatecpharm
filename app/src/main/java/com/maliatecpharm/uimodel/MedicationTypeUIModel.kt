package com.maliatecpharm.uimodel

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.maliatecpharm.R

data class MedicationTypeUIModel constructor(
    val id: Int,
    val name: String,
    @DrawableRes val  medicationImageRes: Int,
    @ColorRes val colorRes: Int = R.color.white)
