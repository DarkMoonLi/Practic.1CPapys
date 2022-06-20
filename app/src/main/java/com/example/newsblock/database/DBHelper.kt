package com.example.newsblock.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.newsblock.models.NewX

class FeedReaderDbHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    val COL_ID = FeedReaderContract.FeedEntry.ID
    val COL_NEWS_TITLE = FeedReaderContract.FeedEntry.NEWS_TITLE
    val COL_NEWS_ANNOTATION = FeedReaderContract.FeedEntry.NEWS_ANNOTATION
    val COL_NEWS_ID = FeedReaderContract.FeedEntry.NEWS_ID
    val COL_NEWS_ID_RESOURCE = FeedReaderContract.FeedEntry.NEWS_ID_RESOURCE
    val COL_IMG = FeedReaderContract.FeedEntry.NEWS_IMG
    val COL_LOCAL_IMG = FeedReaderContract.FeedEntry.NEWS_LOCAL_IMG
    val COL_NEWS_MOBILE_URL = FeedReaderContract.FeedEntry.NEWS_MOBILE_URL
    val COL_NEWS_DATE = FeedReaderContract.FeedEntry.NEWS_DATE
    val COL_NEWS_DATE_UTS = FeedReaderContract.FeedEntry.NEWS_DATE_UTS
    val COL_NEWS_TYPE = FeedReaderContract.FeedEntry.NEWS_TYPE

    fun insertItem(news: NewX)
    {
        val db = this.readableDatabase
        Log.v("INPUT", "input")

        val values = ContentValues().apply {
            put(FeedReaderContract.FeedEntry.NEWS_TITLE, news.title)
            put(FeedReaderContract.FeedEntry.NEWS_ANNOTATION, news.annotation)
            put(FeedReaderContract.FeedEntry.NEWS_ID, news.id)
            put(FeedReaderContract.FeedEntry.NEWS_ID_RESOURCE, news.id_resource)
            put(FeedReaderContract.FeedEntry.NEWS_IMG, news.img)
            put(FeedReaderContract.FeedEntry.NEWS_LOCAL_IMG, news.local_img)
            put(FeedReaderContract.FeedEntry.NEWS_MOBILE_URL, news.mobile_url)
            put(FeedReaderContract.FeedEntry.NEWS_DATE, news.news_date)
            put(FeedReaderContract.FeedEntry.NEWS_DATE_UTS, news.news_date_uts)
            put(FeedReaderContract.FeedEntry.NEWS_TYPE, news.type)
        }

        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values)
        db.close()
    }

    fun updateItem(news: NewX) {
        val db = this.readableDatabase
        val selection = "${FeedReaderContract.FeedEntry.NEWS_ID} LIKE ${news.id}"
        val values = ContentValues().apply {

            put(FeedReaderContract.FeedEntry.NEWS_TITLE, news.title)
            put(FeedReaderContract.FeedEntry.NEWS_ANNOTATION, news.annotation)
            put(FeedReaderContract.FeedEntry.NEWS_ID, news.id)
            put(FeedReaderContract.FeedEntry.NEWS_ID_RESOURCE, news.id_resource)
            put(FeedReaderContract.FeedEntry.NEWS_IMG, news.img)
            put(FeedReaderContract.FeedEntry.NEWS_LOCAL_IMG, news.local_img)
            put(FeedReaderContract.FeedEntry.NEWS_MOBILE_URL, news.mobile_url)
            put(FeedReaderContract.FeedEntry.NEWS_DATE, news.news_date)
            put(FeedReaderContract.FeedEntry.NEWS_DATE_UTS, news.news_date_uts)
            put(FeedReaderContract.FeedEntry.NEWS_TYPE, news.type)

        }
        db?.update(FeedReaderContract.FeedEntry.TABLE_NAME, values, selection, null)
        db.close()
    }

    fun getOneNews(id: String): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT news_id FROM ${FeedReaderContract.FeedEntry.TABLE_NAME} WHERE news_id=${id}", null)
    }

    fun getSelectedNews(id: Int): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM ${FeedReaderContract.FeedEntry.TABLE_NAME} WHERE _id=${id}", null)
    }

    fun getSearchNews(query: String): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM ${FeedReaderContract.FeedEntry.TABLE_NAME} " +
                "WHERE INSTR(lower(annotation), '$query') OR INSTR(lower(title), '$query')", null)
    }

    fun getNews(): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM ${FeedReaderContract.FeedEntry.TABLE_NAME}", null)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "news.db"

        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS ${FeedReaderContract.FeedEntry.TABLE_NAME} (" +
                    "${FeedReaderContract.FeedEntry.ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${FeedReaderContract.FeedEntry.NEWS_TITLE} TEXT," +
                    "${FeedReaderContract.FeedEntry.NEWS_ANNOTATION} TEXT," +
                    "${FeedReaderContract.FeedEntry.NEWS_ID} TEXT," +
                    "${FeedReaderContract.FeedEntry.NEWS_ID_RESOURCE} TEXT," +
                    "${FeedReaderContract.FeedEntry.NEWS_IMG} TEXT," +
                    "${FeedReaderContract.FeedEntry.NEWS_LOCAL_IMG} TEXT," +
                    "${FeedReaderContract.FeedEntry.NEWS_MOBILE_URL} TEXT," +
                    "${FeedReaderContract.FeedEntry.NEWS_DATE} TEXT," +
                    "${FeedReaderContract.FeedEntry.NEWS_DATE_UTS} TEXT," +
                    "${FeedReaderContract.FeedEntry.NEWS_TYPE} TEXT)"

        private const  val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${FeedReaderContract.FeedEntry.TABLE_NAME}"
    }
}