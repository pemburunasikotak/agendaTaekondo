package com.example.agendaukmtaekondo.anggota

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.agendaukmtaekondo.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.detail_anggota.*

class DetailAgendaAnggota : AppCompatActivity() {
    lateinit var ref : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ref = FirebaseDatabase.getInstance().getReference("Agenda")
        setContentView(R.layout.detail_anggota)

        //dari Lihat
        val bundle = intent.extras
        tv_namaDetailanggota.setText(bundle?.getString("name"))
        tv_waktudetailanggota.setText(bundle?.getString("waktu"))
        tv_deskripsidetailanggota.setText(bundle?.getString("desk"))
        tv_lokasidetailanggota.setText(bundle?.getString("lokasi"))
        val nama = tv_lokasidetailanggota.text.toString()

        btn_hasilrapatagendaanggota.setOnClickListener {
            val name = tv_namaDetailanggota
            val bundel = Bundle()
            bundel.putString("nama",name.text.toString())
            val intent= Intent(this, HasilRapatAnggota::class.java)
            intent.putExtras(bundel)
            startActivity(intent)
        }
        btn_lokasianggota.setOnClickListener {
            val  i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/$nama"))
            startActivity(i)
        }
    }
}