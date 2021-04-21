package com.maliatecpharm.activity.mainmenu.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun registerUser(userEntity: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(profileEntity: ProfileEntity)

    @Query("SELECT * FROM profiles ORDER BY id ASC")
    suspend fun readAllData() :List<ProfileEntity>

    @Query("SELECT * FROM profiles WHERE id = :id")
     fun getProfileLiveData(id: Int) :LiveData<ProfileEntity?>

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    suspend fun getUserByEmailAndPass(email: String, password: String) : List<UserEntity>

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String) : List<UserEntity>

    @Query("DELETE FROM profiles WHERE id = :userId")
    suspend fun deleteProfileById(userId: Int)
}