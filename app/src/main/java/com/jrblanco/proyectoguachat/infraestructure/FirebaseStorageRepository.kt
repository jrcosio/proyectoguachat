package com.jrblanco.proyectoguachat.infraestructure

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.jrblanco.proyectoguachat.domain.repository.ImagenRepository

class FirebaseStorageRepository : ImagenRepository {
    private val storage = FirebaseStorage.getInstance()

    override fun saveImagen(imageUri: Uri?, onSuccess: () -> Unit, onFailure: () -> Unit) {
        val storageRef = storage.reference
        val auth = FirebaseAuth.getInstance()

        if (imageUri != null) {
            val imageRef = storageRef.child("avatar_usuarios/${auth.uid}.imagen")
            imageRef.putFile(imageUri)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        onSuccess()
                    }
                }
                .addOnFailureListener {
                    onFailure()
                }
        }
    }

    override fun urlImagen(idGoogle: String, onSuccess: (String) -> Unit) {
        val storageRef = storage.reference
        val imageRef  = storageRef.child("avatar_usuarios/${idGoogle}.imagen")

        imageRef.downloadUrl
            .addOnSuccessListener { uri ->
                val imageUrl = uri.toString()
                onSuccess(imageUrl)
            }

    }

}