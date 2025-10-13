package br.lumago.solix.ui.utils.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import br.lumago.solix.ui.theme.corBotao

@Composable
fun ProgressCircle() {
    CircularProgressIndicator(
        color = corBotao,
    )
}