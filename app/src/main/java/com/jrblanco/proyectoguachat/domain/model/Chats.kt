package com.jrblanco.proyectoguachat.domain.model

data class Chats(
    var idChat: String,
    var title: String,
    var subTitle: String,
    var date: String,
    var lastMessage: String,
    var tipo: Boolean,
    var icon: String,
    var countNewMessage: Int
)
