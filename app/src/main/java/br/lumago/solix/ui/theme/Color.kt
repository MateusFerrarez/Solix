package br.lumago.solix.ui.theme

import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val corVermelho = Color(0xFFAA2727)

val corBotao = Color(0xFF2B4632)
val corTexto = Color(0xFF709E7C)
val corFundoCard = Color(0xFFD9D9D9)

val corGradienteHome1 = Color(0xFFB5FFC9)
val corGradienteHome2 = Color(0xFF6D9979)

val gradientColorHeader1 = Color(0xFF6AAC7B)
val gradientColorHeader2 = Color(0xFF2B4632)

val bgHomeBrush = Brush.linearGradient(listOf(corGradienteHome1, corGradienteHome2))

val bgHeaderBrush = Brush.linearGradient(listOf(gradientColorHeader1, gradientColorHeader2))

@Composable
fun botaoCinzaoColor(): ButtonColors {
    return ButtonDefaults.buttonColors(
        containerColor = corFundoCard
    )
}

@Composable
fun cardCinzaoColor(): CardColors {
    return CardDefaults.cardColors(
        containerColor = corFundoCard
    )
}

@Composable
fun corBotao(): ButtonColors {
    return ButtonDefaults.buttonColors(
        containerColor = corBotao
    )
}

@Composable
fun corCard(): CardColors {
    return CardDefaults.cardColors(
        containerColor = Color.White
    )
}

@Composable
fun estiloField(): TextFieldColors {
    return TextFieldDefaults.colors(
        focusedLabelColor = Color.Black,
        focusedIndicatorColor = Color.Transparent,
        focusedContainerColor = corFundoCard,
        focusedTextColor = Color.Black,
        errorIndicatorColor = Color.Transparent,
        errorCursorColor = Color.Red,
        errorContainerColor = Color.White,
        errorLabelColor = Color.Red,
        errorLeadingIconColor = Color.Red,
        errorTrailingIconColor = Color.Red,
        errorTextColor = Color.Red,
        cursorColor = corTexto,
        selectionColors = TextSelectionColors(
            handleColor = corTexto,
            backgroundColor = corTexto.copy(alpha = 0.6f)
        ),
        unfocusedLabelColor = Color.Black,
        unfocusedTextColor = Color.LightGray,
        unfocusedContainerColor = corFundoCard,
        unfocusedIndicatorColor = Color.Transparent
    )
}