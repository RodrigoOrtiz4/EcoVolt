package com.example.ecovolt_00.navigation
// Pantallas de la app. Para agregar una nueva solo se suma un objeto aquí.
sealed interface Screen {
    data object Login : Screen
    data object Inicio : Screen
    data object Lecturas : Screen
}
