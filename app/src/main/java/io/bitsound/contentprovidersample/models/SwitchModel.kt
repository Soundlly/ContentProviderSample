package io.bitsound.contentprovidersample.models

import android.content.ContentValues
import android.database.Cursor
import io.bitsound.contentprovidersample.tables.SwitchTable

data class SwitchModel(
    var id: Long? = null,
    val key: String,
    val value: Boolean
) {
    fun toContentValues() : ContentValues = ContentValues().apply {
        if (id != null) put(SwitchTable.Columns.ID, id)
        put(SwitchTable.Columns.KEY, key)
        put(SwitchTable.Columns.VALUE, if (value) 1 else 0)
    }
}

fun Cursor.toSwitchModel() : SwitchModel = SwitchModel(
    getLong(SwitchTable.Columns.all.indexOf(SwitchTable.Columns.ID)),
    getString(SwitchTable.Columns.all.indexOf(SwitchTable.Columns.KEY)),
    getInt(SwitchTable.Columns.all.indexOf(SwitchTable.Columns.VALUE)) == 1
)
