package com.example.agendaukmtaekondo.admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.agendaukmtaekondo.R
import com.example.agendaukmtaekondo.admin.model.Rapat
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.hasil_rapat.*

class HasilRapatAdmin : AppCompatActivity() {
    lateinit var ref : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ref = FirebaseDatabase.getInstance().getReference("Rapat")
        setContentView(R.layout.hasil_rapat)

        //Terima intent pket dari layout detail
        val bundle = intent.extras
        tv_nameHasilRapat.setText(bundle?.getString("nama"))

        val name = tv_nameHasilRapat.text.toString()

        //var pengguna = FirebaseAuth.getInstance().currentUser
        //var test = pengguna?.uid.toString()

        tv_nameHasilRapat.setText(name)
        val test = tv_nameHasilRapat.text.toString()
        //Fungsi ketika btn edit di klik
        btn_editRapat.setOnClickListener {
            //kirim paket data ke aktifity selanjutnya
            val bundel = Bundle()
            bundel.putString("nama",name)
            val intent= Intent(this, EditRapatAdmin::class.java)
            intent.putExtras(bundel)
            //jalankan aktifity
            startActivity(intent)
        }
        //deklasi firebase
        val dataRef = FirebaseDatabase.getInstance().getReference("Rapat")
        var rapat: Rapat? = null
        //cek data yang sama dengan idrapat dan sama dengan name
        dataRef.orderByChild("idrapat").equalTo(name)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Log.d("apaini", rapat?.namarapat!!)
                }
                //Fungsi data Change
                override fun onDataChange(p0: DataSnapshot) {
                    for (snap in p0.children)
                        rapat = snap.getValue(Rapat::class.java)
                    //jika rapat samadengan null maka akan mengset belum ada data rapat
                    if (rapat==null){
                        tv_hasilrapat.text="Belum ada data yang di upload"
                    }else {
                        //jika tidak null maka akan mengset data dari Firebase
                        tv_hasilrapat.text = rapat!!.namarapat
                    }
                }
            })
    }
}