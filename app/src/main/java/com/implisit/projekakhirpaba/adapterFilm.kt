package com.implisit.projekakhirpaba

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class adapterFilm (private val listFilm: MutableList<film>):RecyclerView.Adapter<adapterFilm.ListViewHolder>()
{

   inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _judul = itemView.findViewById<TextView>(R.id.judulFilm)
        var _rating = itemView.findViewById<TextView>(R.id.ratingFilm)
        var _gambar = itemView.findViewById<ImageView>(R.id.gambarFilm)
        var _background = itemView.findViewById<androidx.cardview.widget.CardView>(R.id.background)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): adapterFilm.ListViewHolder {
       val view:View = LayoutInflater.from(parent.context).inflate(R.layout.film,parent,false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: adapterFilm.ListViewHolder, position: Int) {
    val film = listFilm[position]
        holder._judul.text = film.judul
        holder._rating.text = film.rating
        val resourceId = holder.itemView.context.resources.getIdentifier(
            film.gambar,
            "drawable",
            holder.itemView.context.packageName
        )
        if (resourceId != 0) {
            holder._gambar.setImageResource(resourceId)
        } else {
            holder._gambar.setImageResource(R.drawable.error)
        }

        holder._background.setOnClickListener{
            val sharedPreferences: SharedPreferences = holder.itemView.context.getSharedPreferences("UserSession", Context.MODE_PRIVATE)
            val userPhone = sharedPreferences.getString("userPhone", "")
            val intent = Intent(holder.itemView.context, detailFilm::class.java)
            intent.putExtra("judul", film.judul)
            intent.putExtra("rating", film.rating)
            intent.putExtra("gambar", film.gambar)
            intent.putExtra("deskripsi", film.deskripsi)
            intent.putExtra("tahun", film.tahun)
            intent.putExtra("genre", film.genre)
            intent.putExtra("userPhone", userPhone)

            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listFilm.size
    }
}