package br.lumago.solix.ui.utils.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import br.lumago.solix.R
import br.lumago.solix.ui.theme.corCard
import br.lumago.solix.ui.theme.titleStyle
import br.lumago.solix.ui.utils.buttons.DefaultButton

@Composable
fun StatusDialog(
    onClick: () -> Unit,
    message: String,
    isError: Boolean
) {
    val icon = when(isError){
        true -> R.drawable.error_icon
        false -> R.drawable.success_icon
    }

    Dialog(
        onDismissRequest = {
            onClick()
        }
    ) {
        Card(colors = corCard()) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = message,
                    style = titleStyle,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Icon(
                    imageVector = ImageVector.vectorResource(icon),
                    contentDescription = "Icon",
                    modifier = Modifier.size(42.dp),
                    tint = Color.Unspecified
                )

                Spacer(modifier = Modifier.height(10.dp))

                DefaultButton(
                    onClick = {onClick()},
                    text = "Fechar"
                )

                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }
}