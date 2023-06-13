package com.jrblanco.proyectoguachat.aplication.usecase

import com.jrblanco.proyectoguachat.domain.model.Usuario
import com.jrblanco.proyectoguachat.domain.repository.DataBaseRepository

class NewContactUserCase(private val database: DataBaseRepository){
    operator fun invoke(idGoogle: String, contactoNuevo: Usuario, onSuccess: () -> Unit){
        database.newContacto(idGoogle, contactoNuevo,onSuccess)
    }
}