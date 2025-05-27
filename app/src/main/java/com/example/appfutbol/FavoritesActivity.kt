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
    private lateinit var adapter: MatchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_list)

        title = "Favoritos"

        recyclerView = findViewById(R.id.recycler_matches)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val db = DBHelper(this)
        favoriteList.addAll(db.getFavorites())

        adapter = MatchAdapter(favoriteList, this) { match, isFavorite ->
            if (!isFavorite) {
                // Cuando quitas favorito, eliminar partido de la lista y refrescar
                favoriteList.remove(match)
                adapter.notifyDataSetChanged()
            }
        }

        recyclerView.adapter = adapter
    }
}
