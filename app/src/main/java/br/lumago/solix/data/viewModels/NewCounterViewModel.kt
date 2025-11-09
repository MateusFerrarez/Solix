package br.lumago.solix.data.viewModels

import android.R
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.lumago.solix.data.entities.relations.ItemCard
import br.lumago.solix.data.repositories.CounterRepository
import br.lumago.solix.data.repositories.ProductsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewCounterViewModel (
    private val repositoryCounter: CounterRepository,
    private val repositoryProducts: ProductsRepository
) : ViewModel() {
    fun updateBarcode(code: String){
        viewModelScope.launch {
            if(repositoryProducts.selectProduct(code) != null) {
                novoItem.value = ItemCard(repositoryProducts.selectProduct(code)!!)
                addItem()
            } else {
                showErrorDialog.value = true
            }
        }
    }

    var showErrorDialog = MutableStateFlow<Boolean>(false)
        private set

    var listaItem = MutableStateFlow<List<ItemCard>>(listOf())
        private set

    var novoItem = MutableStateFlow<ItemCard?>(null)
        private set

    fun addItem(){
        val itemToAdd = novoItem.value
        if (itemToAdd != null) {
            listaItem.update { currentList ->
                currentList + itemToAdd
            }
        }
    }

    fun dismissError(){
        showErrorDialog.value = false
    }

    class NewCounterViewModelFactory(private val repositoryCounter: CounterRepository, private val repositoryProducts: ProductsRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NewCounterViewModel(repositoryCounter, repositoryProducts) as T
        }
    }
}