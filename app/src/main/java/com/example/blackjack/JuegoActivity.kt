package com.example.blackjack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class JuegoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DatosJuego.inicializar(this)

        setContent {
            var baraja by remember {
                mutableStateOf(DatosJuego.crearBaraja())
            }
            val jugador = remember { mutableStateListOf<Carta>() }
            val crupier = remember { mutableStateListOf<Carta>() }

            var terminado by remember { mutableStateOf(false) }
            var mensaje by remember { mutableStateOf("Tu turno") }

            fun sacar(destino: MutableList<Carta>) {
                if (baraja.isNotEmpty()) {
                    destino.add(baraja.removeAt(0))
                }
            }

            fun nuevaPartida() {
                baraja = DatosJuego.crearBaraja()
                jugador.clear()
                crupier.clear()
                repeat(2) {
                    sacar(jugador)
                    sacar(crupier)
                }
                terminado = false
                mensaje = "Tu turno"
            }

            fun finalizar() {
                if (terminado) return

                terminado = true
                val pj = DatosJuego.puntos(jugador)
                val pc = DatosJuego.puntos(crupier)

                val ganador = when {
                    pj > DatosJuego.objetivo ->
                        "Crupier (dispositivo)"
                    pc > DatosJuego.objetivo -> "Jugador"
                    pj > pc -> "Jugador"
                    pc > pj -> "Crupier (dispositivo)"
                    else -> "Empate"
                }

                mensaje = when (ganador) {
                    "Jugador" -> "¡Ganaste!"
                    "Crupier (dispositivo)" -> "Ganó el crupier"
                    else -> "¡Empate!"
                }

                val perdedor = when (ganador) {
                    "Jugador" -> "Crupier (dispositivo)"
                    "Crupier (dispositivo)" -> "Jugador"
                    else -> "Nadie"
                }

                val combinacion = when (ganador) {
                    "Jugador" -> jugador.joinToString(" ")
                    "Crupier (dispositivo)" ->
                        crupier.joinToString(" ")
                    else -> "Jugador: ${jugador.joinToString(" ")}"
                }

                DatosJuego.guardarResultado(
                    ganador, perdedor, combinacion
                )
            }

            fun turnoCrupier() {
                // El crupier pide cartas mientras tenga menos de 17 puntos.
                while (
                    DatosJuego.puntos(crupier) < DatosJuego.objetivo - 4 &&
                    baraja.isNotEmpty()
                ) {
                    sacar(crupier)
                }

                // Al llegar a 17 o más, termina su turno.
                finalizar()
            }

            fun plantarse() {
                if (!terminado) {
                    turnoCrupier()
                }
            }

            fun retirarse() {
                if (!terminado) {
                    terminado = true
                    mensaje = "Te has retirado. El crupier gana la mano."

                    DatosJuego.guardarResultado(
                        ganador = "Crupier",
                        perdedor = "Jugador",
                        combinacion = "Jugador se retiró de la mano"
                    )
                }
            }

            LaunchedEffect(Unit) {
                nuevaPartida()
            }

            fun pedirCarta() {
                if (terminado) return

                sacar(jugador)
                val puntos = DatosJuego.puntos(jugador)

                if (puntos > DatosJuego.objetivo) {
                    finalizar()
                } else if (puntos == DatosJuego.objetivo) {
                    plantarse()
                }
            }

            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorTema()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "PARTIDA",
                            color = Color.White,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(Modifier.height(18.dp))

                        Text(
                            "CRUPIER (DISPOSITIVO)",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )

                        CartasFila(
                            cartas = crupier,
                            ocultar = !terminado
                        )

                        Text(
                            text = if (terminado) {
                                "Puntos: ${DatosJuego.puntos(crupier)}"
                            } else {
                                "Puntos: ?"
                            },
                            color = Color.White
                        )

                        Spacer(Modifier.height(20.dp))

                        Text(
                            "JUGADOR",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )

                        CartasFila(jugador)

                        Text(
                            "Puntos: ${DatosJuego.puntos(jugador)} / " +
                                    DatosJuego.objetivo,
                            color = Color.White
                        )

                        Spacer(Modifier.height(18.dp))

                        Text(
                            mensaje,
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(Modifier.height(12.dp))

                        Button(
                            onClick = { pedirCarta() },
                            enabled = !terminado,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Pedir carta")
                        }

                        Button(
                            onClick = { plantarse() },
                            enabled = !terminado,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Plantarse")
                        }

                        Button(
                            onClick = { retirarse() },
                            enabled = !terminado,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Retirarse")
                        }

                        Button(
                            onClick = { nuevaPartida() },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Nueva partida")
                        }

                        OutlinedButton(
                            onClick = { finish() },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                "Volver al menú",
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CartasFila(cartas: List<Carta>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        items(cartas) { carta ->
            val rojo = carta.palo == "♥" || carta.palo == "♦"

            Column(
                modifier = Modifier
                    .padding(3.dp)
                    .size(width = 55.dp, height = 78.dp)
                    .background(
                        Color.White,
                        RoundedCornerShape(7.dp)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    carta.numero,
                    color = if (rojo) Color.Red else Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    carta.palo,
                    color = if (rojo) Color.Red else Color.Black,
                    fontSize = 20.sp
                )
            }
        }
    }
}
