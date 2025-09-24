package br.lumago.solix.ui.newPayment.components

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.lumago.solix.data.viewModels.NewPaymentViewModel
import br.lumago.solix.ui.utils.Header
import br.lumago.solix.ui.utils.TextWithButton

@Composable
fun NewPayment(viewModel: NewPaymentViewModel) {
    val activity = LocalActivity.current!!

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Header(
            "Nova mensalidade",
            activity
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextWithButton(
            text = "Cliente:",
            buttonText = "",
            widthPercentage = 0.9f,
            onClick = {}
        )

    }
}