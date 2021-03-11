package com.example.agendaukmtaekondo.admin.daftaragendaadmin

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.agendaukmtaekondo.R
import com.example.agendaukmtaekondo.admin.DaftarAgendaAdmin
import com.example.agendaukmtaekondo.admin.DaftarAgendaAdminModel
import com.example.agendaukmtaekondo.admin.DetailAgendaAdmin
import com.example.agendaukmtaekondo.admin.TambahAgendaAdmin
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.tambah_agenda_admin.*

class DaftarAgendaAdminAdapter(private val context: Context, private val daftaragendaadmin: List<DaftarAgendaAdminModel>)
    :RecyclerView.Adapter<DaftarAgendaAdminAdapter.DaftarAgendaHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DaftarAgendaAdminAdapter.DaftarAgendaHolder {
        val view: View= LayoutInflater.from(parent.context).inflate(
            R.layout.list_daftar_agenda_admin, parent, false
        )
        return DaftarAgendaHolder(view)
    }

    override fun onBindViewHolder(holder: DaftarAgendaAdminAdapter.DaftarAgendaHolder, position: Int) {
        val list= daftaragendaadmin[position]
        holder.tv_namaAgenda.text= list.nama
        holder.et_agenda.text= list.diskripsi
        holder.tvwaktuagenda.text = list.waktu
        holder.tvdesk.text= list.lokasi
        holder.btn_editagenda.setOnClickListener {

            Toast.makeText(context, "Edit", Toast.LENGTH_LONG)
            val bundel = Bundle()
            bundel.putString("nama", list.nama)
            bundel.putString("waktu", list.waktu)
            bundel.putString("desk", list.diskripsi)
            bundel.putString("lokasi", list.lokasi)
            val intent = Intent(context, TambahAgendaAdmin::class.java)
            intent.putExtras(bundel)
            context.startActivity(intent)
        }
        holder.btn_hapusagenda.setOnClickListener {
            val ref = FirebaseDatabase.getInstance().getReference("Agenda").child(list.nama)
            Log.d("test",ref.toString())
            val Query: Query = ref
            Query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (appleSnapshot in dataSnapshot.children) {
                        appleSnapshot.ref.removeValue()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e(ContentValues.TAG, "onCancelled", databaseError.toException())
                }
            })
            val intent = Intent(context, DaftarAgendaAdmin::class.java)
            context.startActivity(intent)

        }
        holder.btn_lihat.setOnClickListener {
            Toast.makeText(context, "Edit", Toast.LENGTH_LONG)
            val bundel = Bundle()
            bundel.putString("nama", list.nama)
            bundel.putString("waktu", list.waktu.toString())
            bundel.putString("desk", list.diskripsi.toString())
            bundel.putString("lokasi", list.lokasi.toString())
            val intent = Intent(context, DetailAgendaAdmin::class.java)
            intent.putExtras(bundel)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int = daftaragendaadmin.size

    inner class DaftarAgendaHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_namaAgenda: TextView = view.findViewById(R.id.tv_namaAgenda)
        val et_agenda:TextView = view.findViewById(R.id.tv_agendaadmin)
        val tvdesk:TextView= view.findViewById(R.id.tv_deskagenda)
        val tvwaktuagenda:TextView= view.findViewById(R.id.tv_waktuagenda)

        val btn_hapusagenda:Button= view.findViewById(R.id.btn_hapusagenda)
        val btn_editagenda:Button=view.findViewById(R.id.btn_editagenda)
        val btn_lihat:Button= view.findViewById(R.id.btn_lihatAgenda)
    }


}