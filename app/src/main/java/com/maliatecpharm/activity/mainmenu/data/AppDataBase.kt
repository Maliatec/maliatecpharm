package com.maliatecpharm.activity.mainmenu.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ProfileEntity::class, UserEntity::class,
    MedicineEntity::class,DoctorsEntity::class, FriendEntity::class
    , VitalEntity::class, CalendarEntity::class],
    version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase()
{
    abstract fun userDao(): UserDao
    abstract fun medicineDao(): MedicineDao
    abstract fun doctorDao(): DoctorsDao
    abstract fun friendDao(): FriendDao
    abstract fun vitalDao(): VitalSignsDao
    abstract fun calendarDao(): CalendarDao

    companion object
    {
        @Volatile
        private var INSTANCE: AppDataBase? = null
        fun getDataBase(context: Context): AppDataBase
        {
            val tempInstance = INSTANCE
            if (tempInstance != null)
            {
                return tempInstance
            }

            synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "App_database"
                )
                    .build()
                INSTANCE = instance
                return instance

            }
        }
    }
}