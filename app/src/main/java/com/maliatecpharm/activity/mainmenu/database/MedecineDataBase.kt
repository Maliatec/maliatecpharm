package com.maliatecpharm.activity.mainmenu.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.EditText
import android.widget.Toast
import com.maliatecpharm.uimodel.Medication


class MedecineDataBase
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

        val MEDECINE_TABLE_NAME = "Profiles"
        val COLUMN_MEDECINEID = "customerid"
        val COLUMN_MEDECINENAME = "customername"

    }

    override fun onCreate(db: SQLiteDatabase?)
    {
        val createProfilesTable = ("CREATE TABLE $MEDECINE_TABLE_NAME (" +
                "$COLUMN_MEDECINEID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $COLUMN_MEDECINENAME TEXT)")

        db?.execSQL(createProfilesTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int)
    {
    }

    fun getMedications(mCtx: Context): ArrayList<Medication>
    {
        val qry = "Select * From $MEDECINE_TABLE_NAME"
        val db = this.readableDatabase
        val cursor = db.rawQuery(qry, null)
        val medications = ArrayList<Medication>()

        if (cursor.count == 0)
            Toast.makeText(mCtx, "No Profiles Found", Toast.LENGTH_SHORT).show()
        else
        {
            while (cursor.moveToNext())
            {
                val medication =Medication()
                medication.id= cursor.getInt(cursor.getColumnIndex(COLUMN_MEDECINEID))
                medication.name = cursor.getString(cursor.getColumnIndex(COLUMN_MEDECINENAME))


              medications.add(medication)
            }
            Toast.makeText(mCtx, "${cursor.count} Records Found", Toast.LENGTH_SHORT)
                .show()
        }
        cursor.close()
        db.close()

        return medications
    }

    fun addMedication(mCtx: Context, medication: Medication)
    {
        val values = ContentValues()
        values.put(COLUMN_MEDECINENAME, medication.name)

        val db = this.writableDatabase
        try
        {
            db.insert(MEDECINE_TABLE_NAME, null, values)
            Toast.makeText(mCtx, "Profile Added", Toast.LENGTH_SHORT).show()
        } catch (e: Exception)
        {
            Toast.makeText(mCtx, e.message, Toast.LENGTH_SHORT).show()
        }
        db.close()
    }


}