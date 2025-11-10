package br.lumago.solix.data.viewModels.payment

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
import br.lumago.solix.data.entities.relations.PaymentCard
import br.lumago.solix.data.repositories.PaymentsRepository
import br.lumago.solix.exceptions.payment.DeletePaymentException
import br.lumago.solix.exceptions.payment.GetPaymentException
import br.lumago.solix.exceptions.payment.PaymentSynchronizedException
import br.lumago.solix.ui.paymentHandler.PaymentHandlerScreen
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class PaymentsViewModel(
    private val repository: PaymentsRepository
) : ViewModel() {
    var selectedPayment = MutableStateFlow<PaymentCard?>(null)
        private set
    var showChooserDialog = MutableStateFlow(false)
        private set
    private val _pagingFlow = MutableStateFlow<Flow<PagingData<PaymentCard>>?>(null)
    val pagingFlow: StateFlow<Flow<PagingData<PaymentCard>>?> = _pagingFlow

    var showDialog = MutableStateFlow(false)
        private set
    var dialogMessage = MutableStateFlow("")
        private set

    //
    var exception = MutableStateFlow<Exception?>(null)
        private set

    // Search
    var searchValue = MutableStateFlow("")
        private set
    var queryValue = MutableStateFlow("%%")
        private set
    var lastQuery: String? = null

    init {
        viewModelScope.launch {
            queryValue
                .debounce(350)
                .collectLatest { search ->
                    if (search != lastQuery.toString()) {
                        getPayments(search)
                        lastQuery = search
                    }
                }
        }
    }

    // Open
    fun openNewPaymentScreen(
        launcher: ManagedActivityResultLauncher<Intent, ActivityResult>,
        activity: Activity
    ) {
        val paymentHandlerScreen = Intent(activity, PaymentHandlerScreen::class.java)
        launcher.launch(paymentHandlerScreen)
    }

    fun openEditPaymentScreen(
        launcher: ManagedActivityResultLauncher<Intent, ActivityResult>,
        activity: Activity,
        paymentId: Long
    ) {
        val editPaymentScreen = Intent(activity, PaymentHandlerScreen::class.java)
        editPaymentScreen.putExtra("paymentId", paymentId)
        launcher.launch(editPaymentScreen)
    }

    // Get
    fun getPayments(query: String) {
        viewModelScope.launch {
            try {
                _pagingFlow.value = repository
                    .getPayments(query)
                    .cachedIn(viewModelScope)
            } catch (e: Exception) {
                val customException = GetPaymentException(e.message!!)
                updateException(customException)
            }
        }
    }

    // Update
    fun updateException(newException: Exception?) {
        exception.update { newException }
    }

    fun updateBusca(valor: String) {
        searchValue.update { valor }
        val valorFormatado = when {
            searchValue.value.trim().isEmpty() -> "%%"
            searchValue.value.isDigitsOnly() -> searchValue.value.trim()
            else -> "%${searchValue.value.uppercase()}%"
        }
        queryValue.update { valorFormatado }
    }

    fun updateDialog(value: Boolean) {
        showDialog.update { value }
    }

    fun updateChooserDialog(value: Boolean) {
        showChooserDialog.update { value }
    }

    fun updatePaymentId(paymentId: PaymentCard?) {
        selectedPayment.value = paymentId
        updateChooserDialog(true)
    }

    fun updateDialogMessage(text: String) {
        dialogMessage.update { text }
    }

    // Delete
    fun deletePaymentById() {
        viewModelScope.launch {
            try {
                updateChooserDialog(false)

                if (selectedPayment.value!!.isSychronized) {
                    throw PaymentSynchronizedException("Não é possível excluir uma mensalidade já sincronizada!")
                }

                repository.deletePaymentById(selectedPayment.value!!.paymentId)
                updateDialogMessage("Mensalidade deletada com sucesso")
                updateDialog(true)
                getPayments(queryValue.value)
            } catch (e: Exception) {
                val customException = DeletePaymentException(e.message!!)
                updateException(customException)
            }
        }
    }

    class PaymentViewModelFactory(
        private val repository: PaymentsRepository,
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PaymentsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PaymentsViewModel(
                    repository
                ) as T
            }
            throw IllegalArgumentException("Unkow model view ${modelClass.name}")
        }
    }
}