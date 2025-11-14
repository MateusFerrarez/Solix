package br.lumago.solix.data.viewModels.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.lumago.solix.data.entities.relations.CustomerPosition
import br.lumago.solix.data.repositories.CustomerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MapViewModel(private val repository: CustomerRepository) : ViewModel() {
    var customers = MutableStateFlow<List<CustomerPosition>>(emptyList())
        private set

    init {
        CoroutineScope(Dispatchers.IO).launch {
            getCustomers()
        }
    }

    private suspend fun getCustomers(){
        val tempCustomers = repository.getCustomersPositions()
        customers.update { tempCustomers }
    }

    class MapViewModelFactory(private val repository: CustomerRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MapViewModel(repository) as T
            }

            throw IllegalArgumentException("Unknown ViewModel class ${modelClass.name}")
        }
    }
}