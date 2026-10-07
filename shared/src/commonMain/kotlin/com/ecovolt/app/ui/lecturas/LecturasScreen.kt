package com.ecovolt.app.ui.lecturas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ecovolt.app.model.Lectura
import com.ecovolt.app.model.MomentoLectura

// Vista compartida (Compose Multiplatform): se escribe una vez y corre en Android
@Composable
fun LecturasScreen(viewModel: LecturasViewModel) {
    val estado by viewModel.estado.collectAsState()

    Column(Modifier.fillMaxSize().safeContentPadding().padding(16.dp)) {
        Text("EcoVolt", style = MaterialTheme.typography.headlineMedium)
        Text("Lecturas del medidor", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(12.dp))

        Text(
            text = estado.consumoFinDeSemana
                ?.let { "Consumo del último fin de semana: $it kWh" }
                ?: "Consumo del fin de semana: sin datos suficientes"
        )
        Spacer(Modifier.height(12.dp))

        Button(onClick = viewModel::sincronizar, enabled = !estado.cargando) {
            Text(if (estado.cargando) "Sincronizando..." else "Sincronizar")
        }
        estado.mensaje?.let { Text(it, style = MaterialTheme.typography.bodySmall) }
        Spacer(Modifier.height(12.dp))

        LazyColumn {
            items(estado.lecturas, key = { it.id }) { lectura ->
                LecturaItem(lectura)
            }
        }
    }
}
@Composable
private fun LecturaItem(lectura: Lectura) {
    Card(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Column(Modifier.padding(12.dp)) {
            Text(lectura.fecha, style = MaterialTheme.typography.titleSmall)
            Text(
                if (lectura.momento == MomentoLectura.LUNES_INICIO) "Lunes (inicio)" else "Viernes (cierre)"
            )
            Text("${lectura.valorKwh} kWh")
        }
    }
}