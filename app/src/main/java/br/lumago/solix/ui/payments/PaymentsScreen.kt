package br.lumago.solix.ui.payments

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import br.lumago.solix.data.repositories.PaymentsRepository
import br.lumago.solix.data.viewModels.payment.PaymentsViewModel
import br.lumago.solix.ui.payments.components.PaymentsView

class PaymentsScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(
            this,
            PaymentsViewModel.PaymentViewModelFactory(
                PaymentsRepository(this)
            )
        )[PaymentsViewModel::class]

        setContent {
            PaymentsView(viewModel)
        }
    }
}