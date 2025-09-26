package br.lumago.solix.ui.newPayment.components

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import br.lumago.solix.data.viewModels.NewPaymentViewModel
import br.lumago.solix.ui.utils.Header
import br.lumago.solix.ui.utils.TextWithButton
import br.lumago.solix.ui.utils.TextWithTextField

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

        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
        ) {
            TextWithButton(
                text = "Cliente:",
                buttonText = "",
                widthPercentage = 0.99f,
                onClick = {}
            )

            Spacer(modifier = Modifier.height(20.dp))

            TextWithButton(
                text = "Indicação:",
                buttonText = "",
                widthPercentage = 0.99f,
                onClick = {}
            )

            TextWithTextField(
                "",
                TextFieldValue(),
                true,
                onValueChange = {},
                heigth = 60.dp,
                0.50f
            )
        }

    }
}