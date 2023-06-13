package com.jrblanco.proyectoguachat.aplication.usecase

import com.jrblanco.proyectoguachat.domain.model.Usuario
import com.jrblanco.proyectoguachat.domain.repository.DataBaseRepository

class LoadContactUseCase(private val database: DataBaseRepository) {
    operator fun invoke(idGoogle: String, onSuccess: (Usuario) -> Unit){
        database.loadContact(idGoogle, onSuccess)
    }
}