package com.maliatecpharm.activity.mainmenu

import android.app.Application
import com.maliatecpharm.activity.mainmenu.data.AppDataBase

class MyApp: Application()
{
    override fun onCreate() {
        super.onCreate()
        AppDataBase.getDataBase(this)
    }
}