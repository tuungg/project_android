package com.implisit.projekakhirpaba

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore

class login : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inisialisasi SharedPreferences
        sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)
        val etPhone = findViewById<TextInputEditText>(R.id.etPhone)
        val etPassword = findViewById<TextInputEditText>(R.id.etPassword)
        val btnLogin = findViewById<AppCompatButton>(R.id.btnLogin)
        val btnCreateAkun = findViewById<TextView>(R.id.btnkeRegister)

        // Tombol Login
        btnLogin.setOnClickListener {
            val phone = etPhone.text.toString()
            val password = etPassword.text.toString()

            if (phone.isNotEmpty() && password.isNotEmpty()) {
                loginUser(phone, password)
            } else {
                Toast.makeText(this, "Isi semua kolom!", Toast.LENGTH_SHORT).show()
            }
        }

        // Tombol untuk pindah ke halaman registrasi
        btnCreateAkun.setOnClickListener {
            val intent = Intent(this, register::class.java)
            startActivity(intent)
        }


    }

    private fun loginUser(phone: String, password: String) {
        db.collection("Users").document(phone).get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val dbPassword = document.getString("password")
                    if (dbPassword == password) {
                        Toast.makeText(this, "Login berhasil!", Toast.LENGTH_SHORT).show()

                        // Simpan status login di SharedPreferences
                        sharedPreferences.edit().apply {
                            putBoolean("isLoggedIn", true)
                            putString("userPhone", phone) // Simpan nomor telepon user
                            apply()
                        }

                        navigateToMainScreen()
                    } else {
                        Toast.makeText(this, "Password salah!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "User tidak ditemukan!", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun navigateToMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}