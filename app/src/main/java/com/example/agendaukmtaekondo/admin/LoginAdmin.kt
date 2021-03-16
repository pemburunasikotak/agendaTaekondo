package com.example.agendaukmtaekondo.admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.agendaukmtaekondo.R
import com.example.agendaukmtaekondo.admin.model.Admin
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import kotlinx.android.synthetic.main.login_admin.*

class LoginAdmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_admin)
        //Button login ketika di klik
        btn_loginadmin.setOnClickListener {
            //menjalankan Fungsi Login
            login()
        }

    }

    //Fungsi login
    private fun login() {
        ///awal faldasi data
        if (et_email_admin.text.toString().isEmpty()){
            et_email_admin.error = "masukkan Email"
            et_email_admin.requestFocus()
        }

        if (et_passwd_admin.text.toString().isEmpty()){
            et_passwd_admin.error ="masukkan Paswd yang benar"
            et_passwd_admin.requestFocus()
            return
        }
        //ahir Falidasi data

        //deklarasi variabel yang digunakan
        val user =et_email_admin.text.toString().trim()
        val pasword= et_passwd_admin.text.toString()

        //query ke database firebase table Admin dengan chilnya adalah email
        var query = FirebaseDatabase.getInstance().getReference("Admin").orderByChild("email").equalTo(user)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (snap in snapshot.children) {
                        val x = snap.getValue(Admin::class.java)
                        Log.e("test", Gson().toJson(x))
                        //Cek pAssword login
                        if (x!!.password.equals(pasword.trim())) {
                            val intent = Intent(this@LoginAdmin, DaftarAgendaAdmin::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@LoginAdmin, "User Tidak Ditemukan", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}