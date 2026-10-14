package com.example.blackjack

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Carta(
    val numero: String,
    val palo: String,
    val valor: Int
) {
    override fun toString() = "$numero$palo"
}

data class Resultado(
    val ganador: String,
    val perdedor: String,
    val fecha: String,
    val combinacion: String
)

object DatosJuego {

    private const val KEY_OBJETIVO = "objetivo"
    private const val KEY_TEMA = "tema"
    private const val KEY_RESULTADOS = "resultados"

    private lateinit var tinyDB: TinyDB

    var objetivo = 21
        private set

    var tema = 0
        private set

    val resultados = mutableStateListOf<Resultado>()

    // Inicializar TinyDB y recuperar la información guardada.
    fun inicializar(context: Context) {
        tinyDB = TinyDB(context.applicationContext)

        objetivo = tinyDB.getInt(KEY_OBJETIVO)
            .let { if (it == 0) 21 else it }

        tema = tinyDB.getInt(KEY_TEMA)

        cargarResultados()
    }

    // Guardar los cambios de configuración.
    fun guardarConfiguracion(
        nuevoObjetivo: Int,
        nuevoTema: Int
    ) {
        objetivo = nuevoObjetivo
        tema = nuevoTema

        tinyDB.putInt(KEY_OBJETIVO, objetivo)
        tinyDB.putInt(KEY_TEMA, tema)
    }

    // Recuperar el historial de partidas.
    private fun cargarResultados() {
        resultados.clear()

        val json = tinyDB.getString(KEY_RESULTADOS)

        if (json.isBlank()) return

        try {
            val array = JSONArray(json)

            for (i in 0 until array.length()) {
                val item = array.getJSONObject(i)

                resultados.add(
                    Resultado(
                        ganador = item.getString("ganador"),
                        perdedor = item.getString("perdedor"),
                        fecha = item.getString("fecha"),
                        combinacion = item.getString("combinacion")
                    )
                )
            }
        } catch (e: Exception) {
            resultados.clear()
        }
    }

    // Guardar todos los resultados mediante TinyDB.
    private fun persistirResultados() {
        val array = JSONArray()

        resultados.forEach { resultado ->
            array.put(
                JSONObject().apply {
                    put("ganador", resultado.ganador)
                    put("perdedor", resultado.perdedor)
                    put("fecha", resultado.fecha)
                    put("combinacion", resultado.combinacion)
                }
            )
        }

        tinyDB.putString(KEY_RESULTADOS, array.toString())
    }

    fun crearBaraja(): MutableList<Carta> {
        val palos = listOf("♥", "♦", "♣", "♠")
        val numeros = listOf(
            "A", "2", "3", "4", "5", "6", "7",
            "8", "9", "10", "J", "Q", "K"
        )

        return palos.flatMap { palo ->
            numeros.map { numero ->
                val valor = when (numero) {
                    "A" -> 11
                    "J", "Q", "K" -> 10
                    else -> numero.toInt()
                }

                Carta(numero, palo, valor)
            }
        }.shuffled().toMutableList()
    }

    fun puntos(cartas: List<Carta>): Int {
        var total = cartas.sumOf { it.valor }
        var ases = cartas.count { it.numero == "A" }

        while (total > 21 && ases > 0) {
            total -= 10
            ases--
        }

        return total
    }

    fun guardarResultado(
        ganador: String,
        perdedor: String,
        combinacion: String
    ) {
        val fecha = SimpleDateFormat(
            "dd/MM/yyyy HH:mm:ss",
            Locale.getDefault()
        ).format(Date())

        resultados.add(
            0,
            Resultado(
                ganador,
                perdedor,
                fecha,
                combinacion
            )
        )

        persistirResultados()
    }
}

