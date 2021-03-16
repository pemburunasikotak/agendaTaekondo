package com.example.agendaukmtaekondo.anggota

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import android.widget.Toast
import com.example.agendaukmtaekondo.MainActivity
import com.example.agendaukmtaekondo.R
import com.example.agendaukmtaekondo.admin.model.Admin
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.registrasi.*

class RegistrasiAnggota : AppCompatActivity() {
    lateinit var ref : DatabaseReference
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        setContentView(R.layout.registrasi)
        ref = FirebaseDatabase.getInstance().getReference("Anggota")
        btn_create_registrasi.setOnClickListener {
            registrasi()
            //savedatatorealtimedata()
        }
        fullScreen()

    }
    private fun savedatatorealtimedata(){
        val userId = ref.push().key.toString()
        val nama = et_nama_registras.text.toString().trim()
        val email = et_email_registrasi.text.toString().trim()
        val password = et_passwd_registrasi.text.toString().trim()
        val notelp = et_notelp_registrasi.text.toString().trim()
        val user = Admin(userId,nama,email, password, notelp)

        //this.ref = FirebaseDatabase.getInstance().getReference("Anggota")
        ref.child(userId).setValue(user).addOnCompleteListener {
            Toast.makeText(this, "Successs", Toast.LENGTH_SHORT).show()
            Log.d("test", ref.toString())
            startActivity(Intent(this, LoginAnggota::class.java))
        }
    }

    private fun registrasi() {
        //Validasi untuk data
        if (et_nama_registras.text.toString().isEmpty()) {
            et_nama_registras.error = "Masukkan Nama"
            et_nama_registras.requestFocus()
            return
        }
        if (et_email_registrasi.text.toString().isEmpty()) {
            et_email_registrasi.error = "Masukkan Email"
            et_email_registrasi.requestFocus()
            return
        }

        if (et_passwd_registrasi.toString().isEmpty()) {
            et_passwd_registrasi.error = "Masukkan Paswd yang benar"
            et_passwd_registrasi.requestFocus()
            return
        }
        if (et_notelp_registrasi.toString().isEmpty()) {
            et_notelp_registrasi.error = "Masukkan No telfon"
            et_notelp_registrasi.requestFocus()
            return
        }
        //val userId = ref.push().key.toString()
        val email = et_email_registrasi.text.toString().trim()
        val password = et_passwd_registrasi.text.toString().trim()
        // val user = Karywan(userId,nama, email, password, no_telp)

        auth.createUserWithEmailAndPassword(
            email, password
        ).addOnCompleteListener(this@RegistrasiAnggota) { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                user?.sendEmailVerification()
                    ?.addOnCompleteListener { //task ->
                        Log.d("test",user.toString())
                        if (task.isSuccessful) {
                            Toast.makeText(
                                baseContext,
                                "Silahkan Cek Email",
                                Toast.LENGTH_SHORT
                            ).show()
                            savedatatorealtimedata()
                        }
                    }
            } else {
                Toast.makeText(
                    baseContext, "Ulang Lagi",
                    Toast.LENGTH_SHORT
                ).show()
            }
            //Firebase database
        }
    }


    private fun fullScreen() {
        window.setFlags(
            FLAG_FULLSCREEN,
            FLAG_FULLSCREEN
        )
    }
}