package com.jrblanco.proyectoguachat.aplication.usecase

import com.jrblanco.proyectoguachat.domain.model.Message
import com.jrblanco.proyectoguachat.domain.repository.DataBaseRepository

class SendMessageChatUseCase(private val database: DataBaseRepository) {
    operator fun invoke(messageToSend: Message, idChat: String){
        database.sendMessageChat(messageToSend, idChat)
    }
}