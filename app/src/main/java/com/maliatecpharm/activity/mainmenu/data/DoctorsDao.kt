package com.maliatecpharm.activity.mainmenu.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DoctorsDao
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addDoctor(doctorsEntity: DoctorsEntity)

    @Query("SELECT * FROM doctors WHERE id = :id")
    fun getDoctorsLiveData(id: Int) :LiveData<DoctorsEntity?>

    @Query("SELECT * FROM doctors ORDER BY id ASC")
    fun readAllDoctors() : LiveData<List<DoctorsEntity>>

    @Query("DELETE FROM doctors WHERE id = :drId")
    suspend fun deleteDoctorById(drId: Int)

}