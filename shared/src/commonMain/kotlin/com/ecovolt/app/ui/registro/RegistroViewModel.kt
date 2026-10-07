package com.ecovolt.app.ui.registro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecovolt.app.data.repository.AuthRepository
import com.ecovolt.app.data.repository.ResultadoRegistro
import com.ecovolt.app.model.Usuario
import com.ecovolt.app.service.Validador
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Todo lo que la pantalla de registro necesita mostrar
data class RegistroUiState(
    val nombre: String = "",
    val usuario: String = "",
    val contrasena: String = "",
    val confirmacion: String = "",
    val mostrarContrasena: Boolean = false,
    val errorNombre: String? = null,
    val errorUsuario: String? = null,
    val errorContrasena: String? = null,
    val errorConfirmacion: String? = null,
    val errorGeneral: String? = null,
    val cargando: Boolean = false
)

class RegistroViewModel(private val auth: AuthRepository) : ViewModel() {

    private val _estado = MutableStateFlow(RegistroUiState())
    val estado: StateFlow<RegistroUiState> = _estado.asStateFlow()

    fun alCambiarNombre(valor: String) {
        if (valor.length > 40) return
        _estado.update { it.copy(nombre = valor, errorNombre = null, errorGeneral = null) }
    }

    fun alCambiarUsuario(valor: String) {
        if (valor.length > 20) return
        _estado.update { it.copy(usuario = valor, errorUsuario = null, errorGeneral = null) }
    }

    fun alCambiarContrasena(valor: String) {
        if (valor.length > 32) return
        _estado.update { it.copy(contrasena = valor, errorContrasena = null, errorConfirmacion = null, errorGeneral = null) }
    }

    fun alCambiarConfirmacion(valor: String) {
        if (valor.length > 32) return
        _estado.update { it.copy(confirmacion = valor, errorConfirmacion = null, errorGeneral = null) }
    }

    fun alternarContrasena() {
        _estado.update { it.copy(mostrarContrasena = !it.mostrarContrasena) }
    }

    // Borra el formulario (por ejemplo al regresar al login)
    fun limpiar() {
        _estado.value = RegistroUiState()
    }

    fun registrar(alExito: (Usuario) -> Unit) {
        val actual = _estado.value
        if (actual.cargando) return

        // 1) Validar los campos antes de guardar nada
        val errorNombre = Validador.validarNombre(actual.nombre)
        val errorUsuario = Validador.validarUsuario(actual.usuario)
        val errorContrasena = Validador.validarContrasena(actual.contrasena)
        val errorConfirmacion = Validador.validarConfirmacion(actual.contrasena, actual.confirmacion)
        if (listOf(errorNombre, errorUsuario, errorContrasena, errorConfirmacion).any { it != null }) {
            _estado.update {
                it.copy(
                    errorNombre = errorNombre,
                    errorUsuario = errorUsuario,
                    errorContrasena = errorContrasena,
                    errorConfirmacion = errorConfirmacion
                )
            }
            return
        }

        // 2) Crear la cuenta
        viewModelScope.launch {
            _estado.update { it.copy(cargando = true, errorGeneral = null) }
            when (val resultado = auth.registrar(actual.nombre, actual.usuario, actual.contrasena)) {
                is ResultadoRegistro.Exito -> {
                    _estado.value = RegistroUiState() // limpia los datos de la memoria
                    alExito(resultado.usuario)
                }
                ResultadoRegistro.UsuarioExistente -> _estado.update {
                    it.copy(cargando = false, errorUsuario = "Ese usuario ya está registrado")
                }
                is ResultadoRegistro.Error -> _estado.update {
                    it.copy(cargando = false, errorGeneral = resultado.mensaje)
                }
            }
        }
    }
}
