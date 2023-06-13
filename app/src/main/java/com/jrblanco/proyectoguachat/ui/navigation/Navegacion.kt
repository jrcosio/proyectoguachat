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
import com.jrblanco.proyectoguachat.aplication.viewmodels.ChatViewModel
import com.jrblanco.proyectoguachat.aplication.viewmodels.HomeViewModel
import com.jrblanco.proyectoguachat.modelo.RutasNav
import com.jrblanco.proyectoguachat.ui.screen.login.LoginView
import com.jrblanco.proyectoguachat.aplication.viewmodels.LoginViewModel
import com.jrblanco.proyectoguachat.ui.screen.principal.HomeView
import com.jrblanco.proyectoguachat.ui.screen.registro.RegistroView
import com.jrblanco.proyectoguachat.aplication.viewmodels.RegistroViewModel
import com.jrblanco.proyectoguachat.ui.screens.ChatView
import com.jrblanco.proyectoguachat.ui.screens.SplashView

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

    NavHost(navController = navControl, startDestination = RutasNav.Splash.route) {
        composable(RutasNav.Splash.route) {
            SplashView {
                //Comprobamos si el dispositivo tiene iniciada la sesión de ser así
                //cambiamos la ruta de inicio y se salta el Login
                val usuario = FirebaseAuth.getInstance().currentUser
                if (usuario != null) {
                    navControl.navigate(RutasNav.Home.route, builder = {
                        popUpTo(RutasNav.Splash.route) {
                            inclusive = true
                        }      //Limpia la pila de navegación
                    })
                } else {
                    navControl.navigate(RutasNav.Login.route, builder = {
                        popUpTo(RutasNav.Splash.route) {
                            inclusive = true
                        }      //Limpia la pila de navegación
                    })
                }
            }
        }
        composable(RutasNav.Login.route) { LoginView(navControl = navControl, loginViewModel = LoginViewModel()) }
        composable(RutasNav.Registro.route) { RegistroView(navControl = navControl, regViewModel = RegistroViewModel()) }
        composable(RutasNav.Home.route) { HomeView(navControl = navControl, viewModel = HomeViewModel()) }
        composable(RutasNav.Chat.route) {
            val userId = it.arguments?.getString("idGoogle").orEmpty()
            ChatView(navControl = navControl, viewModel = ChatViewModel(), idGoogle = userId)
        }
    }
}