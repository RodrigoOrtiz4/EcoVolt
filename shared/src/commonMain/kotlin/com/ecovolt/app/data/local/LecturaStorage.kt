package com.ecovolt.app.data.local

import com.example.myapplication.model.Lectura

// Contrato compartido: la app solo conoce esta interfaz.
// La implementación real depende de cada plataforma.
interface LecturaStorage {
    fun guardar(lecturas: List<Lectura>)
    fun obtener(): List<Lectura>
}