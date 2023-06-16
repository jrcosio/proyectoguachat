package com.jrblanco.proyectoguachat.aplication.viewmodels

import android.icu.util.MeasureUnit
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.jrblanco.proyectoguachat.aplication.usecase.LoadChatWithContactUseCase
import com.jrblanco.proyectoguachat.aplication.usecase.LoadContactUseCase
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

    private val _usuario = MutableLiveData<Usuario>()
    val usuario: LiveData<Usuario> = _usuario

    private val _contact = MutableLiveData<Usuario>()
    val contact: LiveData<Usuario> = _contact

    private val _noHasMessages = MutableLiveData<Boolean>()
    val noHasMessages: LiveData<Boolean> = _noHasMessages

    private val _messageText = MutableLiveData<String>()
    val messageText: LiveData<String> = _messageText

    private var idChat = ""

    fun onChangeMessageText(valor: String) {
        _messageText.value = valor
    }

    fun setContact(idContact: String) {
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
        }
    }

    fun sendMessage() {
        if (_messageText.value?.isNotBlank()!! && _messageText.value?.isNotEmpty()!!) {
            val mensajeToSend = Message(
                idGoogle = _usuario.value?.idGoogle.toString(),
                mensaje = _messageText.value.toString(),
                nombre = _usuario.value?.nombre.toString(),
                imagen = "",
                fecha = Timestamp.now()
            )

            if (_noHasMessages.value == true) {
                newChatUseCase(usuario.value!!, contact.value!!) {
                    this.idChat = it
                    sendMessageChatUseCase(mensajeToSend, this.idChat)
                }
                _noHasMessages.value = false
            } else {
                sendMessageChatUseCase(mensajeToSend, this.idChat)
            }
            onChangeMessageText("") //Limpia el mensaje
        }
    }

}