package com.jrblanco.proyectoguachat

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.jrblanco.proyectoguachat.ui.navigation.Navegacion
import com.jrblanco.proyectoguachat.ui.theme.ProyectoGuaChatTheme


class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
           ProyectoGuaChatTheme {
               Surface(
                   modifier = Modifier.fillMaxSize(),
                   color = MaterialTheme.colorScheme.background
               ) {
                   val navController= rememberNavController()
                   Navegacion(navController)
               }
           }
        }
    }
}


//@Preview(showBackground = true, showSystemUi = true)
