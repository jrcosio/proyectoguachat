package com.jrblanco.proyectoguachat.infraestructure

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.jrblanco.proyectoguachat.domain.model.Usuario
import com.jrblanco.proyectoguachat.domain.repository.DataBaseRepository

class FirestoreDatabaseRepository : DataBaseRepository {
    private val USUARIOS = "usuarios"
    private val CONTACTOS = "contactos"
    private val db = Firebase.firestore

    override fun saveUser(user: Usuario, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
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

    override fun loadUser(user: (Usuario) -> Unit) {
        val auth = Firebase.auth
        val idGoogle = auth.currentUser?.uid

        if (idGoogle != null) {
            db.collection(Usuario.USUARIOS)
                .document(idGoogle)
                .get()
                .addOnSuccessListener {
                    if (it.exists()) {
                        val usuario = it.toObject<Usuario>()
                        if (usuario != null) {
                            user(usuario)
                        }
                    }
                }
        }
    }

    override fun allUser(onSuccess: (List<Usuario>) -> Unit) {
        db.collection(USUARIOS)
            .get()
            .addOnSuccessListener { query ->
                val allUsers = mutableListOf<Usuario>()
                query.forEach { document ->
                    allUsers.add(document.toObject(Usuario::class.java))
                }
                onSuccess(allUsers)
            }
    }

    override fun newContacto(idGoogle: String, contactoNuevo: Usuario, onSuccess: () -> Unit) {
        db.collection(USUARIOS).document(idGoogle)
            .collection(CONTACTOS).document(contactoNuevo.idGoogle)
            .set(contactoNuevo)
            .addOnSuccessListener {
                onSuccess()
            }
    }

    override fun allContactos(idGoogle: String, onSuccess: (List<Usuario>) -> Unit) {
        db.collection(USUARIOS).document(idGoogle).collection(CONTACTOS)
            .get()
            .addOnSuccessListener { query ->
                val allContactUser = mutableListOf<Usuario>()
                query.forEach { document ->
                    allContactUser.add(document.toObject(Usuario::class.java))
                }
                onSuccess(allContactUser)
            }
    }

    override fun loadContact(idGoogle: String, onSuccess: (Usuario) -> Unit) {
        val auth = Firebase.auth

        if (idGoogle != null) {
            db.collection(Usuario.USUARIOS)
                .document(idGoogle)
                .get()
                .addOnSuccessListener {
                    if (it.exists()) {
                        val usuario = it.toObject<Usuario>()
                        if (usuario != null) {
                            onSuccess(usuario)
                        }
                    }
                }
        }
    }


}