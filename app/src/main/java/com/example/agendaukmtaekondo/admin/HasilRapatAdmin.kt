package com.example.agendaukmtaekondo.admin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.agendaukmtaekondo.R
import com.example.agendaukmtaekondo.admin.model.Agenda
import com.example.agendaukmtaekondo.admin.model.Rapat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.detail_admin.*
import kotlinx.android.synthetic.main.detail_admin.btn_hasilrapatagenda
import kotlinx.android.synthetic.main.detail_anggota.*
import kotlinx.android.synthetic.main.hasil_rapat.*

class HasilRapatAdmin : AppCompatActivity() {
    lateinit var ref : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ref = FirebaseDatabase.getInstance().getReference("Rapat")
        setContentView(R.layout.hasil_rapat)

        val bundle = intent.extras
        tv_nameHasilRapat.setText(bundle?.getString("nama"))

        val name = tv_nameHasilRapat.text.toString()

        //var pengguna = FirebaseAuth.getInstance().currentUser
        //var test = pengguna?.uid.toString()

        tv_nameHasilRapat.setText(name)

        val test = tv_nameHasilRapat.text.toString()

        btn_editRapat.setOnClickListener {
            val bundel = Bundle()
            bundel.putString("nama",name)
            val intent= Intent(this, EditRapatAdmin::class.java)
            intent.putExtras(bundel)
            startActivity(intent)
        }


        val dataRef = FirebaseDatabase.getInstance().getReference("Rapat")
        var rapat: Rapat? = null
        dataRef.orderByChild("idrapat").equalTo(name)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Log.d("apaini", rapat?.namarapat!!)
                }

                override fun onDataChange(p0: DataSnapshot) {
                    for (snap in p0.children)
                        rapat = snap.getValue(Rapat::class.java)

                    if (rapat==null){
                        tv_hasilrapat.text="Belum ada data yang di upload"
                    }else {
                        tv_hasilrapat.text = rapat!!.namarapat
                    }
                }
            })
    }
}