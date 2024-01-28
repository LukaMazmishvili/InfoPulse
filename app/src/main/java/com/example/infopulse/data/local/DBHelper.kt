package com.example.infopulse.data.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.example.infopulse.data.remote.model.Article
import com.example.infopulse.data.remote.model.SourcesModelDto
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DBHelper @Inject constructor(applicationContext: Context) :
    SQLiteOpenHelper(applicationContext, DATABASE_NAME, null, DATABASE_VERSION) {

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

    fun insertSource(source: SourcesModelDto.Source) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(SourcesEntry.COLUMN_SOURCE_ID, source.id)
            put(SourcesEntry.COLUMN_SOURCE_NAME, source.name)
            put(SourcesEntry.COLUMN_DESCRIPTION, source.description)
            put(SourcesEntry.COLUMN_URL, source.url)
            put(SourcesEntry.COLUMN_CATEGORY, source.category)
            put(SourcesEntry.COLUMN_LANGUAGE, source.language)
            put(SourcesEntry.COLUMN_COUNTRY, source.country)
        }

        db.insertWithOnConflict(
            SourcesEntry.TABLE_NAME,
            null,
            values,
            SQLiteDatabase.CONFLICT_REPLACE
        )
        db.close()
    }

    fun insertNews(news: Article) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(NewsEntry.COLUMN_AUTHOR, news.author)
            put(NewsEntry.COLUMN_TITLE, news.title)
            put(NewsEntry.COLUMN_DESCRIPTION, news.description)
            put(NewsEntry.COLUMN_URL, news.url)
            put(NewsEntry.COLUMN_URL_TO_IMAGE, news.urlToImage)
            put(NewsEntry.COLUMN_PUBLISHED_AT, news.publishedAt)
            put(NewsEntry.COLUMN_CONTENT, news.content)
        }

        db.insertWithOnConflict(NewsEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE)
        db.close()
    }

    fun getAllSources(): List<SourcesModelDto.Source> {
        val sourcesList = mutableListOf<SourcesModelDto.Source>()
        val db = readableDatabase
        val cursor = db.query(
            SourcesEntry.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        with(cursor) {
            while (moveToNext()) {
                val source = SourcesModelDto.Source(
                    getString(getColumnIndexOrThrow(SourcesEntry.COLUMN_SOURCE_ID)),
                    getString(getColumnIndexOrThrow(SourcesEntry.COLUMN_SOURCE_NAME)),
                    getString(getColumnIndexOrThrow(SourcesEntry.COLUMN_DESCRIPTION)),
                    getString(getColumnIndexOrThrow(SourcesEntry.COLUMN_URL)),
                    getString(getColumnIndexOrThrow(SourcesEntry.COLUMN_CATEGORY)),
                    getString(getColumnIndexOrThrow(SourcesEntry.COLUMN_LANGUAGE)),
                    getString(getColumnIndexOrThrow(SourcesEntry.COLUMN_COUNTRY))
                )
                sourcesList.add(source)
            }
        }

        cursor.close()
        return sourcesList
    }

    fun getAllNews(): List<Article> {
        val newsList = mutableListOf<Article>()
        val db = readableDatabase
        val cursor = db.query(
            NewsEntry.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        with(cursor) {
            while (moveToNext()) {
                val news = Article(
                    null,
                    getString(getColumnIndexOrThrow(NewsEntry.COLUMN_AUTHOR)),
                    getString(getColumnIndexOrThrow(NewsEntry.COLUMN_TITLE)),
                    getString(getColumnIndexOrThrow(NewsEntry.COLUMN_DESCRIPTION)),
                    getString(getColumnIndexOrThrow(NewsEntry.COLUMN_URL)),
                    getString(getColumnIndexOrThrow(NewsEntry.COLUMN_URL_TO_IMAGE)),
                    getString(getColumnIndexOrThrow(NewsEntry.COLUMN_PUBLISHED_AT)),
                    getString(getColumnIndexOrThrow(NewsEntry.COLUMN_CONTENT))
                )
                newsList.add(news)
            }
        }

        cursor.close()
        return newsList
    }

    private fun deleteItem(tableName: String, columnName: String, columnValue: String) {
        val db = writableDatabase
        val whereClause = "$columnName = ?"
        val whereArgs = arrayOf(columnValue)

        db.delete(tableName, whereClause, whereArgs)

        db.close()
    }

    fun deleteSource(source: SourcesModelDto.Source) {
        deleteItem(SourcesEntry.TABLE_NAME, SourcesEntry.COLUMN_SOURCE_ID, source.id)
    }

    fun deleteNews(news: Article) {
        deleteItem(NewsEntry.TABLE_NAME, NewsEntry.COLUMN_TITLE, news.title)
    }
}