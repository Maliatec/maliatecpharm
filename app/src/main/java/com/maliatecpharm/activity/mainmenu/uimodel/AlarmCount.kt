package com.maliatecpharm.activity.mainmenu.uimodel



data class AlarmCount(
    val id: Int,
    val text: String,
    var timeList: MutableList<String>
)
