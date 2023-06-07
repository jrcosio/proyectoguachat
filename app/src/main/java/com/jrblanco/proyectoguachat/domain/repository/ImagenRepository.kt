package com.jrblanco.proyectoguachat.domain.repository

import android.net.Uri

interface ImagenRepository {
    fun saveImagen(imageUri: Uri?, onSuccess: () -> Unit, onFailure: () -> Unit)
}