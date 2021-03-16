package com.example.agendaukmtaekondo.anggota

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.agendaukmtaekondo.R
import com.example.agendaukmtaekondo.admin.model.Rapat
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.hasil_rapat_anggota.*

class HasilRapatAnggota : AppCompatActivity() {
    lateinit var ref : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ref = FirebaseDatabase.getInstance().getReference("Rapat")
        setContentView(R.layout.hasil_rapat_anggota)
        val dataRef = FirebaseDatabase.getInstance().getReference("Rapat")
        var rapat: Rapat? = null
        val bundle = intent.extras
        tv_nameHasilRapatAnggota.setText(bundle?.getString("nama"))

        val name = tv_nameHasilRapatAnggota.text.toString()
        dataRef.orderByChild("idrapat").equalTo(name)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Log.d("apaini", rapat?.namarapat!!)
                }
                override fun onDataChange(p0: DataSnapshot) {
                    for (snap in p0.children)
                        rapat = snap.getValue(Rapat::class.java)
                    if (rapat==null){
                        tv_hasilrapatAnggota.text="Belum ada data yang di upload"
                    }else {
                        tv_hasilrapatAnggota.text = rapat!!.namarapat
                    }
                }
            })
    }
    
}