package com.maliatecpharm.activity.mainmenu.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DoctorsDao
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addDoctor(doctorsEntity: DoctorsEntity)

    @Query("SELECT * FROM doctors ORDER BY id ASC")
    suspend fun readAllDoctors() :List<DoctorsEntity>

    @Query("DELETE FROM doctors WHERE id = :drId")
    suspend fun deleteDoctorById(drId: Int)

}