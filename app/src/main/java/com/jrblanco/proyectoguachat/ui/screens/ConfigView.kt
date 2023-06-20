package com.jrblanco.proyectoguachat.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Dataset
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jrblanco.proyectoguachat.aplication.viewmodels.HomeViewModel
import com.jrblanco.proyectoguachat.ui.theme.Azul30
import com.jrblanco.proyectoguachat.ui.theme.Azul40
import com.jrblanco.proyectoguachat.ui.theme.Red50

@Composable
fun ConfigView(viewModel: HomeViewModel, oClickCerrar: () -> Unit) {
    Card(
        shape = RoundedCornerShape(25.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        colors = CardDefaults.cardColors(containerColor = Azul30),
        elevation = CardDefaults.cardElevation(5.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp, start = 20.dp, end = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = viewModel.usuario.value?.avatar ?: "",
                    contentDescription = "Imagen de usuario",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .border(2.dp, Azul40, CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.size(10.dp))

            Text(
                text = viewModel.usuario.value?.nombre ?: "",
                color = Azul40,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = viewModel.usuario.value?.email ?: "",
                color = Color.DarkGray,
                fontSize = 18.sp
            )

            Card(
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp)
                    .weight(1f),
                colors = CardDefaults.cardColors(containerColor = Azul30),
                elevation = CardDefaults.cardElevation(5.dp)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    FilaConfig(
                        texto = "Notificaciones y sonidos",
                        modifier = Modifier.weight(1f),
                        icon = Icons.Filled.NotificationsActive,
                        color = Red50
                    ){}
                    FilaConfig(
                        texto = "Privacidad y seguridad",
                        modifier =Modifier.weight(1f),
                        icon =Icons.Filled.Lock,
                        color = Color.Gray
                    ){}
                    FilaConfig(
                        texto = "Datos y almacenamiento",
                        modifier = Modifier.weight(1f),
                        icon = Icons.Filled.Dataset,
                        color = Color.Green
                    ){}
                    FilaConfig(
                        texto = "Apariencia",
                        modifier = Modifier.weight(1f),
                        icon = Icons.Filled.PhoneAndroid,
                        color = Azul40
                    ){}
                }
            }
            Button(onClick = oClickCerrar) {
                Text(
                    text = "Cerrar sesión",
                    modifier = Modifier.padding(5.dp),
                    fontSize = 24.sp,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.size(25.dp))
        }

    }
}

@Composable
private fun FilaConfig(
    texto: String,
    modifier: Modifier = Modifier,
    icon: ImageVector,
    color: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clickable { onClick }
            .padding(start = 10.dp, end = 10.dp, top = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier.size(40.dp),
            colors = CardDefaults.cardColors(containerColor = color),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Añadir nuevo contacto",
                modifier = Modifier
                    .size(40.dp)
                    .padding(2.dp),
                tint = Color.White
            )
        }
        Text(
            text = texto,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
            fontSize = 14.sp
        )
        Icon(
            imageVector = Icons.Filled.ChevronRight,
            contentDescription = "Icono flecha",
            modifier = Modifier.size(24.dp),
            tint = Color.Gray
        )
    }
}

