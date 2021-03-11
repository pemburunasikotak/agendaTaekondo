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
    private var list : MutableList<DaftarAgendaAdminModel> = ArrayList()
    private lateinit var rvData: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.daftar_agenda_admin)
        rvData = findViewById(R.id.rv_daftaragendaadmin)
        rvData.setHasFixedSize(true)
        btn_adddaftaragenda.setOnClickListener {
            val intent = Intent(this, TambahAgendaAdmin::class.java)
            startActivity(intent)
        }
        btn_keluaradmin.setOnClickListener {
            val intent = Intent(this, LoginAnggota::class.java)
            startActivity(intent)
        }
        fullScreen()
        RecyclerCardView()
    }
    private fun RecyclerCardView() {
        val listadapter = DaftarAgendaAdminAdapter(this,list)
        rvData.adapter = listadapter
        rvData.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        var myRef = FirebaseDatabase.getInstance().getReference("Agenda")

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
    private fun fullScreen() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }
}