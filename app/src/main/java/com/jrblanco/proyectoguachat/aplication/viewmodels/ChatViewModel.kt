package com.jrblanco.proyectoguachat.aplication.viewmodels

import androidx.lifecycle.ViewModel
import com.jrblanco.proyectoguachat.aplication.usecase.LoadContactUseCase
import com.jrblanco.proyectoguachat.infraestructure.FirestoreDatabaseRepository

class ChatViewModel: ViewModel() {
    //--- Repositorios a la BD
    private val dataBaseRepository = FirestoreDatabaseRepository()
    //--- Casos de uso
    private val loadContactUseCase = LoadContactUseCase(dataBaseRepository)



}