package com.ecovolt.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.ecovolt.app.data.remote.EcoVoltApi
import com.ecovolt.app.data.remote.createHttpClient
import com.ecovolt.app.data.repository.AuthRepository
import com.ecovolt.app.navigation.AppNavHost
import com.ecovolt.app.ui.theme.EcoVoltTheme

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
