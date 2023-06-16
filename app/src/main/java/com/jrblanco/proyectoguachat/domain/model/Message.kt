package com.jrblanco.proyectoguachat.domain.model

import com.google.firebase.Timestamp

data class Message(
    var idGoogle: String = "",
    var mensaje: String = "",
    var nombre: String = "",
    var imagen: String = "",
    var fecha: Timestamp? = null
)
