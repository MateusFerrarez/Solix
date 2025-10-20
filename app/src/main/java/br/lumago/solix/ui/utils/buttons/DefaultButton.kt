package br.lumago.solix.ui.utils.buttons

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import br.lumago.solix.ui.theme.corBotao
import br.lumago.solix.ui.theme.titleStyle

@Composable
fun DefaultButton(
    onClick: () -> Unit,
    text: String
) {
    Button(
        onClick = { onClick() },
        colors = corBotao()
    ) {
        Text(
            text = text,
            style = titleStyle,
            fontSize = 16.sp
        )
    }
}