package com.example.ecimdb

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.ecimdb.model.MovieModel

class SQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "movie.db"
        private const val TB_MOVIE = "tb_movie"
        private const val ID = "id"
        private const val NAME = "name"
        private const val NOTE = "note"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE " + TB_MOVIE + "(" + ID + " INTEGER PRIMARY KEY," +
                NAME + " TEXT," + NOTE + " TEXT" + ")")
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TB_MOVIE")
        onCreate(db)
    }

    fun insertMovie(mvn: MovieModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, mvn.id)
        contentValues.put(NAME, mvn.name)
        contentValues.put(NOTE, mvn.note)

        val success = db.insert(TB_MOVIE, null, contentValues)
        db.close()
        return success
    }

    @SuppressLint("Range")
    fun getAllMovie(): ArrayList<MovieModel> {
        val mvnList: ArrayList<MovieModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TB_MOVIE"
        val db = this.readableDatabase

        val result = db.rawQuery(selectQuery, null)

        if (result.moveToFirst()) {
            do {
                val movies = MovieModel()
                movies.id = result.getInt(result.getColumnIndex(ID))
                movies.name = result.getString(result.getColumnIndex(NAME))
                movies.note = result.getString(result.getColumnIndex(NOTE))
                mvnList.add(movies)
            } while (result.moveToNext())
        }
        return mvnList
    }

    fun updateMovie(mvn: MovieModel): Int {
        val db = this.writableDatabase
        val contextValues = ContentValues()
        contextValues.put(ID, mvn.id)
        contextValues.put(NAME, mvn.name)
        contextValues.put(NOTE, mvn.note)

        val success = db.update(TB_MOVIE, contextValues,
            "id=" + mvn.id,
            null)
        db.close()
        return success
    }

    fun deleteMovie(id: Int): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, id)

        val success = db.delete(TB_MOVIE, "id=$id", null)
        db.close()
        return success
    }
}