package io.bitsound.contentprovidersample.models

import android.content.ContentValues
import android.database.Cursor

data class SwitchModel(
    val key: String,
    val value: Boolean
) {
    fun toContentValues() : ContentValues = ContentValues().apply {
        put(SwitchModel.Columns.KEY, key)
        put(SwitchModel.Columns.VALUE, if (value) 1 else 0)
    }

    object Columns {
        const val KEY = "key"
        const val VALUE = "value"
        val all: Array<String> = arrayOf(
            SwitchModel.Columns.KEY,
            SwitchModel.Columns.VALUE
        )
    }
}

fun Cursor.toSwitchModel() : SwitchModel = SwitchModel(
    getString(SwitchModel.Columns.all.indexOf(SwitchModel.Columns.KEY)),
    getInt(SwitchModel.Columns.all.indexOf(SwitchModel.Columns.VALUE)) == 1
)
