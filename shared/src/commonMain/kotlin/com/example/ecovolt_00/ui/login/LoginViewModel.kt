package com.example.ecovolt_00.ui.login

import androidx.lifecycle.ViewModel
import com.example.ecovolt_00.data.repository.AuthRepository
import com.example.ecovolt_00.model.Usuario

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {
    fun login(usuario: String, clave: String): Usuario? {
        return authRepository.iniciarSesion(usuario, clave)
    }
}