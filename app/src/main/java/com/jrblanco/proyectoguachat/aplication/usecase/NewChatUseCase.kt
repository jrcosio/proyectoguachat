package com.jrblanco.proyectoguachat.aplication.usecase

import com.jrblanco.proyectoguachat.domain.model.Usuario
import com.jrblanco.proyectoguachat.domain.repository.DataBaseRepository

class NewChatUseCase(private val database: DataBaseRepository) {
    operator fun invoke(usuario: Usuario, contact: Usuario, onSuccess: (String) -> Unit){
        database.newChat(usuario, contact, onSuccess)
    }
}