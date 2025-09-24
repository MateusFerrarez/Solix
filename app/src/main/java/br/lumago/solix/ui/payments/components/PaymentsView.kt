package br.lumago.solix.ui.payments.components

import android.content.Intent
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.lumago.solix.ui.newPayment.NewPaymentScreen
import br.lumago.solix.ui.utils.ActionButton
import br.lumago.solix.ui.utils.Header

@Composable
fun PaymentsView() {
    val activity = LocalActivity.current!!

    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Column {
            Header(
                title = "Mensalidades",
                activity
            )

            LazyColumn { }
        }

        ActionButton(
            onClick = {
                val newPaymentScreen = Intent(activity, NewPaymentScreen::class.java)
                activity.startActivity(newPaymentScreen)
            },
            text = "Nova mensalidade",
            modifier = Modifier
                .padding(5.dp)
                .align(Alignment.BottomEnd)
        )
    }
}