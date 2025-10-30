package br.lumago.solix.data.viewModels

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import br.lumago.solix.data.entities.relations.PaymentCard
import br.lumago.solix.exceptions.handler.PaymentHandler
import br.lumago.solix.data.repositories.PaymentsRepository
import br.lumago.solix.exceptions.payment.PaymentDeleteException
import br.lumago.solix.exceptions.payment.PaymentGetException
import br.lumago.solix.exceptions.payment.PaymentSynchronizedException
import br.lumago.solix.ui.newPayment.NewPaymentScreen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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

    // Open
    fun openNewPaymentScreen(
        launcher: ManagedActivityResultLauncher<Intent, ActivityResult>,
        activity: Activity
    ) {
        val newPaymentScreen = Intent(activity, NewPaymentScreen::class.java)
        launcher.launch(newPaymentScreen)
    }

    fun openEditPaymentScreen(
        launcher: ManagedActivityResultLauncher<Intent, ActivityResult>,
        activity: Activity,
        paymentId: Long
    ) {
        val editPaymentScreen = Intent(activity, NewPaymentScreen::class.java)
        editPaymentScreen.putExtra("paymentId", paymentId)
        launcher.launch(editPaymentScreen)
    }

    // Get
    fun getPayments(context: Context) {
        viewModelScope.launch {
            try {
                _pagingFlow.value = repository
                    .getPayments()
                    .cachedIn(viewModelScope)
            } catch (e: Exception) {
                val customException = PaymentGetException(e.message!!)
                PaymentHandler(customException).saveLog(context)
                updateException(customException)
            }
        }
    }

    // Update
    fun updateException(newException: Exception?) {
        exception.update { newException }
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
    fun deletePaymentById(context: Context) {
        viewModelScope.launch {
            try {
                updateChooserDialog(false)

                if (selectedPayment.value!!.isSychronized) {
                    throw PaymentSynchronizedException("Não é possível excluir uma mensalidade já sincronizada!")
                }

                repository.deletePaymentById(selectedPayment.value!!.paymentId)
                updateDialogMessage("Mensalidade deletada com sucesso")
                updateDialog(true)
                getPayments(context)
            } catch (e: Exception) {
                val customException = PaymentDeleteException(e.message!!)
                PaymentHandler(customException).saveLog(context)
                updateException(customException)
            }
        }
    }

    class PaymentViewModelFactory(
        private val repository: PaymentsRepository,
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PaymentsViewModel(
                repository
            ) as T
        }
    }
}