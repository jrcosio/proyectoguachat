package com.jrblanco.proyectoguachat.domain.repository

import com.jrblanco.proyectoguachat.domain.model.ChatUsuario
import com.jrblanco.proyectoguachat.domain.model.Chats
import com.jrblanco.proyectoguachat.domain.model.Message
import com.jrblanco.proyectoguachat.domain.model.Usuario

interface DataBaseRepository {
    fun saveUser(user: Usuario, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
    fun loadUser(usuario: (Usuario) -> Unit)

    //---  Métodos para los Contactos
    fun allUser(onSuccess: (List<Usuario>) -> Unit)
    fun newContacto(idGoogle: String, contactoNuevo: Usuario, onSuccess: () -> Unit)
    fun allContactos(idGoogle: String, onSuccess: (List<Usuario>) -> Unit)
    fun loadContact(idGoogle: String, onSuccess: (Usuario) -> Unit)
    //--- Métodos para los chats ---
    fun loadChatWithContact(idGoogleContact: String, onSuccess: (ChatUsuario) -> Unit)
    fun newChat(usuario: Usuario, contact: Usuario, onSuccess: (String) -> Unit)
    fun sendMessageChat(messageToSend: Message, idChat: String)
    //--- Métodos del Listado de Chats de cada usuario
    fun loadListChats(usuario: Usuario, onSuccess: (List<Chats>) -> Unit)
}