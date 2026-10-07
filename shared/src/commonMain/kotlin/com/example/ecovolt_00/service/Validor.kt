package com.example.ecovolt_00.service


// Validaciones de los campos del login. Devuelven el mensaje de error, o null si todo está bien.
object Validador {

    fun validarUsuario(valor: String): String? {
        val texto = valor.trim()
        return when {
            texto.isEmpty() -> "Escribe tu usuario"
            texto.length < 4 -> "Debe tener al menos 4 caracteres"
            texto.length > 20 -> "Debe tener máximo 20 caracteres"
            !texto.all { it.isLetterOrDigit() || it == '.' || it == '_' } ->
                "Solo letras, números, punto y guion bajo"
            else -> null
        }
    }

    fun validarContrasena(valor: String): String? = when {
        valor.isEmpty() -> "Escribe tu contraseña"
        valor.length < 6 -> "Debe tener al menos 6 caracteres"
        valor.length > 32 -> "Debe tener máximo 32 caracteres"
        valor.any { it.isWhitespace() } -> "No puede llevar espacios"
        else -> null
    }
}

