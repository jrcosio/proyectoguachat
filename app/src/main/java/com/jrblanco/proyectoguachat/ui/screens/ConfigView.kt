package com.jrblanco.proyectoguachat.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jrblanco.proyectoguachat.R
import com.jrblanco.proyectoguachat.aplication.viewmodels.HomeViewModel
import com.jrblanco.proyectoguachat.ui.theme.Azul30
import com.jrblanco.proyectoguachat.ui.theme.Azul40

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
                        .size(170.dp)
                        .clip(CircleShape)
                        .border(2.dp, Azul40, CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.size(20.dp))

            Text(
                text = viewModel.usuario.value?.nombre ?: "",
                color = Azul40,
                fontSize = 34.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = viewModel.usuario.value?.email ?: "",
                color = Color.DarkGray,
                fontSize = 18.sp
            )

            Spacer(
                modifier = Modifier
                    .size(25.dp)
                    .weight(1f)
            )
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

