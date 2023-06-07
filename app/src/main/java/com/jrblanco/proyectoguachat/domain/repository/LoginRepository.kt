package com.jrblanco.proyectoguachat.domain.repository

interface LoginRepository {
    suspend fun loginClassic(email: String, password: String):Boolean
    suspend fun loginGoogle(idToken: String): Boolean
}