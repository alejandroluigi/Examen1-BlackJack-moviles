package com.example.blackjack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class ConfiguracionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DatosJuego.inicializar(this)

        setContent {
            var objetivo by remember {
                mutableIntStateOf(DatosJuego.objetivo)
            }
            var tema by remember {
                mutableIntStateOf(DatosJuego.tema)
            }

            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorTema()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "CONFIGURACIÓN",
                            color = Color.White,
                            fontSize = 27.sp
                        )

                        Spacer(Modifier.height(20.dp))

                        Text(
                            "Puntuación objetivo",
                            color = Color.White
                        )

                        listOf(11, 21, 25).forEach { valor ->
                            Row(
                                verticalAlignment =
                                    Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = objetivo == valor,
                                    onClick = { objetivo = valor }
                                )
                                Text(
                                    "$valor puntos",
                                    color = Color.White
                                )
                            }
                        }

                        Spacer(Modifier.height(18.dp))

                        Text(
                            "Tema de colores",
                            color = Color.White
                        )

                        listOf(
                            "Verde",
                            "Azul",
                            "Morado"
                        ).forEachIndexed { indice, nombre ->
                            Row(
                                verticalAlignment =
                                    Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = tema == indice,
                                    onClick = { tema = indice }
                                )
                                Text(
                                    nombre,
                                    color = Color.White
                                )
                            }
                        }

                        Spacer(Modifier.height(24.dp))

                        Button(
                            onClick = {
                                DatosJuego.guardarConfiguracion(
                                    nuevoObjetivo = objetivo,
                                    nuevoTema = tema
                                )

                                finish()
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Guardar configuración")
                        }

                        OutlinedButton(
                            onClick = { finish() },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                "Cancelar",
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}
