package io.bitsound.contentprovidersample.tables

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

object SwitchTable {
    const val NAME = "switches"

    fun create(db: SQLiteDatabase) = db.execSQL(
        """
        CREATE TABLE IF NOT EXISTS ${SwitchTable.NAME} (
            ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${Columns.KEY} TEXT NOT NULL,
            ${Columns.VALUE} INTEGER DEFAULT 0
        )
        """.trimIndent()
    )

    fun drop(db: SQLiteDatabase) = db.execSQL(
        """
        DROP TABLE IF EXISTS ${SwitchTable.NAME}
        """
    )

    object Columns {
        const val ID = BaseColumns._ID
        const val KEY = "key"
        const val VALUE = "value"
        val all: Array<String> = arrayOf(
            Columns.ID,
            Columns.KEY,
            Columns.VALUE
        )
    }
}
