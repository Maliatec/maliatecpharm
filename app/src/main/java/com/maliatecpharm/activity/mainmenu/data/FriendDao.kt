package com.maliatecpharm.activity.mainmenu.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FriendDao
{
    @Query("SELECT * FROM friends ORDER BY id ASC")
    suspend fun readAllData() :List<FriendEntity>

    @Query("DELETE FROM friends WHERE id = :friendId")
    suspend fun deleteFriendById(friendId: Int)
}
