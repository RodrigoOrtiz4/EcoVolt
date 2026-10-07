package com.ecovolt.app.data.remote

import com.ecovolt.app.config.AppConfig
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

// No se indica el motor de red: Ktor usa el que encuentra en cada plataforma
// (OkHttp en Android, agregado en androidMain).
fun createHttpClient(): HttpClient = HttpClient {
    install(ContentNegotiation) {
        json(Json { ignoreUnknownKeys = true })
    }
    install(HttpTimeout) {
        requestTimeoutMillis = AppConfig.TIMEOUT_MS
    }
}
