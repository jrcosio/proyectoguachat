package com.jrblanco.proyectoguachat.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import com.jrblanco.proyectoguachat.domain.model.Usuario
import com.jrblanco.proyectoguachat.ui.theme.Azul40
import com.jrblanco.proyectoguachat.ui.theme.Green30
import com.jrblanco.proyectoguachat.ui.theme.PurpleGrey80
import com.jrblanco.proyectoguachat.ui.theme.Yellow30

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContacto(
    isVisible: Boolean,
    modifier: Modifier = Modifier,
    texto: String,
    viewModel: HomeViewModel,
    onClick: (Usuario) -> Unit
) {
    viewModel.getAllUsers()

    val listadoUsuario = if (texto.isNullOrEmpty()) {
        listOf<Usuario>() //Vacio para que no me muestre nada
    } else {
        viewModel.allUsuario.value?.filter { it.email.contains(texto, ignoreCase = true) }
            .orEmpty()
    }

    AnimatedVisibility(
        visible = isVisible,
        modifier = modifier,
        enter = expandVertically(),
        exit = shrinkVertically()
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp))
                .background(Azul40)
        ) {
            Column() {
                TextField(
                    value = texto,
                    onValueChange = { viewModel.onTextAddContact(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp, end = 15.dp, start = 15.dp),
                    leadingIcon = {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = "Buscar")
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
                    placeholder = { Text(text = "Escribe el email que desea añadir...") }
                )
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 20.dp, start = 25.dp, end = 25.dp)
                        //.height(200.dp)
                        .requiredSizeIn(maxHeight = 200.dp),
                    colors = CardDefaults.cardColors(containerColor = PurpleGrey80)
                ) {
                    LazyColumn(
                    ) {
                        items(listadoUsuario) { user ->
                            ItemSearchContact(item = user) { onClick(user) }
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun ItemSearchContact(item: Usuario, onClick: (Usuario) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Yellow30)
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
            Text(text = item.email, modifier = Modifier.padding(start = 10.dp), fontSize = 12.sp)
        }
    }
}