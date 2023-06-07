package com.jrblanco.proyectoguachat.ui.screen.registro

import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.jrblanco.proyectoguachat.R
import com.jrblanco.proyectoguachat.modelo.RutasNav
import com.jrblanco.proyectoguachat.aplication.viewmodels.RegistroViewModel
import com.jrblanco.proyectoguachat.ui.screen.componentes.TextFieldEmail
import com.jrblanco.proyectoguachat.ui.screen.componentes.TextFieldPassword
import com.jrblanco.proyectoguachat.ui.screen.componentes.TextFieldSoloTexto
import com.jrblanco.proyectoguachat.ui.theme.Pink80
import com.jrblanco.proyectoguachat.ui.theme.Purple40
import com.jrblanco.proyectoguachat.ui.theme.Red60


@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun RegistroView(navControl: NavHostController, regViewModel: RegistroViewModel) {

    var errorRegistro by remember { mutableStateOf(0) }

    val imageUri by regViewModel.imageUri.observeAsState(initial = null)
    val nombre by regViewModel.nombre.observeAsState(initial = "")
    val email by regViewModel.email.observeAsState(initial = "")
    val password by regViewModel.pass.observeAsState(initial = "")
    val vpassword by regViewModel.vpass.observeAsState(initial = "")
    val isRegistroEnable by regViewModel.isResgistroEnable.observeAsState(initial = false)


    //Para lanzar el activity que carga la galería de imagenes del dispositivo
    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        if (it != null) {
            regViewModel.onChangeImageUri(it)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CabeceraRegistro()
            Spacer(modifier = Modifier.height(20.dp))

            AvatarUsuario(imageUri, pickMedia)
            Spacer(modifier = Modifier.height(20.dp))

            TextFieldSoloTexto(
                value = nombre,
                texto = "Nombre completo",
                onValueChange = { regViewModel.onChangeNombre(it) })
            //TextFieldSoloTexto(value = apodo, texto = "Apodo", onValueChange = {regViewModel.onChangeApado(it)})
            TextFieldEmail(
                value = email,
                texto = "Correo electrónico",
                onValueChange = { regViewModel.onChangeEmail(it) })
            TextFieldPassword(
                value = password,
                texto = "Contraseña",
                onValueChange = {regViewModel.onChangePass(it,vpassword) })
            TextFieldPassword(
                value = vpassword,
                texto = "Verifica Contraseña",
                onValueChange = { regViewModel.onChangePass(password,it) })

            Spacer(modifier = Modifier.height(30.dp))
            BotonRegistrar(isRegistroEnable) {
                regViewModel.crearUsuario {
                    when (it) {
                        0 -> {""
                            navControl.navigate(RutasNav.Home.route, builder = {
                                popUpTo(RutasNav.Registro.route) { inclusive = true }      //Limpia la pila de navegación
                            })
                        }
                        1,2,3 -> errorRegistro=it
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            RegistroError(errorRegistro)
        }
    }
}

@Composable
private fun CabeceraRegistro() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .clip(RoundedCornerShape(bottomStart = 120.dp))
            .background(Purple40)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.guachat),
                contentDescription = "Logo GuaChat",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .weight(1f)
            )
            Text(
                text = "Registro",
                modifier = Modifier
                    .padding(bottom = 10.dp, end = 10.dp)
                    .align(Alignment.End),
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
private fun AvatarUsuario(
    imageUri: Uri?,
    pickMedia: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>
) {
    val context = LocalContext.current
    Box() {
        val imagen = if (imageUri != null) {
            val source = ImageDecoder.createSource(context.contentResolver, imageUri!!)
            ImageDecoder.decodeBitmap(source).asImageBitmap()
        } else {
            ImageBitmap.imageResource(id = R.drawable.avatar)
        }

        Image(
            bitmap = imagen,
            contentDescription = "Imagen Usuario",
            modifier = Modifier
                .size(130.dp)
                .clip(CircleShape)
                .border(2.dp, Purple40, CircleShape),
            contentScale = ContentScale.Crop
        )

        Icon(
            imageVector = Icons.Default.AddAPhoto,
            contentDescription = stringResource(R.string.mostrar_password_login),
            Modifier
                .align(Alignment.BottomEnd)
                .size(48.dp)
                .clickable { pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
            tint = Red60
        )
    }
}


/**
 * Botón para registrar un usuario
 */
@Composable
private fun BotonRegistrar(isEnable: Boolean, onClickAction: () -> Unit) {

    Button(
        onClick = onClickAction,
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(start = 90.dp, end = 90.dp),
        enabled = isEnable
    ) {
        Text(text = "Registrarme", fontSize = 22.sp)
    }
}

@Composable
fun RegistroError(tipoError:Int = 0) {
    var textLineaCabezera = ""

    when (tipoError) {
        1 -> {textLineaCabezera = "Error el Usuario ya EXISTE"}
        2 -> {textLineaCabezera = "Error en usuario o contraseña"}
        3 -> {textLineaCabezera = "ERROR en el registro"}
    }

    if (tipoError != 0) {
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
                        text =  textLineaCabezera,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Red60
                    )
                }
                Text(
                    text = "Compruebe que el email es correcto",
                    modifier = Modifier.padding(horizontal = 24.dp),
                    color = Red60,
                    fontSize = 16.sp
                )
                Text(
                    text = "o la contraseña tiene 6 o mas caracteres",
                    modifier = Modifier.padding(horizontal = 24.dp),
                    color = Red60,
                    fontSize = 16.sp
                )
                //Text(text = "e intente de nuevo.", modifier = Modifier.padding(horizontal =24.dp), color = Red60, fontSize = 16.sp)
            }
        }
    }
}

