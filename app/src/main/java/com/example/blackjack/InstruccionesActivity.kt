package com.example.blackjack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class InstruccionesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DatosJuego.inicializar(this)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorTema()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Text(
                            "INSTRUCCIONES",
                            color = Color.White,
                            fontSize = 27.sp
                        )

                        Spacer(Modifier.height(12.dp))

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .verticalScroll(rememberScrollState())
                        ) {
                            val reglas = listOf(
                                "1. El objetivo es llegar a la puntuación " +
                                        "seleccionada sin superarla.",

                                "2. Se usa una baraja inglesa de 52 cartas " +
                                        "sin comodines.",

                                "3. Las cartas del 2 al 10 valen su número.",

                                "4. J, Q y K valen 10 puntos.",

                                "5. El as vale 11 o 1 punto, dependiendo " +
                                        "de la puntuación total.",

                                "6. Al iniciar, jugador y crupier reciben " +
                                        "dos cartas.",

                                "7. El jugador puede pedir cartas mientras " +
                                        "no haya terminado su turno.",

                                "8. Si supera el objetivo, pierde.",

                                "9. Al plantarse, el crupier juega " +
                                        "automáticamente.",

                                "10. En el modo 21, el crupier pide " +
                                        "hasta llegar a 17 puntos.",

                                "11. Gana quien tenga la puntuación válida " +
                                        "más alta sin superar el objetivo.",

                                "12. Si ambos tienen la misma puntuación, " +
                                        "la partida termina en empate.",

                                "13. El historial muestra ganador, perdedor, " +
                                        "fecha, hora y combinación.",

                                "14. En configuración se cambia el tema " +
                                        "y la puntuación objetivo.",

                                "15. Retirarse significa abandonar la mano actual. " +
                                        "Al hacerlo, pierdes la ronda y el crupier gana.",

                                "16. El crupier no realiza apuestas. Cuando el jugador " +
                                        "se planta, el crupier revela sus cartas y completa " +
                                        "su turno. Si tiene menos de 17 puntos, debe pedir " +
                                        "otra carta. Cuando alcanza 17 puntos o más, debe " +
                                        "plantarse. El valor del As puede ser 1 u 11 puntos, " +
                                        "según la combinación de cartas y sin superar 21 " +
                                        "cuando sea posible.",

                                "17. Una vez que el crupier termina su turno, se " +
                                        "comparan las puntuaciones. Gana quien tenga la " +
                                        "puntuación más alta sin superar 21. Si el jugador " +
                                        "supera 21, pierde. Si el crupier supera 21 y el " +
                                        "jugador no, gana el jugador. Si ambos obtienen " +
                                        "la misma puntuación, la mano termina en empate " +
                                        "si esa regla está implementada."
                            )

                            reglas.forEach { regla ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                ) {
                                    Text(
                                        regla,
                                        modifier = Modifier.padding(12.dp),
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }

                        Button(
                            onClick = { finish() },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Volver al menú")
                        }
                    }
                }
            }
        }
    }
}
