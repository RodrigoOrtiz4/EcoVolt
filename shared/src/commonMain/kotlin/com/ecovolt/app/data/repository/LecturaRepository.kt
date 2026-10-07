package com.ecovolt.app.data.repository

import com.example.myapplication.data.local.LecturaStorage
import com.example.myapplication.data.remote.EcoVoltApi
import com.example.myapplication.data.remote.toModel
import com.example.myapplication.model.Lectura
import com.example.myapplication.model.MomentoLectura
import com.example.myapplication.service.ConsumoService
import kotlin.coroutines.cancellation.CancellationException

// Repositorio: unifica la fuente remota (API) y la local (almacenamiento).
// La vista nunca habla directo con la API ni con el almacenamiento.
class LecturaRepository(
    private val api: EcoVoltApi,
    private val storage: LecturaStorage,
    private val consumoService: ConsumoService
) {

    // Lecturas guardadas en el dispositivo (funciona sin internet)
    fun lecturasGuardadas(): List<Lectura> = storage.obtener()

    // Descarga de la API, guarda en local y devuelve el resultado
    suspend fun sincronizar(): Result<List<Lectura>> =
        try {
            val lecturas = api.obtenerLecturas().map { it.toModel() }
            storage.guardar(lecturas)
            Result.success(lecturas)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }

    // Toma la lectura de lunes más reciente y la de viernes anterior a ella
    fun consumoFinDeSemana(lecturas: List<Lectura>): Double? {
        val lunes = lecturas
            .filter { it.momento == MomentoLectura.LUNES_INICIO }
            .maxByOrNull { it.fecha } ?: return null
        val viernes = lecturas
            .filter { it.momento == MomentoLectura.VIERNES_CIERRE && it.fecha < lunes.fecha }
            .maxByOrNull { it.fecha } ?: return null
        return runCatching { consumoService.consumoFinDeSemana(viernes, lunes) }.getOrNull()
    }
}