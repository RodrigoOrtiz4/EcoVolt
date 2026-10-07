package com.example.ecovolt_00.data.repository

import com.example.ecovolt_00.model.Rol
import com.example.ecovolt_00.model.Usuario

class AuthRepository {
    private var usuarioActual: Usuario? = null

    fun sesionActual(): Usuario? = usuarioActual

    fun iniciarSesion(usuario: String, contrasena: String): Usuario? {
        // Usuario de prueba para inicializar
        usuarioActual = Usuario(1, usuario, "Usuario Demo", Rol.DIRECTOR)
        return usuarioActual
    }

    fun cerrarSesion() {
        usuarioActual = null
    }
}