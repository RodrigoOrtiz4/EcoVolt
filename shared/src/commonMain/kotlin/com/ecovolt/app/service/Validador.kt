package com.ecovolt.app.service

// Validaciones de los campos del login y del registro. Devuelven el mensaje de error, o null si todo está bien.
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

    fun validarNombre(valor: String): String? {
        val texto = valor.trim()
        return when {
            texto.isEmpty() -> "Escribe tu nombre"
            texto.length < 3 -> "Debe tener al menos 3 caracteres"
            texto.length > 40 -> "Debe tener máximo 40 caracteres"
            !texto.all { it.isLetter() || it == ' ' || it == '.' || it == '\'' || it == '-' } ->
                "Usa solo letras y espacios"
            else -> null
        }
    }

    fun validarConfirmacion(contrasena: String, confirmacion: String): String? = when {
        confirmacion.isEmpty() -> "Repite tu contraseña"
        contrasena != confirmacion -> "Las contraseñas no coinciden"
        else -> null
    }
}
