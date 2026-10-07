package com.example.ecovolt_00.model


import kotlinx.serialization.Serializable

// Roles de EcoVolt. Cada rol trae el texto que se muestra en pantalla.
@Serializable
enum class Rol(val etiqueta: String, val descripcion: String) {
    DIRECTOR("Director", "Consulta el consumo de la escuela y el avance de los grupos."),
    DOCENTE("Docente", "Revisa las sesiones y el avance de tu grupo."),
    ALUMNO("Alumno", "Completa sesiones y retos para ganar puntos de impacto."),
    RESPONSABLE("Responsable de lecturas", "Captura la lectura del medidor el viernes y el lunes.")
}
