package com.ecovolt.app.data.local

import android.content.Context
import com.ecovolt.app.model.Usuario
import kotlinx.serialization.json.Json

// Código específico de Android: recuerda la sesión con SharedPreferences (privado de la app).
// Solo guarda id, usuario, nombre y rol. Nunca la contraseña.
class SharedPrefsSesionStorage(context: Context) : SesionStorage {

    private val prefs = context.getSharedPreferences("ecovolt_sesion", Context.MODE_PRIVATE)
    private val json = Json { ignoreUnknownKeys = true }

    override fun guardar(usuario: Usuario) {
        prefs.edit().putString(KEY, json.encodeToString(usuario)).apply()
    }

    override fun obtener(): Usuario? {
        val texto = prefs.getString(KEY, null) ?: return null
        return try {
            json.decodeFromString<Usuario>(texto)
        } catch (e: Exception) {
            null
        }
    }

    override fun cerrar() {
        prefs.edit().remove(KEY).apply()
    }

    private companion object {
        const val KEY = "usuario"
    }
}
