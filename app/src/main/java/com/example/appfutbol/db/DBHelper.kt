package com.example.appfutbol.db


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.appfutbol.model.Match

import com.example.appfutbol.model.Team

class DBHelper(context: Context) : SQLiteOpenHelper(context, "FootballDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE favorites (
                id INTEGER PRIMARY KEY,
                home TEXT,
                away TEXT,
                date TEXT
            )
        """
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS favorites")
        onCreate(db)
    }

    //
    fun addFavorite(match: Match) {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put("home", match.homeTeam.name)
            put("away", match.awayTeam.name)
            put("date", match.utcDate)
        }
        db.insert("favorites", null, contentValues)
    }

    // Obtener todos los partidos guardados como favoritos
    fun getFavorites(): List<Match> {
        val list = mutableListOf<Match>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM favorites", null)

        if (cursor.moveToFirst()) {
            do {
                list.add(
                    Match(
                        id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        utcDate = cursor.getString(cursor.getColumnIndexOrThrow("date")),
                        homeTeam = Team(cursor.getString(cursor.getColumnIndexOrThrow("home"))),
                        awayTeam = Team(cursor.getString(cursor.getColumnIndexOrThrow("away")))
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    fun removeFavorite(matchId: Int) {
        val db = writableDatabase
        db.delete("favorites", "id = ?", arrayOf(matchId.toString()))
    }

}
