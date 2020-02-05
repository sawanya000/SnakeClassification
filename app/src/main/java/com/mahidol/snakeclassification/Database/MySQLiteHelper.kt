package com.mahidol.snakeclassification.Database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class MySQLiteHelper(context: Context) : SQLiteOpenHelper(context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {

    override fun onCreate(database: SQLiteDatabase) {
        database.execSQL(CREATE_TABLE_HISTORY)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.w(
            MySQLiteHelper::class.java!!.name,
            "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_HISTORY")
        onCreate(db)
    }

    companion object {

        val TABLE_HISTORY = "history" //ชื่อตาราง
        val COLUMN_ID = "_id" //field ในตาราง
        val COLUMN_IMAGE = "image" //field ในตาราง
        val COLUMN_TIMESTAMP = "timestamp"
        val COLUMN_RANK1 = "rank1"
        val COLUMN_RANK2 = "rank2"
        val COLUMN_RANK3 = "rank3"
        val COLUMN_VALUE1 = "value1"
        val COLUMN_VALUE2 = "value2"
        val COLUMN_VALUE3 = "value3"
        val COLUMN_SPECIES = "species"

        private val DATABASE_NAME = "history.db"
        private val DATABASE_VERSION = 1

        // Database creation sql statement
        // สร้าง table

        private val CREATE_TABLE_HISTORY = ("create table "
                + TABLE_HISTORY + "(" + COLUMN_ID
                + " integer primary key autoincrement, " + COLUMN_IMAGE
                + " text not null, "+ COLUMN_SPECIES +" text not null, "+ COLUMN_TIMESTAMP+" text not null);")
    }

}