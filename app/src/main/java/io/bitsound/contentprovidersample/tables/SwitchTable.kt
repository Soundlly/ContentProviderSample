package io.bitsound.contentprovidersample.tables

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import io.bitsound.contentprovidersample.BuildConfig

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

    object ContentType {
        const val DIR = "vnd.android.cursor.dir/vnd.${BuildConfig.PROVIDER_AUTHORITY}.country"
        const val ITEM = "vnd.android.cursor.item/vnd.${BuildConfig.PROVIDER_AUTHORITY}.country"
    }

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
