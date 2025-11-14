package br.lumago.solix.data.viewModels.customer

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import br.lumago.solix.data.entities.relations.CustomerCard
import br.lumago.solix.data.repositories.CustomerRepository
import br.lumago.solix.exceptions.customer.CustomerSynchronizedException
import br.lumago.solix.exceptions.customer.GetCustomerException
import br.lumago.solix.ui.customerManipulator.CustomerManipulatorScreen
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class CustomerViewModel(private val repository: CustomerRepository) : ViewModel() {
    private val _pagingFlow = MutableStateFlow<Flow<PagingData<CustomerCard>>?>(null)
    val pagingFlow: StateFlow<Flow<PagingData<CustomerCard>>?> = _pagingFlow

    // Dialog
    var showDialog = MutableStateFlow(false)
        private set
    var showChooserDialog = MutableStateFlow(false)
        private set
    var dialogMessage = MutableStateFlow("")
        private set

    // Search
    var searchValue = MutableStateFlow("")
        private set
    var queryValue = MutableStateFlow("%%")
        private set
    var lastQuery: String? = null

    // Exception
    var exception = MutableStateFlow<Exception?>(null)
        private set

    //
    var selectedCustomer = MutableStateFlow<CustomerCard?>(null)
        private set

    init {
        viewModelScope.launch {
            queryValue
                .debounce(350)
                .collectLatest { search ->
                    if (search != lastQuery.toString()) {
                        getCustomers(search)
                        lastQuery = search
                    }
                }
        }
    }

    fun openNewCustomerScreen(
        activity: Activity,
        launcher: ManagedActivityResultLauncher<Intent, ActivityResult>,
    ) {
        val activityNewCustomerScreen = Intent(activity, CustomerManipulatorScreen::class.java)
        launcher.launch(activityNewCustomerScreen)
    }

    fun openEditCustomerScreen(
        activity: Activity,
        launcher: ManagedActivityResultLauncher<Intent, ActivityResult>,
        customer: CustomerCard,
        intentExtra: String?
    ) {
        if (intentExtra != null) {
            val resultCode = when (intentExtra) {
                "customer" -> 1
                else -> 2
            }
            activity.setResult(resultCode, Intent().apply {
                putExtra("localCustomerId", customer.customerId)
                putExtra("selectedCustomer", "${customer.partnerId} - ${customer.customerName}")
            })
            activity.finish()
            return
        }
        val activityEditCustomerScreen = Intent(activity, CustomerManipulatorScreen::class.java)
        activityEditCustomerScreen.putExtra("customerId", customer.customerId)
        activityEditCustomerScreen.putExtra("isSynchronized", customer.isSync)
        launcher.launch(activityEditCustomerScreen)
    }

    // Get
    fun getCustomers(query: String) {
        viewModelScope.launch {
            try {
                _pagingFlow.value = repository
                    .getCustomers(query)
                    .cachedIn(viewModelScope)
            } catch (e: Exception) {
                val customException = GetCustomerException(e.message!!)
                exception.update { customException }
            }
        }
    }

    // Update
    fun updateException(newEx: Exception?) {
        exception.update { newEx }
    }

    fun updateDialogMessage(text: String) {
        dialogMessage.update { text }
    }

    fun updateChooserDialog(value: Boolean) {
        showChooserDialog.update { value }
    }

    fun updateSelectedCustomer(value: CustomerCard?) {
        selectedCustomer.update { value }

        if (value != null) {
            updateChooserDialog(true)
        }
    }

    fun updateDialog(value: Boolean) {
        showDialog.update { value }
    }

    fun updateBusca(value: String) {
        searchValue.update { value }
        val valorFormatado = when {
            searchValue.value.trim().isEmpty() -> "%%"
            searchValue.value.isDigitsOnly() -> searchValue.value.trim()
            else -> "%${searchValue.value.uppercase()}%"
        }
        queryValue.update { valorFormatado }
    }

    // Delete
    fun deleteCustomerById() {
        viewModelScope.launch {
            try {
                updateChooserDialog(false)

                if (selectedCustomer.value!!.isSync) {
                    throw CustomerSynchronizedException("Não é possível excluir um cliente já sincronizado!")
                }
                repository.deleteCustomerById(selectedCustomer.value!!.customerId)
                selectedCustomer.update { null }
                updateDialogMessage("Cliente deletado com sucesso")
                updateDialog(true)
                getCustomers(queryValue.value)
            } catch (e: Exception) {
                exception.update { e }
            }
        }
    }

    class CustomerViewModelFactory(private val repository: CustomerRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CustomerViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CustomerViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class ${modelClass.name}")
        }
    }
}