package com.example.agendaukmtaekondo.admin

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.agendaukmtaekondo.R
import com.example.agendaukmtaekondo.admin.daftaragendaadmin.DaftarAgendaAdminAdapter
import com.example.agendaukmtaekondo.anggota.LoginAnggota
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.daftar_agenda_admin.*

@Suppress("DEPRECATION")
class DaftarAgendaAdmin : AppCompatActivity() {
    //deklarasi data awal yang bisa di akses semua klass
    private var list : MutableList<DaftarAgendaAdminModel> = ArrayList()
    private lateinit var rvData: RecyclerView

    //Fungsi yang pertama kali dijalankan
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Layout yang digunakan
        setContentView(R.layout.daftar_agenda_admin)
        //Deklarasi Recycle View
        rvData = findViewById(R.id.rv_daftaragendaadmin)
        //Set Recyccle View
        rvData.setHasFixedSize(true)

        //Fungsi ketika button adddaftar di clik
        btn_adddaftaragenda.setOnClickListener {
            val intent = Intent(this, TambahAgendaAdmin::class.java)
            startActivity(intent)
        }
        //Fungi ketika btn keluar di clik
        btn_keluaradmin.setOnClickListener {
            val intent = Intent(this, LoginAnggota::class.java)
            startActivity(intent)
        }
        //Fungsi untuk FullScreen
        fullScreen()
        //Fungsi untuk mengisi RV
        RecyclerCardView()
    }

    //Fungsi untuk menjalankan RView
    private fun RecyclerCardView() {
        //deklarasi RV
        val listadapter = DaftarAgendaAdminAdapter(this,list)
        rvData.adapter = listadapter
        rvData.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //deklarasi untuk Database
        var myRef = FirebaseDatabase.getInstance().getReference("Agenda")
        //isi data di RV
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snap in snapshot.children) {
                    val x = snap.getValue(DaftarAgendaAdminModel::class.java)
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
    //Fungsi FullScreen layar
    private fun fullScreen() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }
}