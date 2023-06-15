package com.jrblanco.proyectoguachat.domain.model

data class ChatUsuario(
    var idchat: String = "",
    var isGrupo: Boolean = false,
    var idGoogle: String = "",
    var nombre: String = "",
    var icono: String = ""
)
