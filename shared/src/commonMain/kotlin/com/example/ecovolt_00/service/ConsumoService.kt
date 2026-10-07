package com.example.ecovolt_00.service

import com.example.ecovolt_00.model.Lectura

class ConsumoService {

    // Consumo del fin de semana = lectura del lunes - lectura del viernes
    fun consumoFinDeSemana(viernes: Lectura, lunes: Lectura): Double {
        require(lunes.valorKwh >= viernes.valorKwh) {
            "La lectura del lunes no puede ser menor que la del viernes"
        }
        return lunes.valorKwh - viernes.valorKwh
    }

    // El umbral de 5.0 kWh es provisional; luego lo ajustamos con datos reales de la escuela
    fun hayConsumoFantasma(consumoKwh: Double, umbralKwh: Double = 5.0): Boolean =
        consumoKwh > umbralKwh
}
