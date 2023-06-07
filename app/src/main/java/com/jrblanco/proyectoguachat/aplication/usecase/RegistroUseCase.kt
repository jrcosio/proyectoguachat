package com.jrblanco.proyectoguachat.aplication.usecase

import com.jrblanco.proyectoguachat.domain.repository.RegistroRepository

class RegistroUseCase(private val registroRepository: RegistroRepository) {

    suspend fun newUser(email: String, password: String):Boolean {
        return registroRepository.newUser(email=email, password=password)
    }

}