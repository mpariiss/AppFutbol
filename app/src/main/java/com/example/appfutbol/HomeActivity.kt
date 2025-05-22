package com.example.appfutbol


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.appfutbol.model.MatchListActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        findViewById<Button>(R.id.btn_matches).setOnClickListener {
            startActivity(Intent(this, MatchListActivity::class.java))
        }

        findViewById<Button>(R.id.btn_favorites).setOnClickListener {
            startActivity(Intent(this, FavoritesActivity::class.java))
        }

        findViewById<Button>(R.id.btn_logout).setOnClickListener {
            getSharedPreferences("user_prefs", MODE_PRIVATE)
                .edit().clear().apply()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
