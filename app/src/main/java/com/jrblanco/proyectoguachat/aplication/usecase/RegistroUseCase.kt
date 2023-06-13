package com.jrblanco.proyectoguachat.aplication.usecase

import com.jrblanco.proyectoguachat.domain.repository.RegistroRepository

class RegistroUseCase(private val registroRepository: RegistroRepository) {

    suspend operator fun invoke(email: String, password: String):Boolean {
        return registroRepository.newUser(email=email, password=password)
    }

}