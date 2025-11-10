package br.lumago.solix.ui.customers.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.lumago.solix.data.entities.relations.CustomerCard
import br.lumago.solix.ui.theme.boldStyle
import br.lumago.solix.ui.theme.cardCinzaoColor
import br.lumago.solix.ui.theme.corTexto
import br.lumago.solix.ui.theme.corVermelho
import br.lumago.solix.ui.theme.normalStyle

@Composable
fun CustomerCard(
    customer: CustomerCard,
    onCardClick: () -> Unit
) {
    val borderColor = when (customer.isSync) {
        true -> corTexto
        false -> corVermelho
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = cardCinzaoColor(),
        border = BorderStroke(
            1.dp,
            borderColor
        ),
        onClick = {
            onCardClick()
        }
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "${customer.partnerId} - ",
                        style = boldStyle,
                        color = corTexto,
                        fontSize = 16.sp
                    )

                    Text(
                        text = customer.customerName,
                        style = boldStyle,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 16.sp,
                        maxLines = 1
                    )
                }
            }

            Row {
                Text(
                    text = "Endereço:",
                    style = boldStyle,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 14.sp,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.width(2.dp))

                Text(
                    text = customer.address,
                    style = normalStyle,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 14.sp,
                    maxLines = 2
                )
            }

            Row {
                Text(
                    text = "Cidade:",
                    style = boldStyle,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 14.sp,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.width(2.dp))

                Text(
                    text = customer.city,
                    style = normalStyle,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 14.sp,
                    maxLines = 2
                )
            }
        }
    }
}