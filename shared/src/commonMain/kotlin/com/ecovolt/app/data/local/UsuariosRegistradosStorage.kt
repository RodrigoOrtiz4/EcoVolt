package com.ecovolt.app.data.local

// Contrato para guardar en el dispositivo los usuarios que se registran desde la app.
// Los usuarios predefinidos siguen saliendo de usuarios.json; estos se suman a ellos.
interface UsuariosRegistradosStorage {
    fun obtener(): List<UsuarioRegistro>
    fun guardar(usuarios: List<UsuarioRegistro>)
}
