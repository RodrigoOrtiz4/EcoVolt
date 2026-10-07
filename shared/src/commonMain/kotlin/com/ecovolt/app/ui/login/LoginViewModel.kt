package com.ecovolt.app.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecovolt.app.data.repository.AuthRepository
import com.ecovolt.app.data.repository.ResultadoLogin
import com.ecovolt.app.model.Usuario
import com.ecovolt.app.service.Validador
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Todo lo que la pantalla de login necesita mostrar
data class LoginUiState(
    val usuario: String = "",
    val contrasena: String = "",
    val mostrarContrasena: Boolean = false,
    val errorUsuario: String? = null,
    val errorContrasena: String? = null,
    val errorGeneral: String? = null,
    val cargando: Boolean = false
)

class LoginViewModel(private val auth: AuthRepository) : ViewModel() {

    private val _estado = MutableStateFlow(LoginUiState())
    val estado: StateFlow<LoginUiState> = _estado.asStateFlow()

    fun alCambiarUsuario(valor: String) {
        if (valor.length > 20) return
        _estado.update { it.copy(usuario = valor, errorUsuario = null, errorGeneral = null) }
    }

    fun alCambiarContrasena(valor: String) {
        if (valor.length > 32) return
        _estado.update { it.copy(contrasena = valor, errorContrasena = null, errorGeneral = null) }
    }

    fun alternarContrasena() {
        _estado.update { it.copy(mostrarContrasena = !it.mostrarContrasena) }
    }

    fun iniciarSesion(alExito: (Usuario) -> Unit) {
        val actual = _estado.value
        if (actual.cargando) return

        // 1) Validar los campos antes de consultar nada
        val errorUsuario = Validador.validarUsuario(actual.usuario)
        val errorContrasena = Validador.validarContrasena(actual.contrasena)
        if (errorUsuario != null || errorContrasena != null) {
            _estado.update { it.copy(errorUsuario = errorUsuario, errorContrasena = errorContrasena) }
            return
        }

        // 2) Verificar las credenciales
        viewModelScope.launch {
            _estado.update { it.copy(cargando = true, errorGeneral = null) }
            when (val resultado = auth.iniciarSesion(actual.usuario, actual.contrasena)) {
                is ResultadoLogin.Exito -> {
                    _estado.value = LoginUiState() // limpia usuario y contraseña de la memoria
                    alExito(resultado.usuario)
                }
                ResultadoLogin.CredencialesInvalidas -> _estado.update {
                    it.copy(cargando = false, contrasena = "", errorGeneral = "Usuario o contraseña incorrectos")
                }
                is ResultadoLogin.Error -> _estado.update {
                    it.copy(cargando = false, errorGeneral = resultado.mensaje)
                }
            }
        }
    }
}
