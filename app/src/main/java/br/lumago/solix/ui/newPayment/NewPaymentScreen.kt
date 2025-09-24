package br.lumago.solix.ui.newPayment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import br.lumago.solix.data.repositories.PaymentsRepository
import br.lumago.solix.data.viewModels.NewPaymentViewModel
import br.lumago.solix.ui.newPayment.components.NewPayment

class NewPaymentScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(
            this,
            NewPaymentViewModel.NewPaymentViewModelFactory(PaymentsRepository(this))
        )[NewPaymentViewModel::class]
        setContent {
            NewPayment(viewModel)
        }
    }
}