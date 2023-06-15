package com.jrblanco.proyectoguachat.infraestructure

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.jrblanco.proyectoguachat.domain.model.ChatUsuario
import com.jrblanco.proyectoguachat.domain.model.Usuario
import com.jrblanco.proyectoguachat.domain.repository.DataBaseRepository

class FirestoreDatabaseRepository : DataBaseRepository {
    private val USUARIOS = "usuarios"
    private val CONTACTOS = "contactos"
    private val MISCHATS = "mischat"
    private val CHATS = "chats"

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

    override fun loadChatWithContact(idGoogleContact: String, onSuccess: (Usuario) -> Unit) {
        val auth = Firebase.auth.currentUser
        var chat: Usuario? = null

        if (auth != null) {
            db.collection(Usuario.USUARIOS).document(auth.uid).collection(MISCHATS)
                .get()
                .addOnSuccessListener { query ->
                    query.forEach { document ->
                        val chatContact = document.toObject<Usuario>()
                        if (chatContact.idGoogle.equals(idGoogleContact)) {
                            chat = chatContact
                        }
                    }
                    chat?.let { onSuccess(it) }
                }
                .addOnFailureListener { Log.d("JR LOG", "Error cargando chat con contacto") }
        }
    }

    override fun newChat(usuario: Usuario, contact: Usuario) {

        val datosNewChat = hashMapOf<String, Any>(
            "date" to Timestamp.now(),
            "lastMessage" to "",
            "isGrupo" to false,
            "title" to "",
            "subTitle" to "",
            "icon" to ""
        )

        db.collection(CHATS)
            .add(datosNewChat)
            .addOnSuccessListener { document ->
                val idChat = document.id //Obtengo el Id del Chat
                //En el Usuario
                val chatContacto = ChatUsuario(
                    idchat = idChat,
                    isGrupo = false,
                    idGoogle = contact.idGoogle,
                    nombre = contact.nombre,
                    icono = contact.avatar
                )
                db.collection(USUARIOS).document(usuario.idGoogle).collection(MISCHATS)
                    .document(idChat)
                    .set(chatContacto)
                    .addOnSuccessListener { Log.d("JR LOG","Chat creado en contactos del usuario") }
                //En el Contacto
                val chatUsuario = ChatUsuario(
                    idchat = idChat,
                    isGrupo = false,
                    idGoogle = usuario.idGoogle,
                    nombre = usuario.nombre,
                    icono = usuario.avatar
                )
                db.collection(USUARIOS).document(contact.idGoogle).collection(MISCHATS)
                    .document(idChat)
                    .set(chatUsuario)
                    .addOnSuccessListener { Log.d("JR LOG","Chat creado en contactos del contacto") }
            }
    }


}