package br.lumago.solix.ui.utils.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.lumago.solix.ui.theme.botaoCinzaoColor
import br.lumago.solix.ui.theme.normalStyle
import br.lumago.solix.ui.theme.titleStyle

@Composable
fun TextWithDatePicker(
    text: String,
    buttonText: String?,
    onSelectDate: () -> Unit
) {
    Column {
        Text(
            text = text,
            fontSize = 15.sp,
            style = titleStyle,
            color = Color.Black
        )

        Button(
            onClick = { onSelectDate() },
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth(0.4f),
            colors = botaoCinzaoColor()
        ) {
            if (buttonText != null) {
                Text(
                    text = buttonText,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    style = normalStyle
                )
            }
        }

    }
}