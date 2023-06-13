package com.jrblanco.proyectoguachat.ui.screen.principal

import android.widget.Toast
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
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jrblanco.proyectoguachat.aplication.viewmodels.HomeViewModel
import com.jrblanco.proyectoguachat.domain.model.Chats
import com.jrblanco.proyectoguachat.modelo.RutasNav
import com.jrblanco.proyectoguachat.ui.components.AddContacto
import com.jrblanco.proyectoguachat.ui.components.FABStartChat
import com.jrblanco.proyectoguachat.ui.principal.TopBarChats
import com.jrblanco.proyectoguachat.ui.principal.TopBarContact
import com.jrblanco.proyectoguachat.ui.screen.principal.componenteshome.BottonBar
import com.jrblanco.proyectoguachat.ui.screens.ListaChatsView
import com.jrblanco.proyectoguachat.ui.screens.ConfigView
import com.jrblanco.proyectoguachat.ui.screens.ContactosView
import com.jrblanco.proyectoguachat.ui.theme.Azul40
import com.jrblanco.proyectoguachat.ui.theme.Green30


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(navControl: NavHostController, viewModel: HomeViewModel) {
    //--- Observadores del ViewModel
    val seccion by viewModel.seccion.observeAsState(initial = 1)
    val textSearch by viewModel.textoSearch.observeAsState("")
    val isBuscar by viewModel.isSearch.observeAsState(initial = false)
    val textAddContact by viewModel.textAddContact.observeAsState("")
    val isAddContact by viewModel.isAddContact.observeAsState(initial = false)
    val listaContactos by viewModel.allContacts.observeAsState(initial = null)

    val context = LocalContext.current

    val listaChats = listOf<Chats>(
        Chats("ZZZZ", "Steve Jobs", "Hola mundo", "Jue", "Hola mundo", false, "", 0),
        Chats("ZZZZ1", "Pedro", "Hola mundo", "Jue", "Hola mundo", false, "", 21),
        Chats(
            "ZZZZ",
            "JoseR",
            "Hola mundo",
            "Jue",
            "1234567890123456789012345678901234567890",
            false,
            "",
            1
        ),
        Chats("ZZZZ", "Grupo", "Sandra", "Jue", "bla bla bla", true, "", 33),
        Chats("ZZZZ", "Sandra", "Hola mundo", "Jue", "Hola mundo", false, "", 33),
        Chats("ZZZZ", "Piti", "Hola mundo", "Jue", "Pablo eres un pringao", false, "", 33),
        Chats("ZZZZ", "Pablo", "Hola mundo", "Jue", "Soy monger papa", false, "", 9),
        Chats("ZZZZ", "Emma", "Hola mundo", "Jue", "Prueba de textr", false, "", 9),
        Chats("ZZZZ", "Mama", "Hola mundo", "Jue", "12345678901234567890123456789", false, "", 9)
    )


    Scaffold(
        topBar = {
            when (seccion) {
                0 -> {
                    TopBarContact(
                        isBuscar = { viewModel.changeIsSearch(!isBuscar) },
                        isAdd = { viewModel.changeIsAddContact(!isAddContact) }
                    )
                }

                1 -> {
                    TopBarChats { viewModel.changeIsSearch(!isBuscar) }
                }
            }
        },
        bottomBar = { BottonBar(Modifier, seccion, viewModel) },
        floatingActionButton = { if (seccion == 1) FABStartChat(modifier = Modifier, {}) },
        floatingActionButtonPosition = FabPosition.End,
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Buscar(isBuscar, textSearch, viewModel)
            when (seccion) {
                0 -> {
                    viewModel.getAllContact()
                    val filtro = listaContactos?.filter { contact -> contact.nombre.contains(textSearch, ignoreCase = true)} //Filtra todos los contactos por nombre
                    if (filtro != null) {
                        ContactosView(listaContactos = filtro) { user ->
                            viewModel.onSeccionChange(3)
                            navControl.navigate(RutasNav.Chat.chatRouteWithId(user.idGoogle))
                        }
                    }
                }

                1 -> {
                    val filtro =
                        listaChats.filter { it.title.contains(textSearch, ignoreCase = true) }
                    ListaChatsView(listaChats = filtro)
                }

                2 -> {
                    viewModel.changeIsSearch(false)
                    viewModel.changeIsAddContact(false)
                    ConfigView(viewModel) {
                        viewModel.cerrarSesion(context)
                        navControl.navigate(RutasNav.Login.route, builder = {
                            popUpTo(RutasNav.Home.route) {
                                inclusive = true
                            }      //Limpia la pila de navegación
                        })
                    }
                }
            }
        }
        AddContacto(
            isVisible = isAddContact,
            modifier = Modifier.padding(it),
            texto = textAddContact,
            viewModel = viewModel
        ) { user ->
            viewModel.resetAll()
            viewModel.newContacto(user) {
                viewModel.getAllContact()
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun Buscar(isBuscar: Boolean, texto: String, viewModel: HomeViewModel) {
    AnimatedVisibility(visible = isBuscar) {
        Box(modifier = Modifier.background(Azul40)) {
            TextField(
                value = texto,
                onValueChange = { viewModel.onTextoSearch(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.TopStart)
                    .padding(15.dp),
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
                placeholder = { Text(text = "¿Qué desea buscar?") }
            )
        }
    }
}

