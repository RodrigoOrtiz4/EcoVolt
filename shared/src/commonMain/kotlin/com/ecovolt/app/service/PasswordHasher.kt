package com.ecovolt.app.service

import kotlin.random.Random

// Las contraseñas nunca se guardan ni se comparan en texto plano.
// Se guarda un hash con sal (salt): SHA-256("salt:contraseña"). El hash no se puede revertir.
object PasswordHasher {

    // Sal aleatoria de 8 bytes (16 caracteres hex), distinta para cada usuario
    fun generarSalt(): String =
        Random.nextBytes(8).joinToString("") { (it.toInt() and 0xFF).toString(16).padStart(2, '0') }

    fun hash(contrasena: String, salt: String): String = sha256Hex("$salt:$contrasena")

    fun verificar(contrasena: String, salt: String, hashEsperado: String): Boolean =
        igualesEnTiempoConstante(hash(contrasena, salt), hashEsperado.lowercase())

    // Compara sin detenerse en la primera diferencia, para no filtrar información por tiempo
    private fun igualesEnTiempoConstante(a: String, b: String): Boolean {
        if (a.length != b.length) return false
        var diferencia = 0
        for (i in a.indices) {
            diferencia = diferencia or (a[i].code xor b[i].code)
        }
        return diferencia == 0
    }
}
