package br.lumago.solix.data.viewModels.newCounter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.lumago.solix.data.entities.relations.ItemCard
import br.lumago.solix.data.repositories.ProductsRepository
import br.lumago.solix.exceptions.newCounter.ProductNotFoundException
import br.lumago.solix.ui.newCounter.components.NewCounterView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewCounterViewModel(private val repository: ProductsRepository) : ViewModel() {
    var itemCardList = MutableStateFlow<List<ItemCard>>(emptyList())
        private set
    //
    var exception = MutableStateFlow<Exception?>(null)
        private set
    //

    // Insert
    private fun insert(itemCard: ItemCard) {
        val tempList = itemCardList.value.toMutableList()
        tempList.add(itemCard)
        itemCardList.value = tempList
    }

    // Get
    fun getItemCardByBarcode(barcode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val tempItem = repository.getItemCardByBarcode(barcode) ?: throw ProductNotFoundException(
                    "Produto não encontrado"
                )

                insert(tempItem)
            } catch (e: Exception) {
                exception.update { e }
            }
        }
    }

    // Update
    fun updateException(e: Exception?) {
        exception.update { e }
    }

    class NewCounterViewModelFactory(private val repository: ProductsRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewCounterViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NewCounterViewModel(repository) as T
            }

            throw IllegalArgumentException("Unknown ViewModel class ${modelClass.name}")
        }
    }
}