package com.example.appfutbol


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appfutbol.adapter.MatchAdapter
import com.example.appfutbol.db.DBHelper
import com.example.appfutbol.model.Match

class FavoritesActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private val favoriteList = mutableListOf<Match>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_list)

        title = "Favoritos"

        recyclerView = findViewById(R.id.recycler_matches)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Obtener los partidos guardados como favoritos
        val db = DBHelper(this)
        favoriteList.addAll(db.getFavorites())  // Recuperamos los partidos favoritos de la base de datos SQLite
        recyclerView.adapter = MatchAdapter(favoriteList, this)
    }
}
