package com.maliatecpharm.activity.mainmenu.activities

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel()
{
    private lateinit var timer: CountDownTimer
    val seconds = MutableLiveData<Int>()

    fun seconds() : LiveData<Int>{
        return seconds
    }
     fun start()
    {
        timer = object : CountDownTimer(10000, 1000)
        {
            override fun onTick(millisUntilFinished: Long)
            {
                val timeleft = (millisUntilFinished / (1000))
                seconds.value = timeleft.toInt()
            }

            override fun onFinish()
            {

            }
        }.start()
    }

    private fun stopTimer()
    {
        timer.cancel()
    }
}