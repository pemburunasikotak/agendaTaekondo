package com.example.agendaukmtaekondo.admin

import com.google.firebase.database.PropertyName


//model yang digunakan
open class DaftarAgendaAdminModel(
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