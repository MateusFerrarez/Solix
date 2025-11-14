package br.lumago.solix.ui.newCounter.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.lumago.solix.data.entities.relations.ItemCard
import br.lumago.solix.ui.theme.cardCinzaoColor
import br.lumago.solix.ui.theme.normalStyle

@Composable
fun ItemCard(itemCard: ItemCard) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = cardCinzaoColor(),
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = itemCard.productName,
                style = normalStyle,
                maxLines = 1,
            )
        }
    }
}