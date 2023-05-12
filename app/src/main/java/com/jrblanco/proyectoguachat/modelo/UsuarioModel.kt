package com.jrblanco.proyectoguachat.modelo

data class UsuarioModel(
    var idGoogle: String = "",
    var nombre: String = "",
    var email: String = "",
    var pass: String = "",
    var photo: String = ""
)
