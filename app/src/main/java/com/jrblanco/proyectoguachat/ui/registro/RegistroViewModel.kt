package com.jrblanco.proyectoguachat.ui.registro

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jrblanco.proyectoguachat.modelo.UsuarioModel

class RegistroViewModel : ViewModel() {

    private val _usuario = MutableLiveData<UsuarioModel>()
    val usuario: LiveData<UsuarioModel> = _usuario

    /*private val _nombre = MutableLiveData<String>()
    val nombre: LiveData<String> = _nombre

    private val _apodo = MutableLiveData<String>()
    val apodo: LiveData<String> = _apodo

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _pass = MutableLiveData<String>()
    val pass: LiveData<String> = _pass

    private val _imageUri = MutableLiveData<Uri>()
    val imageUri: LiveData<Uri> = _imageUri
*/
    fun onChangeNombre(nomb:String){
        _usuario.value!!.nombre = nomb
    }
    fun onChangeEmail(nomb:String){
        _usuario.value!!.email = nomb
    }
    fun onChangePass(nomb:String){
        _usuario.value!!.pass = nomb
    }
}