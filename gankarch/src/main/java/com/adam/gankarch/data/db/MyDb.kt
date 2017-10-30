package com.adam.gankarch.data.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * 发现Room和Kotlin一起用的时候有bug，先使用这个
 * @author yu
 * Create on 2017/6/23.
 */
class MyDb(context: Context) : SQLiteOpenHelper(context, "gankDb", null, 1) {

    companion object {
        private val TABLE_NAME = "gankytable"
        private val KEY_GANK_ID = "gankid"
        private val KEY_URL = "url"
        private val KEY_DESC = "desc"
        private val KEY_TYPE = "type"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val sb = StringBuffer()
                .append("CREATE TABLE $TABLE_NAME (")
                .append("_id INTEGER PRIMARY KEY AUTOINCREMENT,")
                .append("$KEY_GANK_ID TEXT NOT NULL,")
                .append("$KEY_DESC TEXT NOT NULL,")
                .append("$KEY_TYPE TEXT NOT NULL,")
                .append("$KEY_URL TEXT NOT NULL")
                .append(")")
        db?.execSQL(sb.toString())
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun insert(entity: GankBean) {
        val values = ContentValues()
        values.put(KEY_GANK_ID, entity.id)
        values.put(KEY_URL, entity.url)
        values.put(KEY_DESC, entity.desc)
        values.put(KEY_TYPE, entity.type)

        val db = writableDatabase
        db.insert(TABLE_NAME, "", values)
        db.close()
    }

    fun delete(entity: GankBean) {
        val db = writableDatabase
        writableDatabase.delete(TABLE_NAME, "$KEY_GANK_ID=?", arrayOf(entity.id))
        db.close()
    }

    fun update(entity: GankBean) {
        val values = ContentValues()
        values.put(KEY_URL, entity.url)
        values.put(KEY_DESC, entity.desc)
        values.put(KEY_TYPE, entity.type)

        val db = writableDatabase
        db.update(TABLE_NAME, values, "$KEY_GANK_ID=?", arrayOf(entity.id))
        db.close()
    }

    fun queryById(gankid: String): GankBean? {

        val cursor = writableDatabase.query(TABLE_NAME, null,
                "$KEY_GANK_ID=?", arrayOf(gankid),
                null, null, null, null)

        var entity: GankBean? = null
        if (cursor.moveToFirst()) {
            entity = GankBean(cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4))
        }
        writableDatabase.close()
        cursor.close()
        return entity
    }

    fun query(offset: Int, size: Int): List<GankBean> {

        val cursor = writableDatabase.query(TABLE_NAME, null,
                null, null,
                null, null, "_id desc", "$offset,$size")

        val dataList = mutableListOf<GankBean>()
        if (cursor.moveToFirst()) {
            do {
                dataList.add(GankBean(cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4)))
            } while (cursor.moveToNext())
        }

        writableDatabase.close()
        cursor.close()

        return dataList
    }

}