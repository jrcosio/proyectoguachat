package com.jrblanco.proyectoguachat.aplication.usecase

import com.jrblanco.proyectoguachat.domain.model.Chats
import com.jrblanco.proyectoguachat.domain.model.Message
import com.jrblanco.proyectoguachat.domain.model.Usuario
import com.jrblanco.proyectoguachat.domain.repository.DataBaseRepository

class LoadListChatUserUseCase(private val database: DataBaseRepository) {
    operator fun invoke(usuario: Usuario, onSuccess: (List<Chats>) -> Unit){
        database.loadListChats(usuario, onSuccess)
    }
}