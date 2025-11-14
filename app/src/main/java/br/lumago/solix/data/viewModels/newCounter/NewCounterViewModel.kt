package br.lumago.solix.data.viewModels.newCounter

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.lumago.solix.data.entities.Counts
import br.lumago.solix.data.entities.relations.ItemCard
import br.lumago.solix.data.repositories.CountRepository
import br.lumago.solix.data.repositories.ProductsRepository
import br.lumago.solix.exceptions.newCounter.ProductNotFoundException
import br.lumago.solix.ui.newCounter.components.NewCounterView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewCounterViewModel(
    private val repository: ProductsRepository,
    private val countRepository: CountRepository
) : ViewModel() {
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
                val tempItem =
                    repository.getItemCardByBarcode(barcode) ?: throw ProductNotFoundException(
                        "Produto não encontrado"
                    )

                insert(tempItem)
            } catch (e: Exception) {
                exception.update { e }
            }
        }
    }

    //
    fun saveCount(activity: Activity) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val tempCount = Counts(
                    totalCount = itemCardList.value.size,
                )

                countRepository.insert(
                    tempCount,
                    itemCardList.value
                )

                activity.setResult(1)
                activity.finish()
            } catch (e: Exception) {
                exception.update { e }
            }
        }
    }

    // Update
    fun updateException(e: Exception?) {
        exception.update { e }
    }

    class NewCounterViewModelFactory(
        private val repository: ProductsRepository,
        private val countRepository: CountRepository
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewCounterViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NewCounterViewModel(
                    repository,
                    countRepository
                ) as T
            }

            throw IllegalArgumentException("Unknown ViewModel class ${modelClass.name}")
        }
    }
}