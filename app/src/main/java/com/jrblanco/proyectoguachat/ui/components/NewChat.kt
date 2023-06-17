package com.jrblanco.proyectoguachat.ui.components

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
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jrblanco.proyectoguachat.aplication.viewmodels.HomeViewModel
import com.jrblanco.proyectoguachat.domain.model.Usuario
import com.jrblanco.proyectoguachat.ui.theme.Azul30
import com.jrblanco.proyectoguachat.ui.theme.Azul40
import com.jrblanco.proyectoguachat.ui.theme.Yellow30

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewChat(
    isVisible: Boolean,
    listaContact: List<Usuario>?,
    viewModel: HomeViewModel,
    onSelected: (Usuario) -> Unit
) {
    if (isVisible){
        AlertDialog(
            onDismissRequest = {viewModel.changeIsNewChat(false)},
            confirmButton = {},
            modifier = Modifier
                .width(330.dp)
                .requiredSizeIn(maxHeight = 400.dp),
            title = { Text(text = "Selecciona un contacto")},
            containerColor = Azul30,
            text = {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxSize(),
                    colors = CardDefaults.cardColors(containerColor = Yellow30),
                ) {
                    LazyColumn {
                        items(listaContact!!) { user ->
                            ItemNewChat(item = user) {
                                viewModel.changeIsNewChat(false)
                                onSelected(user)
                            }
                        }
                    }
                }
            }
        )
    }

   /* if (isVisible) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Card(
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .width(290.dp)
                    .height(330.dp),
                colors = CardDefaults.cardColors(containerColor = Azul30),
                elevation = CardDefaults.cardElevation(5.dp)

            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Selecciona un contacto",
                        modifier = Modifier.padding(top = 5.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray
                    )
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(15.dp),
                        colors = CardDefaults.cardColors(containerColor = Yellow30),
                    ) {
                        LazyColumn(
                        ) {
                            items(listaContact!!) {user ->
                                ItemNewChat(item = user) { onClick(user) }
                            }
                        }
                    }
                }
            }
        }
    }*/
}

@Composable
fun ItemNewChat(item: Usuario, onClick: (Usuario) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .clickable { onClick(item) }
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = item.avatar,
                contentDescription = "Imagen de usuario",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .border(1.dp, Azul40, CircleShape),
                contentScale = ContentScale.Crop
            )

            Text(
                text = item.nombre,
                modifier = Modifier.padding(start = 10.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
