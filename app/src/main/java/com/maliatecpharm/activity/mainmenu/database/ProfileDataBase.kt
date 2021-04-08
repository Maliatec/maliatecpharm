package com.maliatecpharm.activity.mainmenu.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.lang.Exception


class ProfileDataBase
    (
    context: Context,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION)
{
    companion object
    {
        private val DATABASE_NAME = "MyData.db"
        private val DATABASE_VERSION = 1

        val PROFILES_TABLE_NAME = "Profiles"
        val COLUMN_PROFILEID = "customerid"
        val COLUMN_PROFILEFIRSTNAME = "customername"
        val COLUMN_PROFILELASTNAME = "maxcredit"
    }

    override fun onCreate(db: SQLiteDatabase?)
    {
        val createProfilesTable = ("CREATE TABLE $PROFILES_TABLE_NAME (" +
                "$COLUMN_PROFILEID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $COLUMN_PROFILEFIRSTNAME TEXT," +
                " $COLUMN_PROFILELASTNAME TEXT)")

        db?.execSQL(createProfilesTable)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int)
    {
    }

    fun getProfiles(mCtx: Context): ArrayList<Profiles>
    {
        val qry = "Select * From $PROFILES_TABLE_NAME"
        val db = this.readableDatabase
        val cursor = db.rawQuery(qry, null)
        val profiles = ArrayList<Profiles>()

        if (cursor.count == 0)
            Toast.makeText(mCtx, "No Profiles Found", Toast.LENGTH_SHORT).show()
        else
        {
            while (cursor.moveToNext())
            {
                val profile = Profiles()
                profile.ID = cursor.getInt(cursor.getColumnIndex(COLUMN_PROFILEID))
                profile.firstName = cursor.getString(cursor.getColumnIndex(COLUMN_PROFILEFIRSTNAME))
                profile.lastName = cursor.getString(cursor.getColumnIndex(COLUMN_PROFILEFIRSTNAME))

                profiles.add(profile)
            }
            Toast.makeText(mCtx, "${cursor.count} Records Found", Toast.LENGTH_SHORT)
                .show()

        }
        cursor.close()
        db.close()
        return profiles
    }

    fun addProfile(mCtx: Context, customer: Profiles)
    {
        val values = ContentValues()

        values.put(COLUMN_PROFILEFIRSTNAME, customer.firstName)
        values.put(COLUMN_PROFILELASTNAME, customer.lastName)

        val db = this.writableDatabase

        try
        {
            db.insert(PROFILES_TABLE_NAME, null, values)
            Toast.makeText(mCtx, "Profile Added", Toast.LENGTH_SHORT).show()
        } catch (e: Exception)
        {
            Toast.makeText(mCtx, e.message, Toast.LENGTH_SHORT).show()
        }
        db.close()
    }

}
