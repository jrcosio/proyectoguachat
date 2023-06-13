package com.jrblanco.proyectoguachat.aplication.usecase

import com.jrblanco.proyectoguachat.domain.model.Usuario
import com.jrblanco.proyectoguachat.domain.repository.DataBaseRepository

class AllContactUserBDUseCase(private val database: DataBaseRepository) {
    operator fun invoke(idGoogle: String, onSuccess: (List<Usuario>) -> Unit){
        database.allContactos(idGoogle, onSuccess)
    }
}