package com.jrblanco.proyectoguachat.aplication.usecase

import com.jrblanco.proyectoguachat.domain.repository.LoginRepository

class LoginGoogleUseCase(private val loginRepository: LoginRepository) {

    suspend operator fun invoke(idToken: String): Boolean {
        return return loginRepository.loginGoogle(idToken)
    }
}