package com.example.infopulse.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DBHelper @Inject constructor(applicationContext: Context) : SQLiteOpenHelper(applicationContext, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "Saved.db"
        const val DATABASE_VERSION = 1
    }

    object SourcesEntry : BaseColumns {
        const val TABLE_NAME = "sources"
        const val COLUMN_SOURCE_ID = "source_id"
        const val COLUMN_SOURCE_NAME = "source_name"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_URL = "url"
        const val COLUMN_CATEGORY = "category"
        const val COLUMN_LANGUAGE = "language"
        const val COLUMN_COUNTRY = "country"
    }

    object NewsEntry : BaseColumns {
        const val TABLE_NAME = "news"
        const val COLUMN_SOURCE_ID = "source_id"
        const val COLUMN_AUTHOR = "author"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_URL = "url"
        const val COLUMN_URL_TO_IMAGE = "url_to_image"
        const val COLUMN_PUBLISHED_AT = "published_at"
        const val COLUMN_CONTENT = "content"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Create sources table
        val createSourcesTable = """
            CREATE TABLE ${SourcesEntry.TABLE_NAME} (
                ${BaseColumns._ID} INTEGER PRIMARY KEY,
                ${SourcesEntry.COLUMN_SOURCE_ID} TEXT,
                ${SourcesEntry.COLUMN_SOURCE_NAME} TEXT,
                ${SourcesEntry.COLUMN_DESCRIPTION} TEXT,
                ${SourcesEntry.COLUMN_URL} TEXT,
                ${SourcesEntry.COLUMN_CATEGORY} TEXT,
                ${SourcesEntry.COLUMN_LANGUAGE} TEXT,
                ${SourcesEntry.COLUMN_COUNTRY} TEXT
            )
        """.trimIndent()

        // Create news table
        val createNewsTable = """
            CREATE TABLE ${NewsEntry.TABLE_NAME} (
                ${BaseColumns._ID} INTEGER PRIMARY KEY,
                ${NewsEntry.COLUMN_SOURCE_ID} TEXT,
                ${NewsEntry.COLUMN_AUTHOR} TEXT,
                ${NewsEntry.COLUMN_TITLE} TEXT,
                ${NewsEntry.COLUMN_DESCRIPTION} TEXT,
                ${NewsEntry.COLUMN_URL} TEXT,
                ${NewsEntry.COLUMN_URL_TO_IMAGE} TEXT,
                ${NewsEntry.COLUMN_PUBLISHED_AT} TEXT,
                ${NewsEntry.COLUMN_CONTENT} TEXT
            )
        """.trimIndent()

        db.execSQL(createSourcesTable)
        db.execSQL(createNewsTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }
}