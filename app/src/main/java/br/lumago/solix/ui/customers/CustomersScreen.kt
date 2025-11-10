package br.lumago.solix.ui.customers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import br.lumago.solix.data.repositories.CustomerRepository
import br.lumago.solix.data.viewModels.customer.CustomerViewModel
import br.lumago.solix.ui.customers.components.CustomersView

class CustomersScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProvider(
            this,
            CustomerViewModel.CustomerViewModelFactory(CustomerRepository(this))
        )[CustomerViewModel::class]

        setContent {
            CustomersView(viewModel)
        }
    }
}