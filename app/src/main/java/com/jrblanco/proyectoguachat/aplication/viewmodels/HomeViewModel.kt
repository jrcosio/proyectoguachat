package com.jrblanco.proyectoguachat.aplication.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.jrblanco.proyectoguachat.R
import com.jrblanco.proyectoguachat.aplication.usecase.AllContactUserBDUseCase
import com.jrblanco.proyectoguachat.aplication.usecase.AllUsersDBUseCase
import com.jrblanco.proyectoguachat.aplication.usecase.LoadListChatUserUseCase
import com.jrblanco.proyectoguachat.aplication.usecase.LoadUserDBUseCase
import com.jrblanco.proyectoguachat.aplication.usecase.NewContactUserCase
import com.jrblanco.proyectoguachat.aplication.usecase.ObtenerImagenUseCase
import com.jrblanco.proyectoguachat.domain.model.Chats
import com.jrblanco.proyectoguachat.domain.model.Usuario
import com.jrblanco.proyectoguachat.infraestructure.FirebaseStorageRepository
import com.jrblanco.proyectoguachat.infraestructure.FirestoreDatabaseRepository

class HomeViewModel : ViewModel() {
    private val dataBaseRepository = FirestoreDatabaseRepository()
    //private val storageRepository = FirebaseStorageRepository()

    //---- Casos de uso ----
    private val loadUserUseCase = LoadUserDBUseCase(dataBaseRepository)
    private val allUsersDBUseCase = AllUsersDBUseCase(dataBaseRepository)
    private val newContactUserCase = NewContactUserCase(dataBaseRepository)
    private val allContactUserBDUseCase = AllContactUserBDUseCase(dataBaseRepository)

    //private val obtenerImagenUseCase = ObtenerImagenUseCase(storageRepository)
    private val loadListChatUserUseCase = LoadListChatUserUseCase(dataBaseRepository)

    private val _seccion = MutableLiveData<Int>()
    val seccion: LiveData<Int> = _seccion

    private val _textSearch = MutableLiveData<String>()
    val textoSearch: LiveData<String> = _textSearch

    private val _isSearch = MutableLiveData<Boolean>()
    val isSearch: LiveData<Boolean> = _isSearch

    private val _isNewChat = MutableLiveData<Boolean>()
    val isNewChat: LiveData<Boolean> = _isNewChat

    private val _isAddContact = MutableLiveData<Boolean>()
    val isAddContact: LiveData<Boolean> = _isAddContact

    private val _textAddContact = MutableLiveData<String>()
    val textAddContact: LiveData<String> = _textAddContact

    private val _usuario = MutableLiveData<Usuario>()
    val usuario: LiveData<Usuario> = _usuario

    private val _allUsuario = MutableLiveData<List<Usuario>>()
    val allUsuario: LiveData<List<Usuario>> = _allUsuario

    private val _allContacts = MutableLiveData<List<Usuario>>()
    val allContacts: LiveData<List<Usuario>> = _allContacts

    private val _listaChats = MutableLiveData<List<Chats>>()
    val listaChats: LiveData<List<Chats>> = _listaChats

    init {
        initUsuario()
    }

    fun onSeccionChange(valor: Int) {
        _seccion.value = valor
    }

    fun onTextoSearch(valor: String) {
        _textSearch.value = valor
    }

    fun changeIsSearch(valor: Boolean) {
        _isSearch.value = valor
        if (valor) _isAddContact.value = false
    }

    fun onTextAddContact(valor: String) {
        _textAddContact.value = valor
    }

    fun changeIsAddContact(valor: Boolean) {
        _isAddContact.value = valor
        if (valor) _isSearch.value = false
    }

    fun changeIsNewChat(valor: Boolean) {
        _isNewChat.value = valor
    }

    fun resetAll() {
        _isSearch.value = false
        _textSearch.value = ""
        _isAddContact.value = false
        _textAddContact.value = ""
    }

    /**
     * Método para cerrar la sesión actual
     * @param Se le pasa el contexto de la aplicación que se obtiene con el LocalContext.current
     */
    fun cerrarSesion(context: Context) {
        val opciones = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            //.requestIdToken(stringResource(id = R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSingInCliente = GoogleSignIn.getClient(context, opciones)

        //Esto se quita cuando este todo operativo
        googleSingInCliente.signOut()
        FirebaseAuth.getInstance().signOut()
    }

    fun initUsuario() {
        loadUserUseCase {
            _usuario.value = it
            getAllChatUser()
            getAllContact()
        }
    }

    fun getAllUsers() {
        allUsersDBUseCase { _allUsuario.value = it }
    }

    fun newContacto(nuevoContacto: Usuario, onSuccess: () -> Unit) {
        if (_usuario.value!!.idGoogle != nuevoContacto.idGoogle)
            newContactUserCase(_usuario.value!!.idGoogle, nuevoContacto) { onSuccess() }
    }

    fun getAllContact() {
        allContactUserBDUseCase(_usuario.value!!.idGoogle) {
            _allContacts.value = it
        }
    }

    fun getAllChatUser() {
        _usuario.value?.let {
            loadListChatUserUseCase(it) { list ->
                _listaChats.value = list
            }
        }
    }

}