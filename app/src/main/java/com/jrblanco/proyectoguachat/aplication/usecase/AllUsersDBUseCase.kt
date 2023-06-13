package com.jrblanco.proyectoguachat.aplication.usecase

import com.jrblanco.proyectoguachat.domain.model.Usuario
import com.jrblanco.proyectoguachat.domain.repository.DataBaseRepository

class AllUsersDBUseCase(private val database: DataBaseRepository) {

    operator fun invoke(onSuccess: (List<Usuario>) -> Unit){
        database.allUser(onSuccess)
    }
}