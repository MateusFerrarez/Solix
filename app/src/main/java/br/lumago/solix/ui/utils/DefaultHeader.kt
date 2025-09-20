package br.lumago.solix.ui.utils

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import br.lumago.solix.ui.theme.bgHeaderBrush
import br.lumago.solix.ui.theme.titleStyle

@Composable
fun Header(
    title: String,
    activity: Activity
) {
    Row(
        modifier = Modifier
            .height(85.dp)
            .systemBarsPadding()
            .fillMaxWidth()
            .background(bgHeaderBrush),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                activity.finish()
            }
        ) {
            Icon(
                painter = rememberVectorPainter(Icons.AutoMirrored.Filled.KeyboardArrowLeft),
                contentDescription = "Home icon",
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )
        }

        Spacer(modifier = Modifier.width(15.dp))

        Text(
            text = title,
            style = titleStyle,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}