package br.lumago.solix.ui.utils.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import br.lumago.solix.ui.theme.cardCinzaoColor
import br.lumago.solix.ui.theme.normalStyle
import br.lumago.solix.ui.theme.titleStyle
import br.lumago.solix.ui.utils.buttons.DefaultButton

@Composable
fun ChooserDialog(
    onDimissClick: () -> Unit,
    onPositiveClick: () -> Unit,
    message: String,
) {
    Dialog(
        onDismissRequest = {
        }
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = cardCinzaoColor()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = message,
                    style = titleStyle,
                    fontSize = 16.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    DefaultButton(
                        onClick = { onDimissClick() },
                        text = "Não"
                    )

                    Spacer(modifier = Modifier.width(15.dp))

                    DefaultButton(
                        onClick = {
                            onPositiveClick()
                        },
                        text = "Sim"
                    )
                }
            }
        }
    }
}