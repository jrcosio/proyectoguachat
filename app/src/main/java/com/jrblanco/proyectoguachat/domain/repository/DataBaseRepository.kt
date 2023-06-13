package com.jrblanco.proyectoguachat.domain.repository

import com.jrblanco.proyectoguachat.domain.model.Usuario

interface DataBaseRepository {
    fun saveUser(user: Usuario, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
    fun loadUser(usuario: (Usuario) -> Unit)
    fun allUser(onSuccess: (List<Usuario>) -> Unit)

    fun newContacto(idGoogle: String, contactoNuevo: Usuario, onSuccess: () -> Unit)
    fun allContactos(idGoogle: String, onSuccess: (List<Usuario>) -> Unit)
}