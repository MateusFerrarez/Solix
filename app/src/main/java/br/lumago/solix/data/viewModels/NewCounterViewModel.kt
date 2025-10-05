package br.lumago.solix.data.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.lumago.solix.data.repositories.CounterRepository

class NewCounterViewModel (private val repository: CounterRepository) : ViewModel() {

    class NewCounterViewModelFactory(private val repository: CounterRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NewCounterViewModel(repository) as T
        }
    }
}