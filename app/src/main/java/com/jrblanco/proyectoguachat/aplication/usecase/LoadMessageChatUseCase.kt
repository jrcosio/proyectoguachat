package com.jrblanco.proyectoguachat.aplication.usecase

import com.jrblanco.proyectoguachat.domain.model.Message
import com.jrblanco.proyectoguachat.domain.repository.DataBaseRepository

class LoadMessageChatUseCase(private val database: DataBaseRepository) {
    operator fun invoke(idChat: String, onSuccess: (Message) -> Unit){
        database.loadMessageChat(idChat, onSuccess)
    }
}