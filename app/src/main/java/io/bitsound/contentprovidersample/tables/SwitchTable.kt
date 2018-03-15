package io.bitsound.contentprovidersample.tables

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import io.bitsound.contentprovidersample.models.SwitchModel

object SwitchTable {
    val NAME = SwitchModel::class.java.simpleName.toLowerCase()

    fun create(db: SQLiteDatabase)  = db.execSQL(
        """
        CREATE TABLE IF NOT EXISTS ${SwitchTable.NAME} (
            ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${SwitchModel.Columns.KEY} TEXT NOT NULL,
            ${SwitchModel.Columns.VALUE} INTEGER DEFAULT 0
        )
        """.trimIndent()
    )

    fun insert(db: SQLiteDatabase, switchModel: SwitchModel) = db.insert(
        SwitchTable.NAME,
        null,
        switchModel.toContentValues()
    )
}
