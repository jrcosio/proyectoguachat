package com.jrblanco.proyectoguachat.aplication.usecase

import android.net.Uri
import com.jrblanco.proyectoguachat.domain.repository.ImagenRepository

class GuardarImagenUsecase(private val imagenRepository: ImagenRepository) {
    operator fun invoke(imageUri: Uri?, onSuccess: (String) -> Unit, onFailure: () -> Unit) {
        imagenRepository.saveImagen(imageUri, onSuccess, onFailure)
    }
}