package com.jrblanco.proyectoguachat.infraestructure

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jrblanco.proyectoguachat.domain.model.Usuario
import com.jrblanco.proyectoguachat.domain.repository.DataBaseRepository

class FirestoreDatabaseRepository : DataBaseRepository {
    private val USUARIOS = "usuarios"

    private val auth = Firebase.auth
    private val db = Firebase.firestore

    override fun saveUser(user: Usuario, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        user.idGoogle = auth.currentUser?.uid!!
        user.avatar = "${user.idGoogle}.imagen"

        db.collection(Usuario.USUARIOS)
            .document(user.idGoogle)
            .set(user)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(it.exception!!)
                }
            }
    }
}