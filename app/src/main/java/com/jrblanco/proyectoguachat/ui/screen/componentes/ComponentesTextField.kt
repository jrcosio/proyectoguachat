package com.jrblanco.proyectoguachat.ui.screen.componentes

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.jrblanco.proyectoguachat.R

/**
 * Textfield para introducir la contraseña
 * @param pass Variable donde se almacena el texto que se introduce
 * @param onValueChange Fundión Lambda para el control de de textfield
 */
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TextFieldPassword(value: String, texto: String, onValueChange: (String) -> Unit) {
    var passVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .padding(start = 30.dp, end = 30.dp)
            .fillMaxWidth(),
        visualTransformation = if (passVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconToggleButton(
                checked = passVisible,
                onCheckedChange = { passVisible = it }) {
                val icono = if (passVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility
                Icon(
                    imageVector = icono,
                    contentDescription = stringResource(R.string.mostrar_password_login)
                )
            }
        },
        label = { Text(text = texto) },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        )
    )
}

/**
 * Textfield para introducir el texto
 * @param user Variable donde se almacena el texto que se introduce
 * @param onValueChange Fundión Lambda para el control de de textfield
 */
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TextFieldSoloTexto(value: String, texto: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .padding(start = 30.dp, end = 30.dp)
            .fillMaxWidth(),
        label = { Text(text = texto) },
        singleLine = true
    )
}


/**
 * Textfield para introducir el usuario
 * @param user Variable donde se almacena el texto que se introduce
 * @param onValueChange Fundión Lambda para el control de de textfield
 */
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TextFieldEmail(value: String, texto: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .padding(start = 30.dp, end = 30.dp)
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        label = { Text(text = texto) },
        placeholder = {Text(text = texto)},
        singleLine = true
    )
}