package com.implisit.projekakhirpaba

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)

        // Cek status login
        if (!isLoggedIn()) {
            navigateToLogin()
            return
        }

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val keHome = findViewById<ImageView>(R.id.home)
        val keHistory = findViewById<ImageView>(R.id.history)
        val logoutButton = findViewById<ImageView>(R.id.logout)

        logoutButton.setOnClickListener {
            sharedPreferences.edit().apply {
                putBoolean("isLoggedIn", false)
                apply()
            }
            val intent = Intent(this, login::class.java)
            startActivity(intent)
            finish()
        }

        val fragmentManager = supportFragmentManager
        val defaultFragment = home()

        fragmentManager.beginTransaction()
            .add(R.id.frameContainer, defaultFragment, home::class.java.simpleName)
            .commit()

        keHome.setOnClickListener {
            val fragmentHome = home()
            fragmentManager.beginTransaction()
                .replace(R.id.frameContainer, fragmentHome, home::class.java.simpleName)
                .commit()
        }

        keHistory.setOnClickListener {
            val fragmentHistory = history()
            fragmentManager.beginTransaction()
                .replace(R.id.frameContainer, fragmentHistory, history::class.java.simpleName)
                .commit()
        }
    }

    private fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    private fun navigateToLogin() {
        val intent = Intent(this, login::class.java)
        startActivity(intent)
        finish() // Tutup MainActivity agar tidak bisa kembali tanpa login
    }
}