package com.maliatecpharm.activity.mainmenu.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MedicineDao
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMedicine(medicineEntity: MedicineEntity)

    @Query("SELECT * FROM medicines ORDER BY id ASC")
    suspend fun readAllMedicins() :List<MedicineEntity>

    @Query("DELETE FROM medicines WHERE id = :medId")
    suspend fun deleteMedById(medId: Int)

    @Query("SELECT * FROM medicines WHERE id = :id")
    fun getMedLiveData(id: Int) : LiveData<MedicineEntity?>

}