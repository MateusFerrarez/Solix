package br.lumago.solix.ui.utils.buttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import br.lumago.solix.ui.theme.corBotao
import br.lumago.solix.ui.theme.titleStyle

@Composable
fun DefaultButton(
    onClick: () -> Unit,
    text: String,
    fillMaxWidth: Boolean = false
) {
    Button(
        onClick = { onClick() },
        colors = corBotao(),
        modifier = when (fillMaxWidth) {
            true -> Modifier.fillMaxWidth()
            false -> Modifier
        }
    ) {
        Text(
            text = text,
            style = titleStyle,
            fontSize = 16.sp
        )
    }
}