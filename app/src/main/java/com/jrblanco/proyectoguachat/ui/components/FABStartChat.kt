package com.jrblanco.proyectoguachat.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jrblanco.proyectoguachat.R
import com.jrblanco.proyectoguachat.ui.theme.Green40


@Composable
fun FABStartChat(modifier: Modifier, onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier
            .size(70.dp)
            .border(2.dp, Color.LightGray, CircleShape),
        elevation = FloatingActionButtonDefaults.elevation(5.dp),
        containerColor = Green40,
        shape = CircleShape
    ) {
        Icon(
            imageVector = Icons.Filled.EditNote,
            contentDescription = stringResource(R.string.iniciar_chat_nuevo),
            modifier = modifier.size(45.dp)
        )
    }
}