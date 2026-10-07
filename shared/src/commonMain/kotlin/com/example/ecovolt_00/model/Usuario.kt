package com.example.ecovolt_00.model

import kotlinx.serialization.Serializable

// Usuario con sesión iniciada. No lleva contraseña ni hash.
@Serializable
data class Usuario(
    val id: Int,
    val usuario: String,
    val nombre: String,
    val rol: Rol
)
