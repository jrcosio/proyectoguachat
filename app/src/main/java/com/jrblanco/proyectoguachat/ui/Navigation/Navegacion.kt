package com.jrblanco.proyectoguachat.ui.Navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jrblanco.proyectoguachat.modelo.RutasNav
import com.jrblanco.proyectoguachat.ui.login.LoginView
import com.jrblanco.proyectoguachat.ui.login.LoginViewModel
import com.jrblanco.proyectoguachat.ui.registro.RegistroView

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun Navegacion(navControl: NavHostController) {
    NavHost(navController = navControl, startDestination = RutasNav.Login.route) {
        composable(RutasNav.Login.route) { LoginView(navControl, LoginViewModel()) }
        composable(RutasNav.Registro.route) { RegistroView(navControl)}
    }
}