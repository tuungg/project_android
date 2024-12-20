package com.implisit.projekakhirpaba

import android.content.Intent
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

class register : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inisialisasi komponen dari layout
        val etName = findViewById<TextInputEditText>(R.id.etName)
        val etPhone = findViewById<TextInputEditText>(R.id.etPhone)
        val etPassword = findViewById<TextInputEditText>(R.id.etPassword)
        val btnRegister = findViewById<AppCompatButton>(R.id.btnRegister)
        val btnkeLogin = findViewById<TextView>(R.id.btnkeLogin)

        // Tombol register
        btnRegister.setOnClickListener {
            val name = etName.text.toString()
            val phone = etPhone.text.toString()
            val password = etPassword.text.toString()

            if (name.isNotEmpty() && phone.isNotEmpty() && password.isNotEmpty()) {
                registerUser(name, phone, password)
            } else {
                Toast.makeText(this, "Isi semua kolom!", Toast.LENGTH_SHORT).show()
            }
        }
        btnkeLogin.setOnClickListener {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }
    }

    private fun registerUser(name: String, phone: String, password: String) {
        val userData = hashMapOf(
            "name" to name,
            "phone" to phone,
            "password" to password // Simpan password plaintext hanya untuk testing
        )

        // Simpan data pengguna ke Firestore
        db.collection("Users").document(phone).set(userData)
            .addOnSuccessListener {
                // Membuat subkoleksi "Tickets" untuk pengguna
                Toast.makeText(this, "Registrasi berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
                finish() // Kembali ke halaman login

            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Registrasi gagal: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }


    }
