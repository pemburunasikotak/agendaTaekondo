package com.example.agendaukmtaekondo.anggota.daftaragendaanggota

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.agendaukmtaekondo.R
import com.example.agendaukmtaekondo.anggota.DetailAgendaAnggota

class DaftarAgendaAnggotaAdapter(private val context: Context, private val anggota: List<DaftarAgendaanggotaModel>)
    :RecyclerView.Adapter<DaftarAgendaAnggotaAdapter.DaftarAgendaHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DaftarAgendaAnggotaAdapter.DaftarAgendaHolder {
        val view: View= LayoutInflater.from(parent.context).inflate(
            R.layout.list_daftar_agenda_karyawan, parent, false
        )
        return DaftarAgendaHolder(view)
    }

    override fun onBindViewHolder(holder: DaftarAgendaAnggotaAdapter.DaftarAgendaHolder, position: Int) {
        val list= anggota[position]
        holder.tv_namaAgendaAnggota.text= list.nama
        holder.tv_agendaadminAnggota.text= list.lokasi
        holder.tv_deskagendaAnggota.text = list.diskripsi
        holder.tv_waktuagendaAnggota.text = list.waktu

        holder.itemView.setOnClickListener {
            val bundel = Bundle()
            bundel.putString("waktu", list.waktu.toString())
            bundel.putString("desk", list.diskripsi.toString())
            bundel.putString("lokasi", list.lokasi.toString())
            val intent = Intent(context, DetailAgendaAnggota::class.java)
            intent.putExtras(bundel)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = anggota.size

    inner class DaftarAgendaHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tv_namaAgendaAnggota: TextView = view.findViewById(R.id.tv_namaAgendaAnggota)
        var tv_agendaadminAnggota:TextView= view.findViewById(R.id.tv_agendaadminAnggota)
        var tv_deskagendaAnggota: TextView=view.findViewById(R.id.tv_deskagendaAnggota)
        var tv_waktuagendaAnggota: TextView= view.findViewById(R.id.tv_waktuagendaAnggota)

    }
}