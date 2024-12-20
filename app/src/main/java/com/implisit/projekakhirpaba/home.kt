package com.implisit.projekakhirpaba

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [home.newInstance] factory method to
 * create an instance of this fragment.
 */
class home : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    val db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val _rvFilm = view.findViewById<RecyclerView>(R.id.rvFilm)



        adapterFilm = adapterFilm(arFilm)

        _rvFilm.layoutManager = LinearLayoutManager(context)
        _rvFilm.adapter = adapterFilm
        readFilm(db)
        updateData(arFilm)

    }


    fun tambahFilm(
        db: FirebaseFirestore,
        judul: String,
        genre: String,
        tahun: String,
        rating: String,
        deskripsi: String,
        gambar: String
    ) {
        val filmBaru = film(judul, genre, tahun, rating, deskripsi, gambar)
        db.collection("films")
            .document(filmBaru.judul)
            .set(filmBaru)
            .addOnSuccessListener {
                Log.d("Firebase", "Film berhasil disimpan")
                readFilm(db)
            }
            .addOnFailureListener {
                Log.d("Firebase", it.message.toString())
            }
    }


    fun readFilm(db: FirebaseFirestore) {
        db.collection("films")
            .get()
            .addOnSuccessListener { result ->
                val filmList = mutableListOf<film>()
                for (document in result) {
                    val readFilm = film(
                        document.getString("judul").orEmpty(),
                        document.getString("genre").orEmpty(),
                        document.getString("tahun").orEmpty(),
                        document.getString("rating").orEmpty(),
                        document.getString("deskripsi").orEmpty(),
                        document.getString("gambar").orEmpty()
                    )
                    filmList.add(readFilm)
                }

                // Update your RecyclerView adapter with the new data
                updateData(filmList)

            }
            .addOnFailureListener {
                Log.d("Firebase", it.message.toString())
            }
    }

    fun updateData(newData: List<film>) {
        arFilm.clear()
        arFilm.addAll(newData)
        adapterFilm.notifyDataSetChanged()
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    private lateinit var adapterFilm: adapterFilm
    private var arFilm: MutableList<film> = mutableListOf()

}