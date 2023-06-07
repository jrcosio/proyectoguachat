package com.jrblanco.proyectoguachat.domain.repository

interface RegistroRepository {
    suspend fun newUser(email: String, password: String):Boolean
}