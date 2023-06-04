package com.jrblanco.proyectoguachat.ui.screen.principal.componenteshome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Chat
import androidx.compose.material.icons.rounded.Contacts
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jrblanco.proyectoguachat.ui.theme.Green40
import com.jrblanco.proyectoguachat.ui.theme.Green50
import com.jrblanco.proyectoguachat.ui.theme.Purple40
import com.jrblanco.proyectoguachat.ui.theme.Purple80
import com.jrblanco.proyectoguachat.ui.theme.Red60

@Composable
fun BottonBar(modifier: Modifier) {
    BottomAppBar(
        modifier = modifier
            .padding(10.dp)
            .clip(CircleShape),
        containerColor = Purple40,
        tonalElevation = 5.dp,
        contentPadding = PaddingValues(3.dp)
    ) {
        NavigationBarItem(
            selected = false,
            onClick = { /*TODO*/ },
            modifier = Modifier.background(Color.Transparent),
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Contacts,
                    contentDescription = "Contactos",
                )
            },
            label = { Text(text = "Contactos") },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Purple40
            )
        )
        NavigationBarItem(
            selected = false,
            onClick = { /*TODO*/ },
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Chat,
                    contentDescription = "Zona de Chats",
                )
            },
            label = { Text(text = "Chats") },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Purple40
            )
        )
        NavigationBarItem(
            selected = false,
            onClick = { /*TODO*/ },
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Settings,
                    contentDescription = "Configuración",
                )
            },
            label = { Text(text = "Configuración") },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Purple40
            )
        )
    }
}

