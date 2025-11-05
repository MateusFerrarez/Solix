package br.lumago.solix.ui.paymentHandler

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import br.lumago.solix.data.repositories.PaymentsRepository
import br.lumago.solix.data.viewModels.payment.PaymentHandlerViewModel
import br.lumago.solix.ui.paymentHandler.components.NewPayment

class PaymentHandlerScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(
            this,
            PaymentHandlerViewModel.NewPaymentViewModelFactory(PaymentsRepository(this))
        )[PaymentHandlerViewModel::class]
        setContent {
            NewPayment(viewModel)
        }
    }
}