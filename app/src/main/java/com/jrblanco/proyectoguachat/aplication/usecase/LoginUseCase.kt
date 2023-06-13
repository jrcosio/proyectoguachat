package com.jrblanco.proyectoguachat.aplication.usecase

import com.google.firebase.auth.FirebaseAuth
import com.jrblanco.proyectoguachat.domain.repository.LoginRepository

class LoginUseCase(private val loginRepository: LoginRepository) {

    suspend operator fun invoke(email: String, password: String): Boolean {
        return loginRepository.loginClassic(email, password)
    }
}