package com.jrblanco.proyectoguachat.ui.registro

import android.net.Uri
import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toFile
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.jrblanco.proyectoguachat.modelo.UsuarioModel

class RegistroViewModel : ViewModel() {
    private val auth = Firebase.auth
    private val storage = Firebase.storage
    private val db = Firebase.firestore

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
        _isResgistroEnable.value = Patterns.EMAIL_ADDRESS.matcher(_email.value).matches() && _pass.value.equals(_vpass.value) && !_pass.value.isNullOrEmpty() && !_vpass.value.isNullOrEmpty()
        //  _isResgistroEnable.value = _email.value!!.isNotEmpty() && _pass.value!!.isNotEmpty()
    }

    fun onChangePass(pass: String, vpass: String) {
        _pass.value = pass
        _vpass.value = vpass
        _isResgistroEnable.value = Patterns.EMAIL_ADDRESS.matcher(_email.value).matches() && _pass.value.equals(_vpass.value) && !_pass.value.isNullOrEmpty() && !_vpass.value.isNullOrEmpty()

        //_isResgistroEnable.value = _email.value!!.isNotEmpty() && _pass.value!!.isNotEmpty()
    }

    fun onChangeImageUri(uri: Uri) {
        _imageUri.value = uri
    }

    /**
     * Método que crea una nueva autentificación
     * @return retorna en la función lambda los siguientes valores:
     *  0: Todo correcto
     *  1: El usuario que se quioere crear ya existe
     *  2: El email o la contraseña no son válidos
     */
    fun crearUsuario(funcion: (Int) -> Unit) {
        //Si el email es valido y es mayor o igual a 6 caracteres
        if (Patterns.EMAIL_ADDRESS.matcher(_email.value).matches() && _pass.value?.length!! >= 6) {
            auth.createUserWithEmailAndPassword(_email.value!!, _pass.value!!)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        guardarImagenFirebaseStorage()
                        guardarUsuarioFirestoreDatabase()
                        funcion(0)
                    } else funcion(1)
                }
                .addOnFailureListener {
                    Log.d("JR_LOG", "Error creando usuario")
                }
        } else funcion(2)
    }

    /**
     * Método que guarda en la base de datos de Firestore el usuario
     */
    private fun guardarUsuarioFirestoreDatabase() {
        val idGoogle = auth.currentUser?.uid
        val usuario = UsuarioModel(
            idGoogle = idGoogle!!,
            nombre = _nombre.value!!,
            email = _email.value!!,
            avatar = "${idGoogle}.imagen"
        )
        db.collection(UsuarioModel.USUARIOS)
            .document(usuario.idGoogle)
            .set(usuario)
            .addOnCompleteListener {
                if (it.isSuccessful) Log.d("JR_LOG", "Usuario guardado con éxito")
                else Log.d("JR_LOG", "Error al guardar el usuario. ${it.exception}")
            }
    }

    /**
     * Método que guarda en Firebase Storage la imagen del avatar
     * El nombre del fichero en el servidor es el ID de google más la terminación .imagen
     */
    private fun guardarImagenFirebaseStorage() {
        var storageRef = storage.reference //Obtiene la referencia

        if (imageUri.value != null) {
            val imageRef = storageRef.child("avatar_usuarios/${auth.uid}.imagen")
            imageRef.putFile(imageUri.value!!)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d("JR_LOG", "Imagen cargada con existo")
                    }
                }
                .addOnFailureListener {
                    Log.d("JR_LOG", "Error subiendo la imagen")
                }
        }
    }
}