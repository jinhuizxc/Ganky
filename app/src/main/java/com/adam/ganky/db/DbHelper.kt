package com.adam.ganky.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * @author yu
 * Create on 2017/6/23.
 */
class DbHelper(context: Context) : SQLiteOpenHelper(context, "gankDb", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}