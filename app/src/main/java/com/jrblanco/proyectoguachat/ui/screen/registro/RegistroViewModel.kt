package com.jrblanco.proyectoguachat.ui.screen.registro

import android.net.Uri
import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jrblanco.proyectoguachat.data.firebase.FirebaseAuthRepository
import com.jrblanco.proyectoguachat.data.firebase.FirebaseStorageRepository
import com.jrblanco.proyectoguachat.data.firebase.FirestoreDatabaseRepository
import com.jrblanco.proyectoguachat.data.model.UsuarioModel
import kotlinx.coroutines.launch

class RegistroViewModel : ViewModel() {
    private val auth = Firebase.auth
    private val db = Firebase.firestore

    private val loginRepository = FirebaseAuthRepository()
    private val storageRepository = FirebaseStorageRepository()
    private val firestoreDBRepository = FirestoreDatabaseRepository()

    private val _nombre = MutableLiveData<String>("")
    val nombre: LiveData<String> = _nombre

    private val _email = MutableLiveData<String>("")
    val email: LiveData<String> = _email

    private val _pass = MutableLiveData<String>("")
    val pass: LiveData<String> = _pass

    private val _vpass = MutableLiveData<String>("")
    val vpass: LiveData<String> = _vpass

    private val _imageUri = MutableLiveData<Uri>(null)
    val imageUri: LiveData<Uri> = _imageUri

    private val imagenUrl = mutableStateOf("")

    private val _isResgistroEnable = MutableLiveData<Boolean>(false)
    val isResgistroEnable: LiveData<Boolean> = _isResgistroEnable

    fun onChangeNombre(nomb: String) {
        _nombre.value = nomb
    }

    fun onChangeEmail(email: String) {
        _email.value = email
        _isResgistroEnable.value = Patterns.EMAIL_ADDRESS.matcher(_email.value)
            .matches() && _pass.value.equals(_vpass.value) && !_pass.value.isNullOrEmpty() && !_vpass.value.isNullOrEmpty()
        //  _isResgistroEnable.value = _email.value!!.isNotEmpty() && _pass.value!!.isNotEmpty()
    }

    fun onChangePass(pass: String, vpass: String) {
        _pass.value = pass
        _vpass.value = vpass
        _isResgistroEnable.value = Patterns.EMAIL_ADDRESS.matcher(_email.value)
            .matches() && _pass.value.equals(_vpass.value) && !_pass.value.isNullOrEmpty() && !_vpass.value.isNullOrEmpty()

        //_isResgistroEnable.value = _email.value!!.isNotEmpty() && _pass.value!!.isNotEmpty()
    }

    fun onChangeImageUri(uri: Uri) {
        _imageUri.value = uri
    }

    /**
     * Método que crea una nueva autentificación
     * @return retorna en la función lambda los siguientes valores:
     *  0: Todo correcto
     *  1: El usuario que se quiere crear ya existe
     *  2: El email o la contraseña no son válidos
     */
    fun crearUsuario(funcion: (Int) -> Unit) = viewModelScope.launch {
        try {
            val isSuccessful = loginRepository.newUserLogin(_email.value!!, _pass.value!!)
            if (isSuccessful) {
                guardarImagenUsuario(imageUri.value)    //Guardar Imagen
                val usuario = UsuarioModel(nombre = _nombre.value!!,email = _email.value!!)
                guardarUsuarioDB(usuario)               //Guardar Usuario BD
                funcion(0)
            } else {
                Log.d("JR_LOG", "Error creando usuario")
                funcion(1)
            }
        } catch (e: Exception) {
            Log.d("JR_LOG", "Error creando usuario ${e.message}")
            funcion(2)
        }

    }

    /**
     * Método que guarda en la base de datos de Firestore el usuario
     */
    private fun guardarUsuarioDB(user: UsuarioModel) {
        firestoreDBRepository.saveUser(user,
            onSuccess = {Log.d("JR_LOG", "Usuario guardado con éxito")},
            onFailure = {Log.d("JR_LOG", "Error al guardar el usuario. ${it.message}")})
    }

    /**
     * Método que guarda en Firebase Storage la imagen del avatar
     * El nombre del fichero en el servidor es el ID de google más la terminación .imagen
     */
    private fun guardarImagenUsuario(imagen: Uri?) {
        storageRepository.saveImagen(imagen,
            onSuccess = {Log.d("JR_LOG", "Imagen cargada con existo")},
            onFailure = {Log.d("JR_LOG", "Error subiendo la imagen")})
    }

}