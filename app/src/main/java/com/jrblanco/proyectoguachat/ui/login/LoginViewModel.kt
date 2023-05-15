package com.jrblanco.proyectoguachat.ui.login

import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel : ViewModel() {
    public val auth: FirebaseAuth = Firebase.auth

    private val _user = MutableLiveData<String>()
    val user: LiveData<String> = _user

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _isLoginEnable = MutableLiveData<Boolean>()
    val isLoginEnable: LiveData<Boolean> = _isLoginEnable

    private val _isErrorLogin = MutableLiveData<Boolean>()
    val isErrorLogin: LiveData<Boolean> = _isErrorLogin

    /**
     * Función que se llama cuando se cambia el user o el password
     */
    fun onLoginChange(user: String, pass: String) {
        _user.value = user
        _password.value = pass
        _isLoginEnable.value =
            user.isNotEmpty() && pass.isNotEmpty()       //Activa el botón de Iniciar sesión si no están vacios
    }

    /**
     * Método que hace login en el servidor de FireBase
     * @param Expresión lambda que se lanza cuando el login es correcto.
     */
    fun login(loginOk: () -> Unit) = viewModelScope.launch {
        try {
            auth.signInWithEmailAndPassword(_user.value!!, _password.value!!)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        loginOk()
                        _isErrorLogin.value = false
                    } else { _isErrorLogin.value = true }
                }
        } catch (e: Exception) {
            Log.d("JR - Log", e.message!!)
            _isErrorLogin.value = true
        }
    }

    /**
     * Método para iniciar sesión con cuenta de google (@gmail.com)
     */
    fun loginConGoogle(credential: AuthCredential, home: () -> Unit)
    = viewModelScope.launch {
        try {
            auth.signInWithCredential(credential)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d("JR - LOG","Login con google: CORRECTO")
                        //Guardar datos en la base de datos
                        home()
                    }
                }
                .addOnFailureListener {
                    Log.d("JR - LOG","Fallo en el login con cuenta Google")
                }
        } catch (e:Exception) {
            Log.d("JR - LOG",e.message!!)
        }

    }

}