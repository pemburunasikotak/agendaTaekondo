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

//Class adapter di RV
class DaftarAgendaAnggotaAdapter(private val context: Context, private val anggota: List<DaftarAgendaanggotaModel>)
    :RecyclerView.Adapter<DaftarAgendaAnggotaAdapter.DaftarAgendaHolder>(){

    //Fungsi yang petama kali di jalankan
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaftarAgendaAnggotaAdapter.DaftarAgendaHolder {
        val view: View= LayoutInflater.from(parent.context).inflate(
            R.layout.list_daftar_agenda_karyawan, parent, false
        )
        return DaftarAgendaHolder(view)
    }
    //Fungsi yang digunakan untuk mengisi data yng telah di inisialisasi
    override fun onBindViewHolder(holder: DaftarAgendaAnggotaAdapter.DaftarAgendaHolder, position: Int) {
        //Deklarasi model array
        val list= anggota[position]
        //Isi data per Varibalenya
        holder.tv_namaAgendaAnggota.text= list.nama
        holder.tv_agendaadminAnggota.text= list.diskripsi
        holder.tv_deskagendaAnggota.text = list.diskripsi
        holder.tv_waktuagendaAnggota.text = list.waktu

        //Jika di klik maka akan melakukan
        holder.itemView.setOnClickListener {
            //Kirim paket
            val bundel = Bundle()
            bundel.putString("name", list.nama.toString())
            bundel.putString("waktu", list.waktu.toString())
            bundel.putString("desk", list.diskripsi.toString())
            bundel.putString("lokasi", list.lokasi.toString())
            val intent = Intent(context, DetailAgendaAnggota::class.java)
            intent.putExtras(bundel)
            context.startActivity(intent)
        }
    }

    //Hitung jumlah dari datanya
    override fun getItemCount(): Int = anggota.size
    //deklarasi data
    inner class DaftarAgendaHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tv_namaAgendaAnggota: TextView = view.findViewById(R.id.tv_namaAgendaAnggota)
        var tv_agendaadminAnggota:TextView= view.findViewById(R.id.tv_agendaadminAnggota)
        var tv_deskagendaAnggota: TextView=view.findViewById(R.id.tv_deskagendaAnggota)
        var tv_waktuagendaAnggota: TextView= view.findViewById(R.id.tv_waktuagendaAnggota)

    }
}