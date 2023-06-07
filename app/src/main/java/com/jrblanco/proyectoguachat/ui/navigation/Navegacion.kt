package com.jrblanco.proyectoguachat.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.jrblanco.proyectoguachat.R
import com.jrblanco.proyectoguachat.modelo.RutasNav
import com.jrblanco.proyectoguachat.ui.screen.login.LoginView
import com.jrblanco.proyectoguachat.login.application.viewmodel.LoginViewModel
import com.jrblanco.proyectoguachat.ui.screen.principal.HomeView
import com.jrblanco.proyectoguachat.ui.screen.registro.RegistroView
import com.jrblanco.proyectoguachat.registro.application.viewmodel.RegistroViewModel

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun Navegacion(navControl: NavHostController) {

    val opciones = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(stringResource(id = R.string.default_web_client_id))
        .requestEmail()
        .build()

    val googleSingInCliente = GoogleSignIn.getClient(LocalContext.current, opciones)

    //Esto se quita cuando este todo operativo
    googleSingInCliente.signOut()
    FirebaseAuth.getInstance().signOut()


    //Comprobamos si el dispositivo tiene iniciada la sesión de ser así
    //cambiamos la ruta de inicio y se salta el Login
    val usuario = FirebaseAuth.getInstance().currentUser
    val ruta = if (usuario != null) RutasNav.Home.route else RutasNav.Login.route

    NavHost(navController = navControl, startDestination = ruta) {
        composable(RutasNav.Login.route) { LoginView(navControl, LoginViewModel()) }
        composable(RutasNav.Registro.route) { RegistroView(navControl, RegistroViewModel()) }
        composable(RutasNav.Home.route) { HomeView() }
    }
}