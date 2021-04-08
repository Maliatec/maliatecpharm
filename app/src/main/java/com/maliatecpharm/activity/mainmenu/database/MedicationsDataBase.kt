package com.maliatecpharm.activity.mainmenu.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

const val DATABASE_NAME = "MedicationsDB"
const val TABLE_NAME = "Medications"
const val COL_NAME = "name"
const val COL_ID = "id"


    class MedicationsDataBase(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1)
    {

        override fun onCreate(db: SQLiteDatabase?) {
            val createTable = "CREATE TABLE " + TABLE_NAME + " ( " + COL_ID +
                    " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_NAME + " NVARCHAR(256) "+")"

            db?.execSQL(createTable)
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int)
        {
        }

        fun insertDATA(medication: Medication)
        {
            val db = this.writableDatabase
            var cv = ContentValues()
            cv.put(COL_NAME, medication.name)


            var result = db.insert(TABLE_NAME , null,cv)

            if (result == (-1).toLong())
                Toast.makeText(context, "Failed",Toast.LENGTH_SHORT).show()

            else
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()

        }
    }
