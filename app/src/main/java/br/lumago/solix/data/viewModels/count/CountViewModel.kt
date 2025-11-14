package br.lumago.solix.data.viewModels.count

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.lumago.solix.data.entities.relations.CountCard
import br.lumago.solix.data.repositories.CountRepository
import br.lumago.solix.exceptions.count.CountException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.emptyList

class CountViewModel(private val repository: CountRepository) : ViewModel() {
    var countList = MutableStateFlow<List<CountCard>>(emptyList())
        private set
    // Dialog
    var showDialog = MutableStateFlow(false)
        private set
    var dialogMessage = MutableStateFlow("")
        private set
    //
    var exception = MutableStateFlow<Exception?>(null)
        private set

    init {
        getCounts()
    }

    // Get
    fun getCounts(){
        viewModelScope.launch {
            try {
                val tempList = repository.getCounts()
                countList.update { tempList }
            } catch (e: Exception) {
                exception.update { CountException(e.message!!) }
            }
        }
    }

    // Update
    fun updateDialogMessage(text: String) {
        dialogMessage.update { text }
    }

    fun updateDialog(value: Boolean) {
        showDialog.update { value }
    }

    fun updateException(value: Exception?){
        exception.update { value }
    }

    class CountViewModelFactory (private val repository: CountRepository) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CountViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return CountViewModel(repository) as T
            }

            throw IllegalArgumentException("Unknown ViewModel class ${modelClass.name}")
        }
    }
}