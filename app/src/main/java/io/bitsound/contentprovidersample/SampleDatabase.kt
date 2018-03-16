package io.bitsound.contentprovidersample

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import io.bitsound.contentprovidersample.tables.SwitchTable

class SampleDatabase(private val context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {
    companion object {
        const val DATABASE_NAME = "sample.db"
        const val DATABASE_VERSION = 1
    }

    override fun onCreate(database: SQLiteDatabase?) = database?.let{ db ->
        SwitchTable.create(db)
    } ?: Unit

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun destroy() {
        context.deleteDatabase(DATABASE_NAME)
    }
}
