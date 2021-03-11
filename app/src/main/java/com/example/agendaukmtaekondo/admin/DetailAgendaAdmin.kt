package com.example.agendaukmtaekondo.admin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.agendaukmtaekondo.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.detail_admin.*
import kotlinx.android.synthetic.main.detail_anggota.*

class DetailAgendaAdmin : AppCompatActivity() {
    lateinit var ref : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ref = FirebaseDatabase.getInstance().getReference("Agenda")
        setContentView(R.layout.detail_admin)

        //dari Lihat
        val bundle = intent.extras
        tv_namaDetail.setText(bundle?.getString("nama"))
        tv_waktudetail.setText(bundle?.getString("waktu"))
        tv_deskripsidetail.setText(bundle?.getString("desk"))
        tv_lokasidetail.setText(bundle?.getString("lokasi"))

        val lokasi =tv_lokasidetail.text.toString()
        Log.d("Lokasi",lokasi)
        val name = tv_namaDetail.text.toString()
        btn_lokasi.setOnClickListener {
            val  i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/$lokasi"))
            startActivity(i)
        }

        btn_hasilrapatagenda.setOnClickListener {
            val name = tv_namaDetail
            val bundel = Bundle()
            bundel.putString("nama",name.text.toString())
            val intent= Intent(this, HasilRapatAdmin::class.java)
            intent.putExtras(bundel)
            startActivity(intent)
        }
    }
}