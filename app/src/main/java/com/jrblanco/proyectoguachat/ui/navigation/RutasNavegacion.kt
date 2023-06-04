package com.jrblanco.proyectoguachat.modelo

sealed class RutasNav(val route: String) {
    object Login : RutasNav("loginView")
    object Registro : RutasNav("registroView")
    object Home : RutasNav("homeView")
}