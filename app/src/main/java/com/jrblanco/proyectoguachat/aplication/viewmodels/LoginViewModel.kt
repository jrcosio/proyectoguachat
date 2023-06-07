package com.jrblanco.proyectoguachat.aplication.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.jrblanco.proyectoguachat.aplication.usecase.GuardarUserDBUseCase
import com.jrblanco.proyectoguachat.aplication.usecase.LoginUseCase
import com.jrblanco.proyectoguachat.aplication.usecase.RegistroUseCase
import com.jrblanco.proyectoguachat.infraestructure.FirebaseAuthRepository
import com.jrblanco.proyectoguachat.infraestructure.FirestoreDatabaseRepository
import com.jrblanco.proyectoguachat.domain.model.Usuario
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val loginFirebaseRepository = FirebaseAuthRepository();
    private val dataBaseRepository = FirestoreDatabaseRepository()

    //---- Instaciamos los casos de uso
    private val loginUseCase = LoginUseCase(loginFirebaseRepository)    //Inyecto el repository
    private val guardarUserUseCase = GuardarUserDBUseCase(dataBaseRepository)

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
            user.isNotEmpty() && pass.isNotEmpty()     //Activa el botón de Iniciar sesión si no están vacios
    }

    /**
     * Método que hace login en el servidor de FireBase
     * @param Expresión lambda que se lanza cuando el login es correcto.
     */

    fun login(loginOk: () -> Unit) = viewModelScope.launch {
        try {
            val isSuccessful = loginUseCase.login(_user.value!!, _password.value!!)
            if (isSuccessful) {
                loginOk()
                _isErrorLogin.value = false
            } else {
                _isErrorLogin.value = true
            }
        } catch (e: Exception) {
            Log.d("JR - Log", e.message!!)
            _isErrorLogin.value = true
        }
    }

    /**
     * Método para iniciar sesión con cuenta de google (@gmail.com)
     */
    fun loginConGoogle(account: GoogleSignInAccount?, home: () -> Unit) = viewModelScope.launch {
        try {

            val idToken = account?.idToken

            val isSuccessful = idToken?.let { loginUseCase.loginGoogle(it) }
            if (isSuccessful == true) {
                Log.d("JR - LOG", "Login con google: CORRECTO")
                //Guardar datos en la base de datos
                //  val usuario = UsuarioModel(nombre = _nombre.value!!,email = _email.value!!)
                val auth = loginUseCase.auth
                val usuario = Usuario(nombre = auth.currentUser?.displayName.toString(),email = auth.currentUser?.email.toString())
                guardarUserUseCase.saveUser(usuario,
                        onSuccess = {Log.d("JR_LOG", "Usuario guardado con éxito")},
                        onFailure = { Log.d("JR_LOG", "Error al guardar el usuario. ${it.message}") })

                home()
            }
        } catch (e: Exception) {
            Log.d("JR - LOG", e.message!!)
        }
    }


}