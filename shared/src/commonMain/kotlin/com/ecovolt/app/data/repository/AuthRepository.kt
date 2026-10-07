package com.ecovolt.app.data.repository

import com.example.myapplication.data.local.SesionStorage
import com.example.myapplication.data.local.UsuariosLocalDataSource
import com.example.myapplication.model.Usuario
import com.example.myapplication.service.PasswordHasher
import kotlin.coroutines.cancellation.CancellationException

sealed interface ResultadoLogin {
    data class Exito(val usuario: Usuario) : ResultadoLogin
    data object CredencialesInvalidas : ResultadoLogin
    data class Error(val mensaje: String) : ResultadoLogin
}

// Lógica del login. Hoy los usuarios salen de un JSON local; cuando exista la API
// solo se cambiaría la fuente de datos, sin tocar la pantalla.
class AuthRepository(
    private val usuarios: UsuariosLocalDataSource,
    private val sesion: SesionStorage
) {

    suspend fun iniciarSesion(usuario: String, contrasena: String): ResultadoLogin {
        val registros = try {
            usuarios.obtenerUsuarios()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            return ResultadoLogin.Error("No se pudo leer el archivo de usuarios.")
        }

        val registro = registros.firstOrNull { it.usuario.equals(usuario.trim(), ignoreCase = true) }
            ?: return ResultadoLogin.CredencialesInvalidas

        if (!PasswordHasher.verificar(contrasena, registro.salt, registro.passwordHash)) {
            return ResultadoLogin.CredencialesInvalidas
        }

        val autenticado = Usuario(registro.id, registro.usuario, registro.nombre, registro.rol)
        sesion.guardar(autenticado)
        return ResultadoLogin.Exito(autenticado)
    }

    fun sesionActual(): Usuario? = sesion.obtener()

    fun cerrarSesion() = sesion.cerrar()
}
