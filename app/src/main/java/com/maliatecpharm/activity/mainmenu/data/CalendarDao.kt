package com.maliatecpharm.activity.mainmenu.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CalendarDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDate(calendarEntity: CalendarEntity)

    @Query("SELECT * FROM calendar ORDER BY id ASC")
    suspend fun readAllDates() :List<CalendarEntity>

    @Query("DELETE FROM calendar WHERE id = :medId")
    suspend fun deleteDateById(medId: Int)

    @Query("SELECT * FROM calendar WHERE id = :id")
    fun getDateLiveData(id: Int) : LiveData<CalendarEntity?>

}