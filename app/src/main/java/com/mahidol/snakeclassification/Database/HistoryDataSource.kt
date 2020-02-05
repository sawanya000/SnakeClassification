package com.mahidol.snakeclassification.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.mahidol.snakeclassification.Model.History
import java.sql.SQLException

class HistoryDataSource(context: Context) { //ใช้จัดการข้อมูล

    // Database fields
    private var database: SQLiteDatabase? = null
    private val dbHelper: MySQLiteHelper
    private val allColumns = arrayOf(
        MySQLiteHelper.COLUMN_ID,
        MySQLiteHelper.COLUMN_IMAGE,
        MySQLiteHelper.COLUMN_SPECIES,
        MySQLiteHelper.COLUMN_TIMESTAMP
    )

    // make sure to close the cursor
    val allHistorys: ArrayList<History>
        get() {
            val historys = ArrayList<History>()

            val cursor = database!!.query(
                MySQLiteHelper.TABLE_HISTORY,
                allColumns, null, null, null, null, null
            )

            cursor.moveToFirst() //อ่านจาก record แรก
            while (!cursor.isAfterLast) {
                val comment = cursorToHistory(cursor)
                historys.add(comment)
                cursor.moveToNext()
            }
            cursor.close()
            return historys
        }

    init {
        dbHelper = MySQLiteHelper(context)
    }

    @Throws(SQLException::class)
    fun open() {
        database = dbHelper.writableDatabase
    }

    fun close() {
        dbHelper.close()
    }

    fun createHistory(image: String, species: String, timestamp: String): History {
        val values = ContentValues()
        values.put(MySQLiteHelper.COLUMN_IMAGE, image) //เอา input ไปเก็บไว้ใน COLUMN_COMMENT ก่อน
        values.put(MySQLiteHelper.COLUMN_SPECIES, species)
        values.put(MySQLiteHelper.COLUMN_TIMESTAMP, timestamp)
        val insertId = database!!.insert(
            MySQLiteHelper.TABLE_HISTORY, null,
            values
        )

        //ต้อง query ใหม่เพื่อ refresh
        val cursor = database!!.query(
            MySQLiteHelper.TABLE_HISTORY,
            allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null
        )
        cursor.moveToFirst()
        val newHistory = cursorToHistory(cursor)
        cursor.close()
        return newHistory
    }


    private fun cursorToHistory(cursor: Cursor): History {
        val history = History()
        history.id = cursor.getLong(0)
        history.image = cursor.getString(1)
        history.species = cursor.getString(2)
        history.timestamp = cursor.getString(3)
        return history
    }
}