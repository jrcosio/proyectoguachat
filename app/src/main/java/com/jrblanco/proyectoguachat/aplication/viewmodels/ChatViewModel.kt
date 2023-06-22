package com.jrblanco.proyectoguachat.aplication.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jrblanco.proyectoguachat.aplication.usecase.LoadChatWithContactUseCase
import com.jrblanco.proyectoguachat.aplication.usecase.LoadContactUseCase
import com.jrblanco.proyectoguachat.aplication.usecase.LoadMessageChatUseCase
import com.jrblanco.proyectoguachat.aplication.usecase.LoadUserDBUseCase
import com.jrblanco.proyectoguachat.aplication.usecase.NewChatUseCase
import com.jrblanco.proyectoguachat.aplication.usecase.SendMessageChatUseCase
import com.jrblanco.proyectoguachat.domain.model.Message
import com.jrblanco.proyectoguachat.domain.model.Usuario
import com.jrblanco.proyectoguachat.infraestructure.FirestoreDatabaseRepository

class ChatViewModel : ViewModel() {
    //--- Repositorios a la BD
    private val dataBaseRepository = FirestoreDatabaseRepository()

    //--- Casos de uso
    private val loadContactUseCase = LoadContactUseCase(dataBaseRepository)
    private val loadChatWithContactUseCase = LoadChatWithContactUseCase(dataBaseRepository)
    private val loadUserDBUseCase = LoadUserDBUseCase(dataBaseRepository)
    private val newChatUseCase = NewChatUseCase(dataBaseRepository)
    private val sendMessageChatUseCase = SendMessageChatUseCase(dataBaseRepository)
    private val loadMessageChatUseCase = LoadMessageChatUseCase(dataBaseRepository)

    val auth = Firebase.auth.currentUser

    private val _usuario = MutableLiveData<Usuario>()
    val usuario: LiveData<Usuario> = _usuario

    private val _contact = MutableLiveData<Usuario>()
    val contact: LiveData<Usuario> = _contact

    private val _noHasMessages = MutableLiveData<Boolean>()
    val noHasMessages: LiveData<Boolean> = _noHasMessages

    private val _messageText = MutableLiveData<String>()
    val messageText: LiveData<String> = _messageText

    private var idChat = ""

    private val _listMessagesChat = MutableLiveData<List<Message>>()
    val listMessagesChat: LiveData<List<Message>> = _listMessagesChat

    fun onChangeMessageText(valor: String) {
        _messageText.value = valor
    }

    fun initChatData(idContact: String) {
        loadContactUseCase(idContact) {
            _contact.value = it
            recoverMessages(idContact)
        }
        loadUserDBUseCase {
            _usuario.value = it
        }
    }

    fun recoverMessages(idContact: String) {
        _noHasMessages.value = true
        loadChatWithContactUseCase(idContact) {
            _noHasMessages.value = false
            this.idChat = it.idchat
            //si no tengo par de clave generado Genera y envia
            loadMessageChatUseCase(this.idChat) {mnj ->
                //desencripta con clave publica dell contact
                addMensaje(mnj)
            }
        }
    }
    private fun addMensaje(message: Message){
        val listMensajes = _listMessagesChat.value?.toMutableList() ?: mutableListOf()
        listMensajes.add(message)
        _listMessagesChat.value = listMensajes
    }

    fun sendMessage() {
        if (_messageText.value != null) {
            if (_messageText.value?.isNotBlank() == true && _messageText.value?.isNotEmpty()!!) {
                val mensajeToSend = Message(
                    idGoogle = _usuario.value?.idGoogle.toString(),
                    mensaje = _messageText.value.toString(),
                    nombre = _usuario.value?.nombre.toString(),
                    imagen = "",
                    fecha = Timestamp.now()
                )

                if (_noHasMessages.value == true) {
                    //Genera par de clave
                    newChatUseCase(usuario.value!!, contact.value!!) {
                        this.idChat = it
                        //Encripta mensajes con mi clave privada
                        sendMessageChatUseCase(mensajeToSend, this.idChat)
                        loadMessageChatUseCase(this.idChat) {mnj ->
                            //desencripta con clave publica del Contact
                            addMensaje(mnj)
                        }
                    }
                    _noHasMessages.value = false
                } else {
                    sendMessageChatUseCase(mensajeToSend, this.idChat)
                }
                onChangeMessageText("") //Limpia el mensaje
            }
        }
    }

}