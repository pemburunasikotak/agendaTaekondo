package com.example.agendaukmtaekondo.anggota.daftaragendaanggota

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.agendaukmtaekondo.R
import com.example.agendaukmtaekondo.anggota.LoginAnggota
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.daftar_agenda_anggota.*

@Suppress("DEPRECATION")
class DaftarAgendaAnggota : AppCompatActivity() {

    //Deklarasi Varibale yang digunakan dalam satu Class
    lateinit var auth: FirebaseAuth
    private var list : MutableList<DaftarAgendaanggotaModel> = ArrayList()
    private lateinit var rvData: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setValue Variable yang digunakan
        auth = FirebaseAuth.getInstance()
        setContentView(R.layout.daftar_agenda_anggota)
        rvData = findViewById(R.id.rv_daftaragendaanggota)
        rvData.setHasFixedSize(true)

        //Fungsi Btn Join Chat Grup auto  redirect ke WhatApp
        btn_joinChatGrup.setOnClickListener {
            val  intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://chat.whatsapp.com/LSJ8okgHkRU1XvnaqVLFRN"))
            startActivity(intent)
        }
        //Fungssi btn keluar jika di klik maka akan intent ke LoginAnggota
        btn_keluaranggota.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, LoginAnggota::class.java)
            startActivity(intent)
        }
        fullScreen()
        RecyclerCardView()
    }
    //Fungsi membuat Recyleview
    private fun RecyclerCardView() {
        //Deklarsi Varibale
        val listadapter = DaftarAgendaAnggotaAdapter(this, list)
        rvData.adapter = listadapter
        rvData.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //Deklarasi Database
        var myRef = FirebaseDatabase.getInstance().getReference("Agenda")

        //memanggil Fungsi di database digunakan untuk mengset Value
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snap in snapshot.children) {
                    val x = snap.getValue(DaftarAgendaanggotaModel::class.java)
                    //Log.e("testsoal", Gson().toJson(x))
                    list.add(x!!)
                    listadapter.notifyDataSetChanged()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    private fun fullScreen() {
        window.setFlags(
            FLAG_FULLSCREEN,
            FLAG_FULLSCREEN
        )
    }
}