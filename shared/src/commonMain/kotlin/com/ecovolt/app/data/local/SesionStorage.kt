package com.ecovolt.app.data.local

import com.ecovolt.app.model.Usuario

// Contrato para recordar la sesión en el dispositivo. La implementación depende de la plataforma.
interface SesionStorage {
    fun guardar(usuario: Usuario)
    fun obtener(): Usuario?
    fun cerrar()
}
