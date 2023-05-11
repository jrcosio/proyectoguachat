package com.jrblanco.proyectoguachat.ui.login

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.jrblanco.proyectoguachat.R
import com.jrblanco.proyectoguachat.modelo.RutasNav
import com.jrblanco.proyectoguachat.ui.componentes.TextFieldEmail
import com.jrblanco.proyectoguachat.ui.componentes.TextFieldPassword
import com.jrblanco.proyectoguachat.ui.theme.Green50

@Composable
fun LoginView(navControl: NavHostController, loginViewModel: LoginViewModel) {

    val user:String by loginViewModel.user.observeAsState(initial = "")
    val pass:String by loginViewModel.password.observeAsState(initial = "")
    val isLoginEnable by loginViewModel.isLoginEnable.observeAsState(initial = false)

    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            LogoApp()
            Spacer(modifier = Modifier.height(60.dp))

            TextFieldEmail(user, stringResource(R.string.usuario_login)) { loginViewModel.onLoginChange(user = it, pass = pass) }
            TextFieldPassword(pass, stringResource(R.string.password_login)) { loginViewModel.onLoginChange(user = user, pass = it) }

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
                loginViewModel.login() { }
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

            BotonInicioGoogle() { loginViewModel.loginConGoogle() }

        }
    }
}

/**
 * Botón para iniciar sesión con usuario y contraseña
 */
@Composable
private fun BotonInicioSesion(isEnable:Boolean, onClickLogin: () -> Unit) {
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
            .height(120.dp)
    )
}


