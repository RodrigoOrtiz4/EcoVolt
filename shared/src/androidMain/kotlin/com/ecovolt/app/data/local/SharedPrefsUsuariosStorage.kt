package com.ecovolt.app.data.local

import android.content.Context
import kotlinx.serialization.json.Json

// Código específico de Android: guarda los usuarios registrados con SharedPreferences (privado de la app).
// Se guarda salt y hash, nunca la contraseña en texto plano.
class SharedPrefsUsuariosStorage(context: Context) : UsuariosRegistradosStorage {

    private val prefs = context.getSharedPreferences("ecovolt_usuarios", Context.MODE_PRIVATE)
    private val json = Json { ignoreUnknownKeys = true }

    override fun obtener(): List<UsuarioRegistro> {
        val texto = prefs.getString(KEY, null) ?: return emptyList()
        return try {
            json.decodeFromString<List<UsuarioRegistro>>(texto)
        } catch (e: Exception) {
            emptyList()
        }
    }

    override fun guardar(usuarios: List<UsuarioRegistro>) {
        // commit() (síncrono) para confirmar que el registro quedó guardado antes de continuar
        val ok = prefs.edit().putString(KEY, json.encodeToString(usuarios)).commit()
        if (!ok) throw IllegalStateException("No se pudo guardar el usuario")
    }

    private companion object {
        const val KEY = "registrados"
    }
}
