package com.example.ecovolt_00.ui.inicio

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.ecovolt_00.model.Usuario

@Composable
fun InicioScreen(
    usuario: Usuario,
    alVerLecturas: () -> Unit,
    alCerrarSesion: () -> Unit
) {
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Bienvenido, ${usuario.nombre}")
        Button(onClick = alVerLecturas) { Text("Ver Lecturas") }
        Button(onClick = alCerrarSesion) { Text("Cerrar Sesión") }
    }
}