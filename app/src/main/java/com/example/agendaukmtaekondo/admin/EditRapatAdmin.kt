package com.example.agendaukmtaekondo.admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.agendaukmtaekondo.R
import com.example.agendaukmtaekondo.admin.model.Rapat
import com.example.agendaukmtaekondo.anggota.LoginAnggota
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.hasil_rapat.*
import kotlinx.android.synthetic.main.tambah_hasil_rapat.*

class EditRapatAdmin : AppCompatActivity() {
    lateinit var ref : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //deklarasi Firebase
        ref = FirebaseDatabase.getInstance().getReference("Rapat")
        setContentView(R.layout.tambah_hasil_rapat)

        //Terima paket data dengankey nama
        val bundle = intent.extras
        et_namarapat.setText(bundle?.getString("nama"))

        val idrapat =et_namarapat.text.toString()

        //Fungi ketika Button simpan di Clik
        btn_simpandatarapat.setOnClickListener {
            val namarapat = et_tambahrapat.text.toString().trim()

            val test = Rapat(idrapat,namarapat)
            this.ref = FirebaseDatabase.getInstance().getReference("Rapat").child(idrapat)
            ref.setValue(test).addOnCompleteListener {
                Toast.makeText(this, "Successs", Toast.LENGTH_SHORT).show()
                //Log.d("test", ref.toString())
                startActivity(Intent(this, DaftarAgendaAdmin::class.java))
            }
        }
    }


}