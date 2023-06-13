package com.jrblanco.proyectoguachat.ui.principal

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jrblanco.proyectoguachat.R
import com.jrblanco.proyectoguachat.ui.theme.Azul30
import com.jrblanco.proyectoguachat.ui.theme.Azul40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarChats(isBuscar: () -> Unit) {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.guachat),
                    modifier = Modifier.height(50.dp),
                    contentDescription = "Logotipo de GuaChat"
                )
            }
        },
        actions = {
            IconButton(onClick = isBuscar) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Buscar",
                    modifier = Modifier.size(40.dp),
                    tint = Color.Black
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Azul40)
        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarContact(isBuscar: () -> Unit, isAdd: () -> Unit) {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Contactos", fontSize = 28.sp ,fontWeight = FontWeight.Bold, color = Color.White)
            }
        },
        navigationIcon = {
            IconButton(onClick = isAdd) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Añadir nuevo contacto",
                    modifier = Modifier.size(40.dp),
                    tint = Azul30
                )
            }
        },
        actions = {
            IconButton(onClick = isBuscar) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Buscar",
                    modifier = Modifier.size(40.dp),
                    tint = Azul30
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Azul40)
    )
}