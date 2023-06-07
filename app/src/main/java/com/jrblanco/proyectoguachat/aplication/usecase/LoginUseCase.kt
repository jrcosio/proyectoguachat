package com.jrblanco.proyectoguachat.aplication.usecase

import com.google.firebase.auth.FirebaseAuth
import com.jrblanco.proyectoguachat.domain.repository.LoginRepository

class LoginUseCase(private val loginRepository: LoginRepository) {

    suspend fun login(email: String, password: String): Boolean {
        return loginRepository.loginClassic(email, password)
    }

    suspend fun loginGoogle(idToken: String): Boolean {
        return return loginRepository.loginGoogle(idToken)
    }

    var auth = FirebaseAuth.getInstance()
        private set
}