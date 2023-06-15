package com.jrblanco.proyectoguachat.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.jrblanco.proyectoguachat.R
import com.jrblanco.proyectoguachat.aplication.viewmodels.ChatViewModel
import com.jrblanco.proyectoguachat.domain.model.Usuario
import com.jrblanco.proyectoguachat.ui.theme.Azul30
import com.jrblanco.proyectoguachat.ui.theme.Azul40
import com.jrblanco.proyectoguachat.ui.theme.Green30
import com.jrblanco.proyectoguachat.ui.theme.PurpleGrey80
import com.jrblanco.proyectoguachat.ui.theme.Red50

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatView(navControl: NavHostController, viewModel: ChatViewModel, idGoogle: String) {

    val contact by viewModel.contact.observeAsState(initial = Usuario("", "", "", ""))
    val noHasMessage by viewModel.noHasMessages.observeAsState(initial = false)
    val messageText by viewModel.messageText.observeAsState(initial = "")

    viewModel.setContact(idGoogle) //Obtiene info del contacto

    Scaffold(
        topBar = {
            TopBarChat(contact) {
                navControl.popBackStack()
            }
        },
        bottomBar = { BottomBarChat(messageText, viewModel) }

    ) {
        Image(
            painter = painterResource(id = R.drawable.fondo_chat),
            modifier = Modifier.fillMaxSize(),
            contentDescription = "Logotipo de GuaChat",
            contentScale = ContentScale.Crop,
            alpha = 0.4f,
            colorFilter = ColorFilter.tint(Azul30)
        )
        showNoMensajes(noHasMessage, contact)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

        }
    }
}

@Composable
private fun showNoMensajes(isVisible: Boolean, contact: Usuario) {
    if (isVisible) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Card(
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .width(290.dp)
                    .height(160.dp),
                colors = CardDefaults.cardColors(containerColor = PurpleGrey80),
                elevation = CardDefaults.cardElevation(5.dp)

            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Filled.Forum,
                        contentDescription = "Icono mensaje",
                        modifier = Modifier
                            .size(50.dp)
                            .padding(vertical = 0.dp),
                        tint = Red50
                    )
                    Text(
                        text = "Aún no hay mensajes...",
                        modifier = Modifier.padding(horizontal = 10.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Azul40

                    )
                    Text(
                        text = "Envía un mensaje para iniciar una conversación con ${contact.nombre}",
                        modifier = Modifier.padding(5.dp),
                        textAlign = TextAlign.Center,
                        color = Color.DarkGray
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBarChat(messageText: String, viewModel: ChatViewModel) {
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
        ) {
            TextField(
                value = messageText,
                onValueChange = { viewModel.onChangeMessageText(it) },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 5.dp, end = 5.dp),
                trailingIcon = {
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
            IconButton(
                onClick = { viewModel.sendMessage() },
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 5.dp),
            ) {
                Icon(
                    imageVector = Icons.Filled.Send,
                    contentDescription = "Enviar Mensaje",

                    tint = Azul30
                )
            }
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
                    Text(
                        text = user.nombre,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
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

