package com.jrblanco.proyectoguachat.infraestructure

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.jrblanco.proyectoguachat.domain.model.ChatUsuario
import com.jrblanco.proyectoguachat.domain.model.Chats
import com.jrblanco.proyectoguachat.domain.model.Message
import com.jrblanco.proyectoguachat.domain.model.Usuario
import com.jrblanco.proyectoguachat.domain.repository.DataBaseRepository

class FirestoreDatabaseRepository : DataBaseRepository {
    private val USUARIOS = "usuarios"
    private val CONTACTOS = "contactos"
    private val MISCHATS = "mischat"
    private val CHATS = "chats"
    private val MENSAJES = "mensajes"

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

    override fun loadChatWithContact(idGoogleContact: String, onSuccess: (ChatUsuario) -> Unit) {
        val auth = Firebase.auth.currentUser
        var chat: ChatUsuario? = null

        if (auth != null) {
            db.collection(Usuario.USUARIOS).document(auth.uid).collection(MISCHATS)
                .get()
                .addOnSuccessListener { query ->
                    query.forEach { document ->
                        val chatContact = document.toObject<ChatUsuario>()
                        if (chatContact.idGoogle.equals(idGoogleContact)) {
                            chat = chatContact
                        }
                    }
                    chat?.let { onSuccess(it) }
                }
                .addOnFailureListener { Log.d("JR LOG", "Error cargando chat con contacto") }
        }
    }

    override fun newChat(usuario: Usuario, contact: Usuario, onSuccess: (String) -> Unit) {

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
                    icono = contact.avatar //??
                )
                db.collection(USUARIOS).document(usuario.idGoogle).collection(MISCHATS)
                    .document(idChat)
                    .set(chatContacto)
                    .addOnSuccessListener {
                        Log.d(
                            "JR LOG",
                            "Chat creado en contactos del usuario"
                        )
                    }
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
                    .addOnSuccessListener {
                        Log.d(
                            "JR LOG",
                            "Chat creado en contactos del contacto"
                        )
                    }
                onSuccess(idChat)
            }
            .addOnFailureListener { Log.e("JR LOG", "Error creando CHAT") }
    }


    override fun sendMessageChat(messageToSend: Message, idChat: String) {

        db.collection(CHATS).document(idChat).collection(MENSAJES)
            .add(messageToSend)
            .addOnSuccessListener {
                //Actualiza la info del hilo padre
                val datosForChat = hashMapOf<String, Any>(
                    "lastMessage" to messageToSend.mensaje,
                    "date" to messageToSend.fecha!!,
                    "subTitle" to messageToSend.nombre,
                )
                db.collection(CHATS).document(idChat)
                    .update(datosForChat)
            }
            .addOnFailureListener { Log.e("JR LOG", "Error enviando/guardando mensaje") }
    }

    override fun loadMessageChat(idChat: String, onSuccess: (Message) -> Unit) {
        db.collection(CHATS).document(idChat).collection(MENSAJES)
            .orderBy("fecha", Query.Direction.ASCENDING)
            .addSnapshotListener{ snapShot, ex ->
                if (ex != null) {
                    Log.e("JR LOG", "Error en el listener del loadMessageChat")
                    return@addSnapshotListener
                }
                if (snapShot != null && !snapShot.isEmpty){
                    snapShot.documentChanges.forEach{dc ->
                        if (dc.type == DocumentChange.Type.ADDED) {
                            val mensaje = dc.document.toObject<Message>()
                            onSuccess(mensaje)
                        }
                    }
                }
            }
    }

    override fun loadListChats(usuario: Usuario, onSuccess: (List<Chats>) -> Unit) {
        val listMisChats = mutableListOf<ChatUsuario>()
        val listChats = mutableListOf<Chats>()

        db.collection(USUARIOS).document(usuario.idGoogle).collection(MISCHATS)
            .addSnapshotListener{ snapShot, ex ->
                if (ex != null) {
                    Log.e("JR LOG", "Error en el listener del loadMessageChat")
                    return@addSnapshotListener
                }
                if (snapShot != null && !snapShot.isEmpty){
                    snapShot.documentChanges.forEach { dc ->
                        val chat = dc.document.toObject<ChatUsuario>()
                        listMisChats.add(chat)
                    }
                    db.collection(CHATS)
                        .addSnapshotListener{ snapShot, ex ->
                            if (ex != null) {
                                Log.e("JR LOG", "Error en el listener del loadMessageChat")
                                return@addSnapshotListener
                            }
                            if (snapShot != null && !snapShot.isEmpty){
                                for (document in snapShot.documentChanges) {
                                    for (mischat in listMisChats) {
                                        if (document.document.id.equals(mischat.idchat)) {
                                            val chat = document.document.toObject<Chats>()
                                            chat.idChat = document.document.id
                                            chat.icon = mischat.icono
                                            chat.title = mischat.nombre
                                            chat.idGoogle = mischat.idGoogle
                                            listChats.add(chat)
                                        }
                                    }
                                }
                                onSuccess(listChats)
                            }
                        }
                }
            }
//        db.collection(USUARIOS).document(usuario.idGoogle).collection(MISCHATS)
//            .get()
//            .addOnSuccessListener { docMisChat ->
//                docMisChat.forEach { chat ->
//                    val chat = chat.toObject<ChatUsuario>()
//                    listMisChats.add(chat)
//                }
//
//                db.collection(CHATS)
//                    .get()
//                    .addOnSuccessListener {
//                        for (document in it) {
//                            for (mischat in listMisChats) {
//
//                                if (document.id.equals(mischat.idchat)) {
//                                    val chat = document.toObject<Chats>()
//                                    chat.idChat = document.id
//                                    chat.icon = mischat.icono
//                                    chat.title = mischat.nombre
//                                    chat.idGoogle = mischat.idGoogle
//                                    listChats.add(chat)
//                                }
//                            }
//                        }
//                        onSuccess(listChats)
//                    }
//
//            }
    }
}
