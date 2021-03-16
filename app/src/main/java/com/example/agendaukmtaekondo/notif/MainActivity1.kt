package com.example.agendaukmtaekondo.notif

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.agendaukmtaekondo.R
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//const val TOPIC = "/topics/myTopic2"

class MainActivity1 : AppCompatActivity() {
    val TOPIC = "/topics/myTopic2"
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseService.sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            FirebaseService.token = it.token
            etToken.setText(it.token)
        }
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
        btnSend.setOnClickListener {

            val title = etTitle.text.toString()
            val message = etMessage.text.toString()
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

            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)

        }
//        btnSend.setOnClickListener {
//
//
//            val title = etTitle.text.toString()
//            val message = etMessage.text.toString()
//            //val recipientToken = etToken.text.toString()
//
//            Log.d("testtingmasage", title)
//            //&& recipientToken.isNotEmpty()
//            Toast.makeText(this, message, Toast.LENGTH_LONG)
//            //Toast.makeText(this, title, Toast.LENGTH_LONG)
//            //Toast.makeText(this, TOPIC, Toast.LENGTH_LONG)
//            if(title.isNotEmpty() && message.isNotEmpty() ) {
//                PushNotification(
//                        NotificationData(title, message),
//                        TOPIC
//                        //recipientToken
//
//                ).also {
//                    sendNotification(it)
//
//                    Log.d("testtingmasage", message)
//                }
//            }
//        }
    }

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
}
