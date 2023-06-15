package com.jrblanco.proyectoguachat.aplication.usecase

import com.jrblanco.proyectoguachat.domain.model.Usuario
import com.jrblanco.proyectoguachat.domain.repository.DataBaseRepository

class LoadChatWithContactUseCase(private val database: DataBaseRepository) {
    operator fun invoke(idGoogleContact: String, onSuccess: (Usuario) -> Unit) {
        database.loadChatWithContact(idGoogleContact, onSuccess)
    }
}