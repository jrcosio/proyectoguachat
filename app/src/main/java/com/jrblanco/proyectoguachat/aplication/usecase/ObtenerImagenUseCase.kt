package com.jrblanco.proyectoguachat.aplication.usecase

import android.net.Uri
import com.jrblanco.proyectoguachat.domain.repository.ImagenRepository

class ObtenerImagenUseCase(private val imagenRepository: ImagenRepository) {
    operator fun invoke(idGoogle: String, onSuccess: (String) -> Unit) {
        imagenRepository.urlImagen(idGoogle,onSuccess)
    }
}