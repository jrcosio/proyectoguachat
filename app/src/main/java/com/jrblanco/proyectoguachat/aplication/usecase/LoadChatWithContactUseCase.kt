package com.jrblanco.proyectoguachat.aplication.usecase

import com.jrblanco.proyectoguachat.domain.model.ChatUsuario
import com.jrblanco.proyectoguachat.domain.model.Usuario
import com.jrblanco.proyectoguachat.domain.repository.DataBaseRepository

class LoadChatWithContactUseCase(private val database: DataBaseRepository) {
    operator fun invoke(idGoogleContact: String, onSuccess: (ChatUsuario) -> Unit) {
        database.loadChatWithContact(idGoogleContact, onSuccess)
    }
}