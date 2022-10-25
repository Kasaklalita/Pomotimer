package com.kasaklalita.pomotimer.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.kasaklalita.pomotimer.models.QuoteModel
import kotlin.random.Random

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "PomotimerDatabase"
        private const val DATABASE_VERSION = 1
        private const val TABLE_QUOTE = "QuotesTable"

        // All the Columns names
        private const val KEY_ID = "_id"
        private const val KEY_QUOTE = "quote"
        private const val KEY_AUTHOR = "author"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_QUOTE_TABLE = ("CREATE TABLE " + TABLE_QUOTE + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_QUOTE + " TEXT,"
                + KEY_AUTHOR + " TEXT)")
        db?.execSQL(CREATE_QUOTE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_QUOTE")
        onCreate(db)
    }

    fun addQuote(quote: QuoteModel): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_QUOTE, quote.quote)
        contentValues.put(KEY_AUTHOR, quote.author)

        //Inserting a row
        val result = db.insert(TABLE_QUOTE, null, contentValues)
        db.close()
        return result
    }

    @SuppressLint("Range")
    fun getQuotesList(): ArrayList<QuoteModel> {
        val quotesList = ArrayList<QuoteModel>()
        val selectQuery = "SELECT * FROM $TABLE_QUOTE"
        val db = this.readableDatabase

        try {
            val cursor: Cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    val quote = QuoteModel(
                        cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                        cursor.getString(cursor.getColumnIndex(KEY_QUOTE)),
                        cursor.getString(cursor.getColumnIndex(KEY_ID))
                    )
                    quotesList.add(quote)
                } while (cursor.moveToNext())
            }
            cursor.close()
        } catch (e: SQLException) {
            db.execSQL(selectQuery)
            return ArrayList<QuoteModel>()
        }
        return quotesList
    }

    fun getRandomQuote(): QuoteModel {
        val quotesList = getQuotesList()
        if (quotesList.isEmpty()) {
            val quote = QuoteModel(0, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum pharetra scelerisque nibh id dignissim. Proin rhoncus fermentum nibh. Sed tincidunt nibh id ligula congue viverra.", "Veniamin Polienko")
            return quote
        } else {
            val result = quotesList.random()
            return result
        }
    }
}