package com.jrblanco.proyectoguachat.ui.login

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.jrblanco.proyectoguachat.R
import com.jrblanco.proyectoguachat.modelo.RutasNav
import com.jrblanco.proyectoguachat.ui.componentes.TextFieldEmail
import com.jrblanco.proyectoguachat.ui.componentes.TextFieldPassword
import com.jrblanco.proyectoguachat.ui.theme.Green50
import com.jrblanco.proyectoguachat.ui.theme.Pink80
import com.jrblanco.proyectoguachat.ui.theme.Red60

@Composable
fun LoginView(navControl: NavHostController, loginViewModel: LoginViewModel) {

    val user by loginViewModel.user.observeAsState(initial = "")
    val pass by loginViewModel.password.observeAsState(initial = "")
    val isLoginEnable by loginViewModel.isLoginEnable.observeAsState(initial = false)
    val isErrorLogin by loginViewModel.isErrorLogin.observeAsState(initial = false)

    val context = LocalContext.current

    val googleLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            loginViewModel.loginConGoogle(credential) {
                navControl.navigate(RutasNav.Home.route, builder = {
                    popUpTo(RutasNav.Login.route) {
                        inclusive = true
                    }      //Limpia la pila de navegación
                })

            }
        } catch (e: Exception){  Log.d("JR - LOG", "GoogleSignIn falló: ${e.message!!}") }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            LogoApp()
            Spacer(modifier = Modifier.height(if (!isErrorLogin) 50.dp else 20.dp))

            LoginError(isErrorLogin)

            TextFieldEmail(value = user, texto = stringResource(R.string.usuario_login)) {
                loginViewModel.onLoginChange(user = it, pass = pass)
            }
            TextFieldPassword(value = pass, texto = stringResource(R.string.password_login)) {
                loginViewModel.onLoginChange(user = user, pass = it)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = stringResource(R.string.no_tengo_usuario_login),
                    modifier = Modifier.padding(top = 10.dp, bottom = 20.dp),
                    textAlign = TextAlign.End
                )
                Text(
                    text = stringResource(R.string.quiero_registrarme_login),
                    modifier = Modifier
                        .padding(end = 30.dp, top = 10.dp, bottom = 20.dp)
                        .clickable { navControl.navigate(RutasNav.Registro.route) },
                    textAlign = TextAlign.End,
                    color = Green50
                )
            }
            BotonInicioSesion(isLoginEnable) {
                loginViewModel.login {
                    navControl.navigate(RutasNav.Home.route, builder = {
                        popUpTo(RutasNav.Login.route) {
                            inclusive = true
                        }      //Limpia la pila de navegación
                    })
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Divider(
                color = Color.Black,
                thickness = 1.dp,
                modifier = Modifier
                    .padding(vertical = 20.dp, horizontal = 30.dp)
                    .fillMaxWidth()
            )

            Text(
                text = stringResource(R.string.indentificarse_con_login),
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .clickable { /*TODO*/ },
                textAlign = TextAlign.Center,
            )

            BotonInicioGoogle() {
                val token =  "488953975550-9rhuf6vopf8fdaqbeqn5b86i8e9fn3rb.apps.googleusercontent.com"

                val opciones = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(token)
                    .requestEmail()
                    .build()
                val googleSingInCliente = GoogleSignIn.getClient(context, opciones)
                googleLauncher.launch(googleSingInCliente.signInIntent)
            }

        }
    }
}

/**
 * Método que muestra en pantalla el mensaje de error de login o contraseña
 */
@Composable
fun LoginError(isErrorLogin: Boolean) {
    if (isErrorLogin) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(Pink80)
        ) {
            Column(modifier = Modifier.padding(5.dp)) {
                Row {
                    Icon(
                        imageVector = Icons.Rounded.Warning,
                        contentDescription = "Error",
                        tint = Red60
                    )
                    Text(
                        text = "Usuario o contraseña incorrecto",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Red60
                    )
                }
                Text(
                    text = "Compruebe que ha introducido",
                    modifier = Modifier.padding(horizontal = 24.dp),
                    color = Red60,
                    fontSize = 16.sp
                )
                Text(
                    text = "correctamente el usuario y contraseña",
                    modifier = Modifier.padding(horizontal = 24.dp),
                    color = Red60,
                    fontSize = 16.sp
                )
                Text(
                    text = "e intente de nuevo.",
                    modifier = Modifier.padding(horizontal = 24.dp),
                    color = Red60,
                    fontSize = 16.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}

/**
 * Botón para iniciar sesión con usuario y contraseña
 */
@Composable
private fun BotonInicioSesion(isEnable: Boolean, onClickLogin: () -> Unit) {
    Button(
        onClick = onClickLogin,
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(start = 90.dp, end = 90.dp, bottom = 10.dp),
        enabled = isEnable
    ) {
        Text(text = stringResource(R.string.inicio_sesion_login), fontSize = 22.sp)
    }
}

/**
 * Botón para iniciar sesión con un usuario de Google
 */
@Composable
private fun BotonInicioGoogle(onCLickGoogle: () -> Unit) {
    OutlinedButton(
        onClick = onCLickGoogle,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(start = 90.dp, end = 90.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.google),
            contentDescription = stringResource(R.string.login_con_google),
            modifier = Modifier.height(50.dp)
        )
    }
}

/**
 * Logo de la aplicación
 */
@Composable
private fun LogoApp() {
    Image(
        painter = painterResource(id = R.drawable.guachat),
        contentDescription = "Google",
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
    )
}


