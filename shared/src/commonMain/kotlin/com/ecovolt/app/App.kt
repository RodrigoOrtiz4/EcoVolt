package com.example.myapplication

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.myapplication.data.remote.EcoVoltApi
import com.example.myapplication.data.remote.createHttpClient
import com.example.myapplication.data.repository.AuthRepository
import com.example.myapplication.navigation.AppNavHost
import com.example.myapplication.ui.theme.EcoVoltTheme

// Raíz de la UI compartida. Los almacenamientos llegan desde fuera porque
// su implementación es específica de cada plataforma.
@Composable
fun App(storage: LecturaStorage, sesionStorage: SesionStorage) {
    EcoVoltTheme {
        val lecturaRepository = remember {
            LecturaRepository(
                api = EcoVoltApi(createHttpClient()),
                storage = storage,
                consumoService = ConsumoService()
            )
        }
        val authRepository = remember {
            AuthRepository(UsuariosLocalDataSource(), sesionStorage)
        }
        AppNavHost(lecturaRepository, authRepository)
    }
}
