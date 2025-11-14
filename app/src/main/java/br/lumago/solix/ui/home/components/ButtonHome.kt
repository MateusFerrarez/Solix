package br.lumago.solix.ui.home.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.lumago.solix.ui.theme.corFundoCard
import br.lumago.solix.ui.theme.corTexto
import br.lumago.solix.ui.theme.titleStyle

@Composable
fun ButtonHome(
    painter: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Spacer(modifier = Modifier.height(35.dp))

    Button(
        onClick = { onClick() },
        contentPadding = PaddingValues(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = corFundoCard),
        shape = RoundedCornerShape(55f)
    ) {
        Row(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth(0.70f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = painter,
                contentDescription = "",
                modifier = Modifier.size(30.dp),
                tint = Color.Black
            )

            Spacer(modifier = Modifier.width(15.dp))

            Text(text = text,
                style = titleStyle,
                fontSize = 24.sp,
                color = corTexto
            )
        }
    }
}