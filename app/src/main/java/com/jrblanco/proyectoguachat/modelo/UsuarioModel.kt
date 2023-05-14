package com.jrblanco.proyectoguachat.modelo

data class UsuarioModel(
    var idGoogle: String = "",
    var nombre: String = "",
    var email: String = "",
    var avatar: String = ""
) {
    fun toMap(): MutableMap<String, Any>{
        return mutableMapOf(
            "idgoogle" to this.idGoogle,
            "nombre" to this.nombre,
            "email" to this.email,
            "avatar" to this.avatar
        )
    }

    companion object {
        val USUARIOS = "usuarios"
    }
}
