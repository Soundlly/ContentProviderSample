package io.bitsound.contentprovidersample

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import android.provider.BaseColumns
import android.util.Log
import io.bitsound.contentprovidersample.models.SwitchModel
import io.bitsound.contentprovidersample.tables.SwitchTable

class SampleContentProvider : ContentProvider() {

    private lateinit var helper: SampleDatabase

    companion object {
        private val TAG = SampleContentProvider::class.java.simpleName
        const val SWITCH_DIR = 0
        const val SWITCH_ITEM = 1

        private val uriMatcher : UriMatcher by lazy {
            UriMatcher(UriMatcher.NO_MATCH).apply {
                addURI(BuildConfig.LOCAL_PROVIDER_AUTHORITY, SwitchTable.NAME, SWITCH_DIR)
                addURI(BuildConfig.LOCAL_PROVIDER_AUTHORITY, "${SwitchTable.NAME}/#", SWITCH_ITEM)
            }
        }
    }

    override fun onCreate(): Boolean {
        helper = SampleDatabase(context)
        return true
    }

    override fun insert(uri: Uri?, values: ContentValues?): Uri {
        Log.d(TAG, "insert(uri=$uri, values=$values)")
        when(uriMatcher.match(uri)) {
            SWITCH_DIR -> {
                val id = helper.writableDatabase.insertOrThrow(
                    SwitchTable.NAME,
                    null,
                    values
                )
                return Uri.parse("content://${BuildConfig.LOCAL_PROVIDER_AUTHORITY}/${SwitchTable.NAME}/$id")
            }
            SWITCH_ITEM -> throw IllegalArgumentException("Invalid URI : $uri")
            else -> throw IllegalArgumentException("Invalid URI : $uri")
        }
    }

    override fun query(uri: Uri?, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor {
        Log.d(TAG, "query(uri=$uri, projection=$projection, selection=$selection, selectionArgs=$selectionArgs, sortOrder=$sortOrder)")
        SQLiteQueryBuilder().let { builder ->
            builder.tables = SwitchTable.NAME

            return if (uri == null) throw IllegalArgumentException("Invalid URI : $uri")
            else when (uriMatcher.match(uri)) {
                SWITCH_DIR -> builder.query(helper.writableDatabase, projection, selection, selectionArgs, null, null, sortOrder)
                SWITCH_ITEM -> {
                    try {
                        val id = uri.pathSegments[1]
                        builder.appendWhere("${BaseColumns._ID}=$id")
                        builder.query(helper.writableDatabase, projection, selection, selectionArgs, null, null, sortOrder)
                    } catch (e: Exception) {
                        throw IllegalArgumentException("Invalid URI : $uri")
                    }
                }
                else -> throw IllegalArgumentException("Invalid URI : $uri")
            }
        }
    }

    override fun update(uri: Uri?, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        Log.d(TAG, "update(uri=$uri, values=$values, selection=$selection, selectionArgs=$selectionArgs)")
        if (uri == null) return 0
        else return when (uriMatcher.match(uri)) {
            SWITCH_DIR -> return helper.writableDatabase.update(SwitchTable.NAME, values, selection, selectionArgs)
            SWITCH_ITEM -> {
                try {
                    val id = uri.pathSegments[1]
                    helper.writableDatabase.update(
                        SwitchTable.NAME,
                        values,
                        "${BaseColumns._ID}=$id${if (selection.isNullOrBlank()) "" else " AND ($selection)"}",
                        selectionArgs
                    )
                } catch (e: Exception) {
                    throw IllegalArgumentException("Invalid URI : $uri")
                }
            }
            else -> throw IllegalArgumentException("Invalid URI : $uri")
        }
    }

    override fun delete(uri: Uri?, selection: String?, selectionArgs: Array<out String>?): Int {
        Log.d(TAG, "delete(uri=$uri, selection=$selection, selectionArgs=$selectionArgs)")
        if (uri == null) return 0
        else return when (uriMatcher.match(uri)) {
            SWITCH_DIR -> return 0
            SWITCH_ITEM -> {
                try {
                    val id = uri.pathSegments[1]
                    helper.writableDatabase.delete(
                        SwitchTable.NAME,
                        "${BaseColumns._ID}=$id${if (selection.isNullOrBlank()) "" else " AND ($selection)"}",
                        selectionArgs
                    )
                } catch (e: Exception) {
                    throw IllegalArgumentException("Invalid URI : $uri")
                }
            }
            else -> throw IllegalArgumentException("Invalid URI : $uri")
        }
    }

    override fun getType(uri: Uri?): String {
        return when(uriMatcher.match(uri)) {
            SWITCH_DIR -> "vnd.android.cursor.dir/vnd.${BuildConfig.LOCAL_PROVIDER_AUTHORITY}.country"
            SWITCH_ITEM -> "vnd.android.cursor.item/vnd.${BuildConfig.LOCAL_PROVIDER_AUTHORITY}.country"
            else -> throw IllegalArgumentException("Invalid URI : $uri")
        }
    }
}
