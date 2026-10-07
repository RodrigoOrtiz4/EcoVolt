package com.example.ecovolt_00.service

// Calcular SHA-256 depende de la plataforma, así que aquí solo se declara.
// La implementación para Android está en androidMain (Sha256.android.kt).
expect fun sha256Hex(texto: String): String