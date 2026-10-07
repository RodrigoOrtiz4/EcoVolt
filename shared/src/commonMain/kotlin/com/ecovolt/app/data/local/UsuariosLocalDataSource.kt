package com.ecovolt.app.data.local

import kotlinx.serialization.json.Json
import myapplication.shared.generated.resources.Res

// Lee las credenciales predefinidas desde composeResources/files/usuarios.json
class UsuariosLocalDataSource {

    private val json = Json { ignoreUnknownKeys = true }
    private var cache: List<UsuarioRegistro>? = null

    suspend fun obtenerUsuarios(): List<UsuarioRegistro> {
        cache?.let { return it }
        val texto = Res.readBytes("files/usuarios.json").decodeToString()
        val lista = json.decodeFromString<UsuariosArchivo>(texto).usuarios
        cache = lista
        return lista
    }
}
