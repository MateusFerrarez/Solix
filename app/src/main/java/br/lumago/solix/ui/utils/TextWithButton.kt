package br.lumago.solix.ui.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import br.lumago.solix.ui.theme.BotaoCinzaoColor
import br.lumago.solix.ui.theme.titleStyle

@Composable
fun TextWithButton(
    text: String,
    buttonText: String,
    onClick: () -> Unit,
    widthPercentage: Float
) {
    Column {
        Text(
            text = text,
            fontSize = 14.sp,
            style = titleStyle,
            color = Color.Black
        )

        Button(
            onClick = { onClick() },
            modifier = Modifier.fillMaxWidth(widthPercentage),
            colors = BotaoCinzaoColor()
        ) {
            Text(
                text = buttonText,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
        }
    }
}