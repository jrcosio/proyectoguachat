package com.jrblanco.proyectoguachat.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.jrblanco.proyectoguachat.R
import com.jrblanco.proyectoguachat.aplication.viewmodels.ChatViewModel
import com.jrblanco.proyectoguachat.domain.model.Usuario
import com.jrblanco.proyectoguachat.ui.theme.Azul30
import com.jrblanco.proyectoguachat.ui.theme.Azul40
import com.jrblanco.proyectoguachat.ui.theme.Green30

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatView(navControl: NavHostController, viewModel: ChatViewModel, idGoogle: String) {
    val user = Usuario(
        "jhghj",
        "Sandra",
        "s@s.com",
        "https://firebasestorage.googleapis.com/v0/b/proyecto-guachat-dam.appspot.com/o/avatar_usuarios%2FIiLF8xugh4annaifNN9Bk0kuzpV2.imagen?alt=media&token=470662fe-0136-40e8-827c-0b982b90c682"
    )

    Scaffold(
        topBar = { TopBarChat(user){
            navControl.popBackStack()
        } },
        bottomBar = {BottomBarChat()}

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Image(
                painter = painterResource(id = R.drawable.fondo_chat),
                modifier = Modifier.fillMaxSize(),
                contentDescription = "Logotipo de GuaChat",
                contentScale = ContentScale.Crop,
                alpha = 0.4f,
                colorFilter = ColorFilter.tint(Azul30)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBarChat() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(Azul40),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ){
            TextField(
                value = "",
                onValueChange = {  },
                modifier = Modifier.weight(1f).padding(start = 5.dp,end =5.dp),
                trailingIcon =  {
                    Icon(imageVector = Icons.Filled.AttachFile, contentDescription = "Adjuntar")
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Green30,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                shape = CircleShape,
                maxLines = 1,
                singleLine = true,
                placeholder = { Text(text = "Mensaje") }
            )
            Icon(
                imageVector = Icons.Filled.Send,
                contentDescription = "Volver atras",
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 5.dp)
                    .clickable { },
                tint = Azul30
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarChat(user: Usuario, onClickBack: () -> Unit) {

    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = user.nombre, fontSize = 24.sp ,fontWeight = FontWeight.Bold, color = Color.White)
                    Text(text = user.email, color = Azul30, fontSize = 14.sp)
                }
            }
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.Filled.ArrowBackIosNew,
                contentDescription = "Volver atras",
                modifier = Modifier
                    .size(40.dp)
                    .clickable { onClickBack() },
                tint = Azul30
            )
        },
        actions = {
            AsyncImage(
                model = user.avatar,
                contentDescription = "Imagen de usuario",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Black, CircleShape),
                contentScale = ContentScale.Crop
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Azul40)
    )
}

