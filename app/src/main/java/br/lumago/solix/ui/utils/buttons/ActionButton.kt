package br.lumago.solix.ui.utils.buttons

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.lumago.solix.ui.theme.corBotao
import br.lumago.solix.ui.theme.titleStyle

@Composable
fun ActionButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier
) {
    FloatingActionButton(
        onClick = { onClick() },
        containerColor = corBotao,
        modifier = modifier
    ) {
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 10.dp)) {
            Icon(
                painter = rememberVectorPainter(Icons.Default.Add),
                contentDescription = "Home icon",
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )

            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = text,
                style = titleStyle,
                fontSize = 16.sp
            )
        }
    }
}