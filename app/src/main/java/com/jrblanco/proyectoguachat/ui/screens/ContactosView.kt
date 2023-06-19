package com.jrblanco.proyectoguachat.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jrblanco.proyectoguachat.R
import com.jrblanco.proyectoguachat.domain.model.Usuario
import com.jrblanco.proyectoguachat.ui.theme.Azul40
import com.jrblanco.proyectoguachat.ui.theme.PurpleGrey80

@Composable
fun ContactosView(listaContactos: List<Usuario>, onclick: (Usuario) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize()
    ) {
        items(listaContactos) {
            ItemCardContact(usuario = it){ onclick(it) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemCardContact(usuario: Usuario, onclick: () -> Unit) {
    val context = LocalContext.current
    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .padding(vertical = 20.dp, horizontal = 10.dp),
        colors = CardDefaults.cardColors(containerColor = PurpleGrey80),
        elevation = CardDefaults.cardElevation(5.dp),
        onClick = onclick,

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp, start = 20.dp, end = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = usuario.avatar,
                    contentDescription = "Imagen de usuario",
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(130.dp)
                        .border(2.dp, Azul40, CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                text = usuario.nombre.take(13),
                modifier = Modifier.padding(top = 10.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Azul40
            )
        }

    }
}
