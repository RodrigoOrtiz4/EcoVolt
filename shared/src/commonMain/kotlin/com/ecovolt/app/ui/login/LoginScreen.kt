package com.ecovolt.app.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ecovolt.app.model.Usuario

// Pantalla de inicio de sesión (frontend). Solo dibuja el estado y avisa lo que hace el usuario.
@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    alIrARegistro: () -> Unit,
    alIniciarSesion: (Usuario) -> Unit
) {
    val estado by viewModel.estado.collectAsState()
    val colores = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(0f to colores.primary, 0.42f to colores.primary, 1f to colores.background))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeContentPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logotipo
            Box(
                modifier = Modifier.size(84.dp).clip(CircleShape).background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Text("⚡", fontSize = 42.sp)
            }
            Spacer(Modifier.height(12.dp))
            Text(
                text = "EcoVolt",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "Gestión y ahorro de energía",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.9f)
            )
            Spacer(Modifier.height(28.dp))

            // Tarjeta con el formulario
            Card(
                modifier = Modifier.fillMaxWidth().widthIn(max = 420.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(Modifier.padding(24.dp)) {
                    Text(
                        text = "Iniciar sesión",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Entra con tu usuario y contraseña",
                        style = MaterialTheme.typography.bodyMedium,
                        color = colores.onSurfaceVariant
                    )
                    Spacer(Modifier.height(20.dp))

                    OutlinedTextField(
                        value = estado.usuario,
                        onValueChange = viewModel::alCambiarUsuario,
                        label = { Text("Usuario") },
                        singleLine = true,
                        enabled = !estado.cargando,
                        isError = estado.errorUsuario != null,
                        supportingText = { estado.errorUsuario?.let { Text(it) } },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(4.dp))

                    OutlinedTextField(
                        value = estado.contrasena,
                        onValueChange = viewModel::alCambiarContrasena,
                        label = { Text("Contraseña") },
                        singleLine = true,
                        enabled = !estado.cargando,
                        isError = estado.errorContrasena != null,
                        supportingText = { estado.errorContrasena?.let { Text(it) } },
                        visualTransformation = if (estado.mostrarContrasena) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = { viewModel.iniciarSesion(alIniciarSesion) }),
                        trailingIcon = {
                            Text(
                                text = if (estado.mostrarContrasena) "Ocultar" else "Mostrar",
                                style = MaterialTheme.typography.labelLarge,
                                color = colores.primary,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { viewModel.alternarContrasena() }
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                            )
                        },
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    estado.errorGeneral?.let { mensaje ->
                        Spacer(Modifier.height(8.dp))
                        Surface(
                            color = colores.errorContainer,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = mensaje,
                                style = MaterialTheme.typography.bodyMedium,
                                color = colores.onErrorContainer,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }

                    Spacer(Modifier.height(20.dp))
                    Button(
                        onClick = { viewModel.iniciarSesion(alIniciarSesion) },
                        enabled = !estado.cargando,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth().height(52.dp)
                    ) {
                        if (estado.cargando) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(22.dp),
                                color = colores.onPrimary,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text("Iniciar sesión", fontWeight = FontWeight.SemiBold)
                        }
                    }

                    Spacer(Modifier.height(8.dp))
                    TextButton(
                        onClick = alIrARegistro,
                        enabled = !estado.cargando,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("¿No tienes cuenta? Regístrate")
                    }

                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "¿Problemas para entrar? Pide ayuda a tu docente o al director.",
                        style = MaterialTheme.typography.bodySmall,
                        color = colores.onSurfaceVariant
                    )
                }
            }
        }
    }
}
