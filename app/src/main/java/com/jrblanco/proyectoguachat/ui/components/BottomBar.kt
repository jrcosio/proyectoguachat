package com.jrblanco.proyectoguachat.ui.screen.principal.componenteshome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Chat
import androidx.compose.material.icons.rounded.Contacts
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jrblanco.proyectoguachat.aplication.viewmodels.HomeViewModel
import com.jrblanco.proyectoguachat.ui.theme.Azul40

@Composable
fun BottonBar(modifier: Modifier, seccion: Int, viewModel: HomeViewModel) {
    BottomAppBar(
        modifier = modifier
            .clipToBounds(),
        containerColor = Azul40,
        tonalElevation = 5.dp,
        contentPadding = PaddingValues(3.dp),
    ) {
        NavigationBarItem(
            selected = seccion == 0,
            onClick = {
                viewModel.resetAll()
                viewModel.onSeccionChange(0)
            },
            modifier = Modifier.background(Color.Transparent),
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Contacts,
                    contentDescription = "Contactos",
                )
            },
            label = { Text(text = "Contactos") },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Azul40
            )
        )
        NavigationBarItem(
            selected = seccion == 1,
            onClick = {
                viewModel.resetAll()
                viewModel.onSeccionChange(1)
            },
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Chat,
                    contentDescription = "Zona de Chats",
                )
            },
            label = { Text(text = "Chats") },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Azul40
            )
        )
        NavigationBarItem(
            selected = seccion == 2,
            onClick = { viewModel.onSeccionChange(2) },
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Settings,
                    contentDescription = "Configuración",
                )
            },
            label = { Text(text = "Configuración") },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Azul40
            )
        )
    }
}

