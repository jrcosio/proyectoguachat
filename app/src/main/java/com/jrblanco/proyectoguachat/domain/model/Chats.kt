package com.jrblanco.proyectoguachat.domain.model

import com.google.firebase.Timestamp

data class Chats(
    var idChat: String = "",
    var date: Timestamp? = null,
    var lastMessage: String = "",
    var isGrupo: Boolean = false,
    var title: String = "",
    var subTitle: String = "",
    var icon: String = "",
    var idGoogle:String = ""
  //  var countNewMessage: Int = -1,
)
