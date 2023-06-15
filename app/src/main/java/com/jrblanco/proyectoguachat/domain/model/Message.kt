package com.jrblanco.proyectoguachat.domain.model

import com.google.firebase.Timestamp


data class Message(
    var iduser: String = "",
    var nombre: String = "",
    var mensaje: String = "",
    var fechayhora: Timestamp? = null
)
