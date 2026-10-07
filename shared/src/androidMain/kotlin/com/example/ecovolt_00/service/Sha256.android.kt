package com.example.ecovolt_00.service

import java.security.MessageDigest

actual fun sha256Hex(texto: String): String {
    val bytes = MessageDigest.getInstance("SHA-256").digest(texto.toByteArray())
    return bytes.joinToString("") { "%02x".format(it) }
}