package com.ecovolt.app.data.repository

import com.ecovolt.app.data.local.SesionStorage
import com.ecovolt.app.data.local.UsuarioRegistro
import com.ecovolt.app.data.local.UsuariosLocalDataSource
import com.ecovolt.app.data.local.UsuariosRegistradosStorage
import com.ecovolt.app.model.Rol
import com.ecovolt.app.model.Usuario
import com.ecovolt.app.service.PasswordHasher
import kotlin.coroutines.cancellation.CancellationException

sealed interface ResultadoLogin {
    data class Exito(val usuario: Usuario) : ResultadoLogin
    data object CredencialesInvalidas : ResultadoLogin
    data class Error(val mensaje: String) : ResultadoLogin
}

sealed interface ResultadoRegistro {
    data class Exito(val usuario: Usuario) : ResultadoRegistro
    data object UsuarioExistente : ResultadoRegistro
    data class Error(val mensaje: String) : ResultadoRegistro
}

// Lógica del login y del registro. Los usuarios predefinidos salen de un JSON local y los
// que se registran en la app se guardan en el dispositivo. Cuando exista la API solo se
// cambiarían las fuentes de datos, sin tocar las pantallas.
class AuthRepository(
    private val usuarios: UsuariosLocalDataSource,
    private val registrados: UsuariosRegistradosStorage,
    private val sesion: SesionStorage
) {

    // Predefinidos + registrados en la app
    private suspend fun todosLosUsuarios(): List<UsuarioRegistro> =
        usuarios.obtenerUsuarios() + registrados.obtener()

    suspend fun iniciarSesion(usuario: String, contrasena: String): ResultadoLogin {
        val registros = try {
            todosLosUsuarios()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            return ResultadoLogin.Error("No se pudo leer la lista de usuarios.")
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

    // Crea una cuenta nueva. Todo registro desde la app entra como ALUMNO: los demás roles
    // (director, docente, responsable) se asignan aparte, no se pueden elegir al registrarse.
    suspend fun registrar(nombre: String, usuario: String, contrasena: String): ResultadoRegistro {
        val existentes = try {
            todosLosUsuarios()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            return ResultadoRegistro.Error("No se pudo leer la lista de usuarios.")
        }

        val nombreUsuario = usuario.trim()
        if (existentes.any { it.usuario.equals(nombreUsuario, ignoreCase = true) }) {
            return ResultadoRegistro.UsuarioExistente
        }

        val salt = PasswordHasher.generarSalt()
        val nuevo = UsuarioRegistro(
            id = (existentes.maxOfOrNull { it.id } ?: 0) + 1,
            usuario = nombreUsuario,
            nombre = nombre.trim(),
            rol = Rol.ALUMNO,
            salt = salt,
            passwordHash = PasswordHasher.hash(contrasena, salt)
        )

        try {
            registrados.guardar(registrados.obtener() + nuevo)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            return ResultadoRegistro.Error("No se pudo guardar tu cuenta. Intenta de nuevo.")
        }

        // Al terminar el registro la persona entra directo, sin volver a escribir sus datos
        val autenticado = Usuario(nuevo.id, nuevo.usuario, nuevo.nombre, nuevo.rol)
        sesion.guardar(autenticado)
        return ResultadoRegistro.Exito(autenticado)
    }

    fun sesionActual(): Usuario? = sesion.obtener()

    fun cerrarSesion() = sesion.cerrar()
}
