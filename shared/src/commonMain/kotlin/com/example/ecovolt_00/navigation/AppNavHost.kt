package com.example.ecovolt_00.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ecovolt_00.data.repository.AuthRepository
import com.example.ecovolt_00.data.repository.LecturaRepository
import com.example.ecovolt_00.ui.inicio.InicioScreen
import com.example.ecovolt_00.ui.lecturas.LecturasScreen
import com.example.ecovolt_00.ui.lecturas.LecturasViewModel
import com.example.ecovolt_00.ui.login.LoginScreen
import com.example.ecovolt_00.ui.login.LoginViewModel

// Navegación compartida: si ya hay una sesión guardada entra directo, si no pide login.
@Composable
fun AppNavHost(lecturaRepository: LecturaRepository, authRepository: AuthRepository) {
    var usuario by remember { mutableStateOf(authRepository.sesionActual()) }
    var pantalla by remember {
        mutableStateOf<Screen>(if (usuario == null) Screen.Login else Screen.Inicio)
    }

    when (pantalla) {
        Screen.Login -> {
            val viewModel = viewModel<LoginViewModel> { LoginViewModel(authRepository) }
            LoginScreen(viewModel) { autenticado ->
                usuario = autenticado
                pantalla = Screen.Inicio
            }
        }
        Screen.Inicio -> {
            val actual = usuario
            if (actual != null) {
                InicioScreen(
                    usuario = actual,
                    alVerLecturas = { pantalla = Screen.Lecturas },
                    alCerrarSesion = {
                        authRepository.cerrarSesion()
                        usuario = null
                        pantalla = Screen.Login
                    }
                )
            }
        }
        Screen.Lecturas -> {
            val viewModel = viewModel<LecturasViewModel> { LecturasViewModel(lecturaRepository) }
            Box(Modifier.fillMaxSize()) {
                LecturasScreen(viewModel)
                TextButton(
                    onClick = { pantalla = Screen.Inicio },
                    modifier = Modifier.align(Alignment.TopEnd).safeContentPadding()
                ) { Text("Volver") }
            }
        }
    }
}