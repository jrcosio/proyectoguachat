package com.jrblanco.proyectoguachat.infraestructure

import android.util.Log
import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.jrblanco.proyectoguachat.domain.repository.LoginRepository
import com.jrblanco.proyectoguachat.domain.repository.RegistroRepository
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepository : LoginRepository, RegistroRepository {

    var auth = FirebaseAuth.getInstance()
        private set     //solo de lectura desde fuera de la clase

    override suspend fun loginClassic(email: String, password: String): Boolean {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            result.user != null
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun loginGoogle(idToken: String): Boolean {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val result = auth.signInWithCredential(credential).await()
            result.user != null
        } catch (e: Exception) {
            false
        }
    }

    override fun newPassword(password: String) {
        auth.currentUser?.updatePassword(password)?.addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d("JR LOG", "Password cambiado con éxito")
                } else {
                    Log.d("JR LOG", "Error cambiando Password")
                }

            }
    }

    override suspend fun newUser(email: String, password: String): Boolean {
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            return false
        }
        //Si el email es valido y es mayor o igual a 6 caracteres
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length!! >= 6) {
            try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                return result.user != null
            } catch (e: Exception) {
                return false
            }
        }
        return false
    }
}