package com.jrblanco.proyectoguachat.aplication.usecase

import com.jrblanco.proyectoguachat.domain.model.Usuario
import com.jrblanco.proyectoguachat.domain.repository.DataBaseRepository

class GuardarUserDBUseCase(private val database: DataBaseRepository) {
    operator fun invoke(user: Usuario, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        database.saveUser(user, onSuccess, onFailure)
    }

}