package com.jrblanco.proyectoguachat.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.jrblanco.proyectoguachat.modelo.RutasNav
import com.jrblanco.proyectoguachat.ui.login.LoginView
import com.jrblanco.proyectoguachat.ui.login.LoginViewModel
import com.jrblanco.proyectoguachat.ui.principal.HomeView
import com.jrblanco.proyectoguachat.ui.registro.RegistroView
import com.jrblanco.proyectoguachat.ui.registro.RegistroViewModel

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun Navegacion(navControl: NavHostController) {

    //Comprobamos si el dispositivo tiene iniciada la sesión de ser así
    //cambiamos la ruta de inicio y se salta el Login
    val usuario = FirebaseAuth.getInstance().currentUser
    val ruta = if (usuario != null) RutasNav.Home.route else RutasNav.Login.route

    NavHost(navController = navControl, startDestination = ruta) {
        composable(RutasNav.Login.route) { LoginView(navControl, LoginViewModel()) }
        composable(RutasNav.Registro.route) { RegistroView(navControl, RegistroViewModel())}
        composable(RutasNav.Home.route) { HomeView() }
    }
}