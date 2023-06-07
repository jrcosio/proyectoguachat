package com.jrblanco.proyectoguachat.ui.screen.principal

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthCredential
import com.jrblanco.proyectoguachat.ui.principal.TopBar
import com.jrblanco.proyectoguachat.ui.screen.principal.componenteshome.BottonBar
import com.jrblanco.proyectoguachat.ui.theme.Green40
import com.jrblanco.proyectoguachat.ui.theme.Purple40


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun HomeView() {




    var isBuscar by remember { mutableStateOf(false) }
    var texto by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopBar { isBuscar = !isBuscar } },
        bottomBar = { BottonBar(Modifier) }

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Buscar(isBuscar, texto)

        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun Buscar(
    isBuscar: Boolean,
    texto: String
) {
    var texto1 = texto
    AnimatedVisibility(visible = isBuscar) {
        Box(modifier = Modifier.background(Purple40)) {
            TextField(
                value = texto1,
                onValueChange = { texto1 = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.TopStart)
                    .padding(15.dp),
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Search, contentDescription = "Buscar")
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Green40,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                shape = CircleShape,
                maxLines = 1,
                singleLine = true,
                placeholder = { Text(text = "¿Qué desea buscar?") }
            )
        }
    }
}