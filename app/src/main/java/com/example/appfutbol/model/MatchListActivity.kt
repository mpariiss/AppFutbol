package com.example.appfutbol.model



import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appfutbol.R
import com.example.appfutbol.adapter.MatchAdapter
import com.example.appfutbol.api.ApiClient
import kotlinx.coroutines.*

class MatchListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_list)

        recyclerView = findViewById(R.id.recycler_matches)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MatchAdapter(emptyList(), this)

        loadMatchesFromApi()
    }

    private fun loadMatchesFromApi() {
        CoroutineScope(Dispatchers.IO).launch {
            try {

                  val response = ApiClient.create().getMatches()

                if (response.isSuccessful) {
                    val matches = response.body()?.matches ?: emptyList()
                    Log.d("API", "Se recibieron ${matches.size} partidos")

                    withContext(Dispatchers.Main) {
                        if (matches.isEmpty()) {
                            Toast.makeText(
                                this@MatchListActivity,
                                "No hay partidos disponibles",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            recyclerView.adapter = MatchAdapter(matches, this@MatchListActivity)
                        }
                    }
                } else {
                    Log.e("API", "Error: código ${response.code()}")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@MatchListActivity,
                            "Error de servidor: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                Log.e("API", "Excepción: ${e.message}")
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@MatchListActivity,
                        "No se pudo conectar con la API",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}
