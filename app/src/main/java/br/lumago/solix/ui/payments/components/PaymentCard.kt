package br.lumago.solix.ui.payments.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.lumago.solix.data.entities.relations.PaymentCard
import br.lumago.solix.ui.theme.boldStyle
import br.lumago.solix.ui.theme.cardCinzaoColor
import br.lumago.solix.ui.theme.corTexto
import br.lumago.solix.ui.theme.corVermelho
import br.lumago.solix.ui.utils.formatting.FormatCurrency
import br.lumago.solix.ui.utils.formatting.FormatDate

@Composable
fun CardPayment(
    payment: PaymentCard,
    onDeleteClick: () -> Unit,
    onCardClick: () -> Unit
) {
    val borderColor = when (payment.isSychronized){
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
        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "${payment.paymentId} - ",
                        style = boldStyle,
                        color = corTexto,
                        fontSize = 16.sp
                    )

                    Text(
                        text = payment.customer,
                        style = boldStyle,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 16.sp,
                        maxLines = 1
                    )
                }

                IconButton(
                    onClick = { onDeleteClick() }
                ) {
                    Icon(
                        painter = rememberVectorPainter(Icons.Default.Delete),
                        contentDescription = "Delete payment",
                        modifier = Modifier.size(25.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(5.dp))

            Row {
                Text(
                    text = "Valor: ${FormatCurrency((payment.value * 100).toString()).formart()}",
                    style = boldStyle,
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            Row {
                Text(
                    text = "Data de contrato: ${FormatDate(payment.contractDate).format()}",
                    style = boldStyle,
                )
            }
        }
    }
}