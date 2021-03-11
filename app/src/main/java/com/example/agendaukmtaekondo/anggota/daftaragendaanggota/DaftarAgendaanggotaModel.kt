package com.example.agendaukmtaekondo.anggota.daftaragendaanggota

import com.google.firebase.database.PropertyName

open class DaftarAgendaanggotaModel(

    var id : String =" ",
    @PropertyName(value ="nama")
    var nama:String ="",
    @PropertyName(value ="agenda")
    var diskripsi: String="",
    @PropertyName(value ="tempat")
    var lokasi: String="",
    @PropertyName(value ="waktu")
    var waktu: String=""

    )