package com.example.agendaukmtaekondo.admin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.agendaukmtaekondo.*
import com.example.agendaukmtaekondo.admin.model.Agenda
import com.example.agendaukmtaekondo.notif.FirebaseService
import com.example.agendaukmtaekondo.notif.NotificationData
import com.example.agendaukmtaekondo.notif.PushNotification
import com.example.agendaukmtaekondo.notif.RetrofitInstance
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import kotlinx.android.synthetic.main.tambah_agenda_admin.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.launch


class TambahAgendaAdmin : AppCompatActivity() {
    val TOPIC = "/topics/myTopic2"
    val TAG = "MainActivity"
    lateinit var ref : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tambah_agenda_admin)
        FirebaseService.sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            FirebaseService.token = it.token
            //etToken.setText(it.token)
        }
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
        ref = FirebaseDatabase.getInstance().getReference("Agenda")


        //dari Edit Text
        val bundle = intent.extras
        et_namatambahagenda.setText(bundle?.getString("nama"))
        et_waktutambahagenda.setText(bundle?.getString("waktu"))
        et_deskripsitambahagenda.setText(bundle?.getString("desk"))
        et_loasitambahagenda.setText(bundle?.getString("lokasi"))



        btn_simpantambahagenda.setOnClickListener {


            //ahir dari pemangilan fungsi
            tambahdata()

            val title = et_namatambahagenda.text.toString().trim()
            val message = et_deskripsitambahagenda.text.toString().trim()
            //val recipientToken = etToken.text.toString()

            Log.d("testtingmasage", title)
            //&& recipientToken.isNotEmpty()
            Toast.makeText(this, message, Toast.LENGTH_LONG)
            //Toast.makeText(this, title, Toast.LENGTH_LONG)
            //Toast.makeText(this, TOPIC, Toast.LENGTH_LONG)
            if(title.isNotEmpty() && message.isNotEmpty() ) {
                PushNotification(
                    NotificationData(title, message),
                    TOPIC
                    //recipientToken

                ).also {
                    sendNotification(it)

                    Log.d("testtingmasage", message)
                }
            }

        }

    }
    //Notifikasi fungsi

    private fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = RetrofitInstance.api.postNotification(notification)
            if(response.isSuccessful) {
                Log.d(TAG, "Response: ${Gson().toJson(response)}")
            } else {
                Log.e(TAG, response.errorBody().toString())
            }
        } catch(e: Exception) {
            Log.e(TAG, e.toString())
        }
    }

    //Fungsi Tambah data
    private fun tambahdata() {
        //Falidasi data yang digunakan
        if (et_namatambahagenda.text.toString().isEmpty()) {
            et_namatambahagenda.error = "Masukkan Nama Event"
            et_namatambahagenda.requestFocus()
            return
        }
        if (et_waktutambahagenda.text.toString().isEmpty()) {
            et_waktutambahagenda.error = "Masukkan wkt"
            et_waktutambahagenda.requestFocus()
            return
        }

        if (et_deskripsitambahagenda.text.toString().isEmpty()) {
            et_deskripsitambahagenda.error = "Masukkan tgl"
            et_deskripsitambahagenda.requestFocus()
            return
        }
        if (et_loasitambahagenda.toString().isEmpty()) {
            et_loasitambahagenda.error = "Masukkan tempat"
            et_loasitambahagenda.requestFocus()
            return
        }

        //Notifikasi

        val title = et_namatambahagenda.text.toString()
        val message = et_loasitambahagenda.text.toString()
        Log.d("testtingmasage", title)
        Toast.makeText(this, message, Toast.LENGTH_LONG)
        if(title.isNotEmpty() && message.isNotEmpty() ) {
            PushNotification(
                NotificationData(title, message),
                TOPIC
                //recipientToken
            ).also {
                sendNotification(it)
                Log.d("testtingmasage", message)
            }
        }

        //ahir dari pemangilan fungsi

        val userId = et_namatambahagenda.text.toString().trim()
        val nama = et_namatambahagenda.text.toString().trim()
        val waktu = et_waktutambahagenda.text.toString().trim()
        val diskripsi = et_deskripsitambahagenda.text.toString().trim()
        val lokasi = et_loasitambahagenda.text.toString().trim()

        val event = Agenda(userId, nama, waktu, diskripsi, lokasi)
        //Kirim ke database
        ref.child(nama).setValue(event).addOnCompleteListener {
            Toast.makeText(this, "Sukses Tambah data ",Toast.LENGTH_LONG).show()
            val intent = Intent(this, DaftarAgendaAdmin::class.java)
            startActivity(intent)
        }
    }

}