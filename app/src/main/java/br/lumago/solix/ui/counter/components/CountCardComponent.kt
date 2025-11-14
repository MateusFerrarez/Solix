package br.lumago.solix.ui.counter.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.lumago.solix.data.entities.relations.CountCard
import br.lumago.solix.ui.theme.boldStyle
import br.lumago.solix.ui.theme.cardCinzaoColor
import br.lumago.solix.ui.theme.normalStyle
import br.lumago.solix.ui.utils.formatting.FormatDate

@Composable
fun CountCard(countCard: CountCard){
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
                text = "${countCard.countId} - Quantidade de produtos: ${countCard.countQuantity}",
                style = boldStyle,
                maxLines = 1,
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Data da contagem: ${FormatDate(countCard.date).formatFromDb()}",
                style = boldStyle,
                maxLines = 1,
            )
        }
    }
}