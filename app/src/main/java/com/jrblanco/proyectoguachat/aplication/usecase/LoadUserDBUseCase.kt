package com.jrblanco.proyectoguachat.aplication.usecase

import com.jrblanco.proyectoguachat.domain.model.Usuario
import com.jrblanco.proyectoguachat.domain.repository.DataBaseRepository

class LoadUserDBUseCase(private val database: DataBaseRepository) {

    operator fun invoke(user: (Usuario) -> Unit){
        database.loadUser(user)
    }
}