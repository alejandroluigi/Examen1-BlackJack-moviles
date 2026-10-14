package com.example.blackjack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.blackjack.ui.theme.BlackJackTheme
import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

fun colorTema(): Color {
    return when (DatosJuego.tema) {
        1 -> Color(0xFF172554)
        2 -> Color(0xFF4A1942)
        else -> Color(0xFF164B35)
    }
}

fun colorAcento(): Color {
    return when (DatosJuego.tema) {
        1 -> Color(0xFF60A5FA)
        2 -> Color(0xFFF0ABFC)
        else -> Color(0xFFE3B341)
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DatosJuego.inicializar(this)

        setContent {
            BlackJackTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorTema()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "JUEGO DEL BlackJack",
                            fontSize = 32.sp,
                            color = Color.White
                        )

                        Spacer(Modifier.height(12.dp))

                        Text(
                            "Objetivo: ${DatosJuego.objetivo} puntos",
                            color = Color.White
                        )

                        Spacer(Modifier.height(25.dp))

                        BotonMenu("Jugar") {
                            abrir(JuegoActivity::class.java)
                        }

                        BotonMenu("Ver score") {
                            abrir(ScoreActivity::class.java)
                        }

                        BotonMenu("Instrucciones") {
                            abrir(InstruccionesActivity::class.java)
                        }

                        BotonMenu("Configuración") {
                            abrir(ConfiguracionActivity::class.java)
                        }
                    }
                }
            }
        }
    }

    private fun abrir(clase: Class<out Activity>) {
        startActivity(Intent(this, clase))
    }
}

@Composable
fun BotonMenu(texto: String, accion: () -> Unit) {
    Button(
        onClick = accion,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorAcento(),
            contentColor = Color.Black
        )
    ) {
        Text(texto, fontSize = 17.sp)
    }
}
