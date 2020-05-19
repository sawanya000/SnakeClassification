package com.mahidol.snakeclassification.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.mahidol.snakeclassification.Model.Detail
import com.mahidol.snakeclassification.Model.History
import java.sql.SQLException

class HistoryDataSource(context: Context) { //ใช้จัดการข้อมูล

    // Database fields
    private var database: SQLiteDatabase? = null
    private val dbHelper: MySQLiteHelper
    private val allColumnsHistory = arrayOf(
        MySQLiteHelper.COLUMN_ID,
        MySQLiteHelper.COLUMN_IMAGE,
        MySQLiteHelper.COLUMN_SPECIES,
        MySQLiteHelper.COLUMN_TIMESTAMP
    )
    private val allColumnsDetail = arrayOf(
        MySQLiteHelper.COLUMN_ID,
        MySQLiteHelper.COLUMN_RANK1,
        MySQLiteHelper.COLUMN_VALUE1,
        MySQLiteHelper.COLUMN_RANK2,
        MySQLiteHelper.COLUMN_VALUE2,
        MySQLiteHelper.COLUMN_RANK3,
        MySQLiteHelper.COLUMN_VALUE3
    )

    // make sure to close the cursor
    val allHistorys: ArrayList<History>
        get() {
            val historys = ArrayList<History>()

            val cursor = database!!.query(
                MySQLiteHelper.TABLE_HISTORY,
                allColumnsHistory, null, null, null, null, null
            )

            cursor.moveToFirst() //อ่านจาก record แรก
            while (!cursor.isAfterLast) {
                val history = cursorToHistory(cursor)
                historys.add(history)
                cursor.moveToNext()
            }
            cursor.close()
            return historys
        }

    val allDetails: ArrayList<Detail>
        get() {
            val details = ArrayList<Detail>()

            val cursor = database!!.query(
                MySQLiteHelper.TABLE_DETAIL,
                allColumnsDetail, null, null, null, null, null
            )

            cursor.moveToFirst() //อ่านจาก record แรก
            while (!cursor.isAfterLast) {
                val detail = cursorToDetail(cursor)
                details.add(detail)
                cursor.moveToNext()
            }
            cursor.close()
            return details
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
            allColumnsHistory, MySQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null
        )

        cursor.moveToFirst()
        val newHistory = cursorToHistory(cursor)
        cursor.close()
        println("kkkkkkkkkkkkkk"+newHistory.image.length)
        return newHistory
    }

    fun createDetail(detail: Detail): Detail? {
        val values = ContentValues()
        values.put(MySQLiteHelper.COLUMN_ID, detail.id)
        values.put(MySQLiteHelper.COLUMN_RANK1, detail.rank1)
        values.put(MySQLiteHelper.COLUMN_VALUE1, detail.value1)
        values.put(MySQLiteHelper.COLUMN_RANK2, detail.rank2)
        values.put(MySQLiteHelper.COLUMN_VALUE2, detail.value2)
        values.put(MySQLiteHelper.COLUMN_RANK3, detail.rank3)
        values.put(MySQLiteHelper.COLUMN_VALUE3, detail.value3)
        val insertId = database!!.insert(
            MySQLiteHelper.TABLE_DETAIL, null,
            values
        )

        //ต้อง query ใหม่เพื่อ refresh
        val cursor = database!!.query(
            MySQLiteHelper.TABLE_DETAIL,
            allColumnsDetail, MySQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null
        )
        cursor.moveToFirst()
        val newDetail = cursorToDetail(cursor)
        cursor.close()
        return newDetail
    }

    fun queryHistory(index: Long):History{
        val cursor = database!!.query(
            MySQLiteHelper.TABLE_HISTORY,
            allColumnsHistory, MySQLiteHelper.COLUMN_ID + " = " + index, null, null, null, null
        )
        cursor.moveToFirst()
        val history = cursorToHistory(cursor)
        cursor.close()
        return history
    }

    fun queryDetail(index: Long): Detail {
        val cursor = database!!.query(
            MySQLiteHelper.TABLE_DETAIL,
            allColumnsDetail, MySQLiteHelper.COLUMN_ID + " = " + index, null, null, null, null
        )
        cursor.moveToFirst()
        val newDetail = cursorToDetail(cursor)
        cursor.close()
        return newDetail
    }

    private fun cursorToHistory(cursor: Cursor): History {
        val history = History()
        history.id = cursor.getLong(0)
        history.image = cursor.getString(1)
        history.species = cursor.getString(2)
        history.timestamp = cursor.getString(3)
        return history
    }

    private fun cursorToDetail(cursor: Cursor): Detail {
        val detail = Detail()
        detail.id = cursor.getLong(0)
        detail.rank1 = cursor.getString(1)
        detail.value1 = cursor.getString(2)
        detail.rank2 = cursor.getString(3)
        detail.value2 = cursor.getString(4)
        detail.rank3 = cursor.getString(5)
        detail.value3 = cursor.getString(6)
        return detail
    }
}