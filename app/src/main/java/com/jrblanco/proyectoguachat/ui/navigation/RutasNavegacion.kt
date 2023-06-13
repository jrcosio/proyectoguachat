package com.jrblanco.proyectoguachat.modelo

import com.jrblanco.proyectoguachat.domain.model.Usuario

sealed class RutasNav(val route: String) {

    object Splash : RutasNav("splashView")
    object Login : RutasNav("loginView")
    object Registro : RutasNav("registroView")
    object Home : RutasNav("homeView")
    object Chat : RutasNav("chatView/{idGoogle}")
    fun chatRouteWithId(id: String): String {
        return "chatView/$id"
    }

}