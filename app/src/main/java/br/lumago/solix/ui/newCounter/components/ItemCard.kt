package br.lumago.solix.ui.newCounter.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.lumago.solix.data.entities.relations.ItemCard

@Composable
fun CardItem(
    itemCard: ItemCard
) {
    Card(
        modifier = Modifier.fillMaxWidth(0.85F),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD9D9D9))

    ) {
        Text(
            text = itemCard.descricao,
            style = TextStyle(fontSize = 16.sp, textAlign = Center),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(16.dp)
                .fillMaxWidth()
        )
    }
}