package br.lumago.solix.data.viewModels.sync

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.lumago.solix.data.handlers.CustomerSyncHandler
import br.lumago.solix.data.repositories.CustomerRepository
import br.lumago.solix.exceptions.sync.SyncCustomerException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime

sealed class SyncStatus {
    object Wating : SyncStatus()
    data class Loading(val value: String) : SyncStatus()
    data class Error(val message: String) : SyncStatus()
    object Finished : SyncStatus()
}

class SyncViewModel : ViewModel() {

    // Status
    var currentSyncStatus = MutableStateFlow<SyncStatus>(SyncStatus.Wating)
        private set

    var progressBarValue = MutableStateFlow(0.0f)
        private set

    var buttonStatus = MutableStateFlow(true)
        private set

    // Exception
    var exception = MutableStateFlow<Exception?>(null)
        private set


    fun startSync(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                currentSyncStatus.update { SyncStatus.Loading("Iniciando sincronização...") }
                progressBarValue.update { 0.2f }
                delay(1500L)
                currentSyncStatus.update { SyncStatus.Loading("Lendo clientes...") }
                syncCustomers(context)
                delay(1500L)
                progressBarValue.update { 0.25f }
                finishSync()
            } catch (e: Exception) {
                exception.update { e }
            }
        }
    }

    private suspend fun syncCustomers(context: Context) {
        try {
            val handler = CustomerSyncHandler(
                CustomerRepository(
                    context
                ),
                currentSyncStatus
            )
            handler.startSync(LocalDateTime.now().toString())
            delay(3500L)
            currentSyncStatus.update { SyncStatus.Loading("Leitura de clientes finalizada...") }
        } catch (e: Exception) {
            currentSyncStatus.update { SyncStatus.Error("Erro ao sincronizar clientes!") }
            throw SyncCustomerException(e.message!!)
        }
    }

    private suspend fun syncProducts(context: Context){

    }

    private suspend fun finishSync() {
        currentSyncStatus.update { SyncStatus.Loading("Finalizando sincronização...") }
        delay(1500)
        currentSyncStatus.update { SyncStatus.Finished }
    }

    class SyncViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SyncViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SyncViewModel() as T
            }
            throw IllegalArgumentException("Unknown ViewModel class ${modelClass.name}")
        }
    }
}