package com.example.blackjack

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding

fun nombreImagen(carta: Carta): String {
    val numero = when (carta.numero) {
        "A" -> "as"
        "J" -> "jota"
        "Q" -> "reina"
        "K" -> "rey"
        else -> carta.numero
    }

    val palo = when (carta.palo) {
        "♥" -> "corazon"
        "♦" -> "diamante"
        "♣" -> "trebol"
        "♠" -> "pica"
        else -> "corazon"
    }

    return "carta_${numero}_${palo}"
}

@Composable
fun CartasFila(
    cartas: List<Carta>,
    ocultar: Boolean = false
) {
    val contexto = LocalContext.current

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(horizontal = 4.dp)
    ) {
        itemsIndexed(cartas) { indice, carta ->

            // Ocultar solamente la primera carta si corresponde
            val estaOculta = ocultar && indice == 0

            val nombre = if (estaOculta) {
                "carta_volteada"
            } else {
                nombreImagen(carta)
            }

            val recurso = contexto.resources.getIdentifier(
                nombre,
                "drawable",
                contexto.packageName
            )

            if (recurso != 0) {
                Image(
                    painter = painterResource(id = recurso),
                    contentDescription = if (estaOculta) {
                        "Carta boca abajo"
                    } else {
                        "Carta ${carta.numero} de ${carta.palo}"
                    },
                    modifier = Modifier.paddingCarta(),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}


private fun Modifier.paddingCarta(): Modifier {
    return this
        .width(68.dp)
        .height(100.dp)
}
