package com.implisit.projekakhirpaba

import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Locale

class detailFilm : AppCompatActivity() {
    private lateinit var selectedDate: TextView
    private lateinit var selectedTime: TextView
    private lateinit var selectedTheaterName: String
    private lateinit var selectedTheaterAddress: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_film)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dateGet()

        val judul = intent.getStringExtra("judul")
        val rating = intent.getStringExtra("rating")
        val gambar = intent.getStringExtra("gambar")
        val deskripsi = intent.getStringExtra("deskripsi")
        val tahun = intent.getStringExtra("tahun")
        val genre = intent.getStringExtra("genre")
        val no_telpon = intent.getStringExtra("userPhone")

        val judulFilm = findViewById<TextView>(R.id.filmTitle)
        val posterFilm = findViewById<ImageView>(R.id.filmPoster)
        val deskripsiFilm = findViewById<TextView>(R.id.filmDeskripsi)
        val ratingFilm = findViewById<TextView>(R.id.filmRating)
        val tahunFilm = findViewById<TextView>(R.id.filmTahun)

        deskripsiFilm.text = deskripsi
        ratingFilm.text = rating
        tahunFilm.text = tahun

        judulFilm.text = judul
        posterFilm.setImageResource(resources.getIdentifier(gambar, "drawable", packageName))

        val dates = listOf(
            findViewById<TextView>(R.id.tglHariIni),
            findViewById<TextView>(R.id.tglBesok),
            findViewById<TextView>(R.id.tglLusa),
        )

        val times = mapOf(
            findViewById<TextView>(R.id.tempat1_waktu1) to Pair("AEON MALL JGC CGV", "AEON Cakung Mall Lt. 3 Ps. Modern Jakarta Garden City"),
            findViewById<TextView>(R.id.tempat1_waktu2) to Pair("AEON MALL JGC CGV", "AEON Cakung Mall Lt. 3 Ps. Modern Jakarta Garden City"),
            findViewById<TextView>(R.id.tempat1_waktu3) to Pair("AEON MALL JGC CGV", "AEON Cakung Mall Lt. 3 Ps. Modern Jakarta Garden City"),
            findViewById<TextView>(R.id.tempat1_waktu4) to Pair("AEON MALL JGC CGV", "AEON Cakung Mall Lt. 3 Ps. Modern Jakarta Garden City"),
            findViewById<TextView>(R.id.tempat1_waktu5) to Pair("AEON MALL JGC CGV", "AEON Cakung Mall Lt. 3 Ps. Modern Jakarta Garden City"),
            findViewById<TextView>(R.id.tempat2_waktu1) to Pair("Cinemaxx Lippo Plaza", "Lippo Plaza Kramat Jati Lt. 2, Jakarta Timur"),
            findViewById<TextView>(R.id.tempat2_waktu2) to Pair("Cinemaxx Lippo Plaza", "Lippo Plaza Kramat Jati Lt. 2, Jakarta Timur"),
            findViewById<TextView>(R.id.tempat2_waktu3) to Pair("Cinemaxx Lippo Plaza", "Lippo Plaza Kramat Jati Lt. 2, Jakarta Timur"),
            findViewById<TextView>(R.id.tempat2_waktu4) to Pair("Cinemaxx Lippo Plaza", " Lippo Plaza Kramat Jati Lt. 2, Jakarta Timur"),
            findViewById<TextView>(R.id.tempat2_waktu5) to Pair("Cinemaxx Lippo Plaza", "Lippo Plaza Kramat Jati Lt. 2, Jakarta Timur"),
            findViewById<TextView>(R.id.tempat3_waktu1) to Pair("XXI Plaza Senayan", "Plaza Senayan Lt. 5, Jakarta Selatan"),
            findViewById<TextView>(R.id.tempat3_waktu2) to Pair("XXI Plaza Senayan", "Plaza Senayan Lt. 5, Jakarta Selatan"),
            findViewById<TextView>(R.id.tempat3_waktu3) to Pair("XXI Plaza Senayan", "Plaza Senayan Lt. 5, Jakarta Selatan"),
            findViewById<TextView>(R.id.tempat3_waktu4) to Pair("XXI Plaza Senayan", "Plaza Senayan Lt. 5, Jakarta Selatan"),
            findViewById<TextView>(R.id.tempat3_waktu5) to Pair("XXI Plaza Senayan", "Plaza Senayan Lt. 5, Jakarta Selatan")
        )

        dates.forEach { date ->
            date.setOnClickListener {
                if (::selectedDate.isInitialized) {
                    selectedDate.setBackgroundResource(R.drawable.rounded_border)
                }
                selectedDate = date
                selectedDate.setBackgroundResource(R.drawable.rounded_background_gr)
            }
        }

        times.forEach { (time, theater) ->
            time.setOnClickListener {
                if (::selectedTime.isInitialized) {
                    selectedTime.setBackgroundResource(R.drawable.rounded_border)
                }
                selectedTime = time
                selectedTime.setBackgroundResource(R.drawable.rounded_background_gr)
                selectedTheaterName = theater.first
                selectedTheaterAddress = theater.second
            }
        }

        val kePilihKursi = findViewById<Button>(R.id.kePilihKursi)
        kePilihKursi.setOnClickListener {
            val intent = Intent(this, pilihKursi::class.java)
            intent.putExtra("judul", judul)
            intent.putExtra("rating", rating)
            intent.putExtra("gambar", gambar)
            intent.putExtra("deskripsi", deskripsi)
            intent.putExtra("tahun", tahun)
            intent.putExtra("genre", genre)
            intent.putExtra("selectedDate", selectedDate.text.toString())
            intent.putExtra("selectedTime", selectedTime.text.toString())
            intent.putExtra("theaterName", selectedTheaterName)
            intent.putExtra("theaterAddress", selectedTheaterAddress)
            intent.putExtra("userPhone", no_telpon)
            startActivity(intent)
        }
    }

    private fun dateGet() {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

        val today = dateFormat.format(calendar.time)
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        val tomorrow = dateFormat.format(calendar.time)
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        val dayAfterTomorrow = dateFormat.format(calendar.time)

        val dates = listOf(
            findViewById<TextView>(R.id.tglHariIni),
            findViewById<TextView>(R.id.tglBesok),
            findViewById<TextView>(R.id.tglLusa)
        )

        dates[0].text = today
        dates[1].text = tomorrow
        dates[2].text = dayAfterTomorrow
    }
}