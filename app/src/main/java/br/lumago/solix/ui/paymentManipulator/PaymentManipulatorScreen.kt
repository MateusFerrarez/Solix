package br.lumago.solix.ui.paymentManipulator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import br.lumago.solix.data.repositories.PaymentsRepository
import br.lumago.solix.data.viewModels.payment.PaymentManipulatorViewModel
import br.lumago.solix.ui.paymentManipulator.components.NewPayment

class PaymentManipulatorScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(
            this,
            PaymentManipulatorViewModel.NewPaymentViewModelFactory(PaymentsRepository(this))
        )[PaymentManipulatorViewModel::class]
        setContent {
            NewPayment(viewModel)
        }
    }
}