package com.maliatecpharm.activity.mainmenu.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

const val DB_NAME = "ProfilesDB"
const val Table_NAME = "Profiles"
const val Column_NAME = "name"
const val Column_ID = "id"


class ProfileDataBase(var context: Context) : SQLiteOpenHelper(context, DB_NAME, null, 1)
{

    override fun onCreate(profileDB: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + Table_NAME + " ( " + Column_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + Column_NAME + " NVARCHAR(256) "+")"

        profileDB?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int)
    {
    }

    fun insertData(profile: Profile)
    {
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(Column_NAME, profile.name)


        var result = db.insert(Table_NAME , null,cv)

        if (result == (-1).toLong())
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()

        else
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()

    }
}
