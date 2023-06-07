package com.jrblanco.proyectoguachat.domain.repository

import com.jrblanco.proyectoguachat.domain.model.Usuario

interface DataBaseRepository {
    fun saveUser(user: Usuario, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
}