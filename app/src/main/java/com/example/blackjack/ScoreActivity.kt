package com.example.blackjack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class ScoreActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DatosJuego.inicializar(this)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorTema()
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            "SCORE DE PARTIDAS",
                            color = Color.White,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(Modifier.height(12.dp))

                        if (DatosJuego.resultados.isEmpty()) {
                            Text(
                                "Todavía no hay partidas.",
                                color = Color.White
                            )
                        } else {
                            LazyColumn(
                                modifier = Modifier.weight(1f),
                                verticalArrangement =
                                    Arrangement.spacedBy(10.dp)
                            ) {
                                items(DatosJuego.resultados) { r ->
                                    Card(
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color.White
                                        )
                                    ) {
                                        Column(
                                            Modifier.padding(12.dp)
                                        ) {
                                            Text(
                                                "GANADOR: ${r.ganador}",
                                                color = Color(0xFF167344),
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 18.sp
                                            )

                                            Text(
                                                "PERDEDOR: ${r.perdedor}",
                                                color = Color(0xFFB3261E),
                                                fontWeight = FontWeight.Bold
                                            )

                                            Text("Fecha y hora: ${r.fecha}")
                                            Text(
                                                "Combinación ganadora: " +
                                                        r.combinacion
                                            )
                                        }
                                    }
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
