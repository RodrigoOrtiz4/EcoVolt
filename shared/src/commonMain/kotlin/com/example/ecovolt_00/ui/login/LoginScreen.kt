package com.example.ecovolt_00.ui.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.ecovolt_00.model.Usuario

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginExitoso: (Usuario) -> Unit
) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = {
            val user = viewModel.login("admin", "123456")
            if (user != null) onLoginExitoso(user)
        }) {
            Text("Iniciar Sesión (Demo)")
        }
    }
}