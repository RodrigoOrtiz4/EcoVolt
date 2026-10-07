package com.ecovolt.app.data.remote

import com.example.myapplication.config.AppConfig
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

class EcoVoltApi(private val client: HttpClient) {

    private val json = Json { ignoreUnknownKeys = true }

    suspend fun obtenerLecturas(): List<LecturaDto> {
        val texto = client.get("${AppConfig.BASE_URL}${AppConfig.LECTURAS_PATH}").bodyAsText()
        return json.decodeFromString(texto)
    }
}