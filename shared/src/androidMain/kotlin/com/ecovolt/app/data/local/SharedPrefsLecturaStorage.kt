package com.ecovolt.app.data.local

import android.content.Context
import com.example.myapplication.config.AppConfig
import com.example.myapplication.data.remote.LecturaDto
import com.example.myapplication.data.remote.toDto
import com.example.myapplication.data.remote.toModel
import com.example.myapplication.model.Lectura
import kotlinx.serialization.json.Json

// Código específico de Android: usa SharedPreferences, que no existe en otras plataformas.
class SharedPrefsLecturaStorage(context: Context) : LecturaStorage {

    private val prefs = context.getSharedPreferences(AppConfig.STORAGE_NAME, Context.MODE_PRIVATE)
    private val json = Json { ignoreUnknownKeys = true }

    override fun guardar(lecturas: List<Lectura>) {
        val texto = json.encodeToString(lecturas.map { it.toDto() })
        prefs.edit().putString(KEY, texto).apply()
    }

    override fun obtener(): List<Lectura> {
        val texto = prefs.getString(KEY, null) ?: return emptyList()
        return json.decodeFromString<List<LecturaDto>>(texto).map { it.toModel() }
    }

    private companion object {
        const val KEY = "lecturas"
    }
}