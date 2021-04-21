package com.maliatecpharm.activity.mainmenu.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface VitalSignsDao
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addVital(vitalEntity: VitalEntity)

    @Query("SELECT * FROM vital_signs WHERE id = :id")
    fun getVitalLiveData(id: Int) : LiveData<VitalEntity?>


    @Query("SELECT * FROM vital_signs ORDER BY id ASC")
    suspend fun readAllData(): List<VitalEntity>

    @Query("DELETE FROM vital_signs WHERE id = :vitalId")
    suspend fun deleteVitalById(vitalId: Int)

}