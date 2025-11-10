package br.lumago.solix.ui.customerHandler

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import br.lumago.solix.data.repositories.CustomerRepository
import br.lumago.solix.data.viewModels.customer.CustomerHandlerViewModel
import br.lumago.solix.ui.customerHandler.components.CustomerHandlerView

class CustomerHandlerScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(
            this,
            CustomerHandlerViewModel.CustomerHandlerViewModelFactory(
                CustomerRepository(this)
            )
        )[CustomerHandlerViewModel::class]
        setContent {
            CustomerHandlerView(viewModel)
        }
    }
}