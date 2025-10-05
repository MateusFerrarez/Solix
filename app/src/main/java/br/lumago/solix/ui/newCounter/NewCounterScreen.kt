package br.lumago.solix.ui.newCounter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import br.lumago.solix.data.repositories.CounterRepository
import br.lumago.solix.data.viewModels.NewCounterViewModel
import br.lumago.solix.ui.newCounter.components.NewCounter

class NewCounterScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(
            this,
            NewCounterViewModel.NewCounterViewModelFactory(CounterRepository(this))
        )[NewCounterViewModel::class]
        setContent {
            NewCounter(viewModel)
        }
    }
}