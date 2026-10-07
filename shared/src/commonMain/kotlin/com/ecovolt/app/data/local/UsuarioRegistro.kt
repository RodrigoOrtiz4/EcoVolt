package com.ecovolt.app.data.local

import com.ecovolt.app.model.Rol
import kotlinx.serialization.Serializable

// Así viene cada usuario en usuarios.json. Se guarda el hash, nunca la contraseña.
@Serializable
data class UsuarioRegistro(
    val id: Int,
    val usuario: String,
    val nombre: String,
    val rol: Rol,
    val salt: String,
    val passwordHash: String
)

@Serializable
data class UsuariosArchivo(val usuarios: List<UsuarioRegistro>)
