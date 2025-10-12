package br.lumago.solix.data.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.lumago.solix.data.entities.relations.ItemCard
import br.lumago.solix.data.repositories.CounterRepository
import kotlinx.coroutines.flow.MutableStateFlow

class NewCounterViewModel (private val repository: CounterRepository) : ViewModel() {

    var listaItem = MutableStateFlow<List<ItemCard>>(listOf(ItemCard("135354 - TESTE"), ItemCard("1111111 - AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")))
        private set
    class NewCounterViewModelFactory(private val repository: CounterRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NewCounterViewModel(repository) as T
        }
    }
}