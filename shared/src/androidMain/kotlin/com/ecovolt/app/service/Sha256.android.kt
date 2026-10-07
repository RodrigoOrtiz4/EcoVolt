package com.example.myapplication.service

import java.security.MessageDigest

// Código específico de Android: usa MessageDigest de Java para calcular el SHA-256
actual fun sha256Hex(texto: String): String {
    val bytes = MessageDigest.getInstance("SHA-256").digest(texto.encodeToByteArray())
    return bytes.joinToString("") { "%02x".format(it) }
}
