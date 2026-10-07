package com.ecovolt.app.model
enum class MomentoLectura { VIERNES_CIERRE, LUNES_INICIO }

data class Lectura(
    val id: Int,
    val fecha: String,            // ejemplo: "2026-09-25"
    val momento: MomentoLectura,
    val valorKwh: Double
)