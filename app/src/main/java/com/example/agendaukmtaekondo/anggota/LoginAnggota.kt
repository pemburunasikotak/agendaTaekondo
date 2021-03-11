package com.example.agendaukmtaekondo.anggota

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.agendaukmtaekondo.R
import com.example.agendaukmtaekondo.admin.LoginAdmin
import com.example.agendaukmtaekondo.admin.model.Admin
import com.example.agendaukmtaekondo.anggota.daftaragendaanggota.DaftarAgendaAnggota
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.login_anggota.*

class LoginAnggota : AppCompatActivity() {

    lateinit var ref : DatabaseReference
    lateinit var auth: FirebaseAuth

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_anggota)
        auth = FirebaseAuth.getInstance()
        ref = FirebaseDatabase.getInstance().getReference("Anggota")
        et_loginadmin.setOnClickListener {
            val intent = Intent(this, LoginAdmin::class.java )
            startActivity(intent)
        }
        btn_createakun.setOnClickListener {
            val intent = Intent(this, RegistrasiAnggota::class.java )
            startActivity(intent)
        }
        btn_loginkaryawan.setOnClickListener {
            loginbaru()
        }
        fullScreen()
    }

    private fun loginbaru() {
        et_passwd_anggota
        et_email_anggota
        if (et_email_anggota.text.toString().isEmpty()){
            et_email_anggota.error = "masukkan Email"
            et_email_anggota.requestFocus()
        }

        if (et_passwd_anggota.text.toString().isEmpty()){
            et_passwd_anggota.error ="masukkan Paswd yang benar"
            et_passwd_anggota.requestFocus()
            return
        }
        auth.signInWithEmailAndPassword(
            et_email_anggota.text.toString(),
            et_passwd_anggota.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("Test", "signInWithEmail:success")
                    val user: FirebaseUser = auth.getCurrentUser()!!
                    updateUI(user)
                } else {
                    updateUI(null)
                }

            }
    }

    //fungsi untuk Verifikasi Email
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
    fun getUser(email: String){
        var user: Admin? = null
        ref.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (snap in p0.children) {
                    user = snap.getValue(Admin::class.java)
                }

                //AlStatic.setSPString(this@Login, App.instance.USER_KEY, Gson().toJson(user))
            }
        })
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null){
            if (currentUser.isEmailVerified){
                getUser(currentUser.email!!)
                startActivity(Intent(this, DaftarAgendaAnggota::class.java))
                finish()
            }else{
                Toast.makeText(
                    baseContext, "Email belum Di Verifikasi silahkan cek Email",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }else{
            Toast.makeText(
                baseContext, "Login failed.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    private fun fullScreen() {
        window.setFlags(
            FLAG_FULLSCREEN,
            FLAG_FULLSCREEN
        )
    }


}

