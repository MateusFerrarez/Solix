package br.lumago.solix.data.viewModels

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.lumago.solix.data.entities.Payments
import br.lumago.solix.data.entities.relations.CustomerSelected
import br.lumago.solix.data.repositories.PaymentsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalDateTime

class NewPaymentViewModel(private val repository: PaymentsRepository) : ViewModel() {
    private var payment: Payments? = null
    // Buttons
    var customerSelected = MutableStateFlow<CustomerSelected?>(null)
        private set

    var indicatorSelected = MutableStateFlow<CustomerSelected?>(null)
        private set

    var dueDate = MutableStateFlow<LocalDate>(LocalDate.now())
        private set

    var contractDate = MutableStateFlow<LocalDate>(LocalDate.now())
        private set
    // TextFields
    var paymentValue by mutableStateOf(TextFieldValue("R$ 0,00"))
        private set

    var observationValue by mutableStateOf(TextFieldValue(""))
        private set
    // Dialog
    var showDialog = MutableStateFlow(false)
        private set

    fun mock() {
        customerSelected.value = CustomerSelected("11 - MATEUS TESTE DE LARGURA DO CARD PARA VERIFICAR O TAMANHO DO OVERFLOX", 1L)
        indicatorSelected.value = CustomerSelected("12 - MATEUS TESTE 2", 2L)
    }

    // Insert
    fun insertPayment(activity: Activity) {
        val formatedPaymentValue = paymentValue.text
            .replace("R$", "")
            .replace(".", "")
            .replace(",", ".")
            .trim()
            .toDouble()

        payment = Payments(
            paymentId = 0L,
            enterpriseId = 1L,
            customerId = customerSelected.value!!.customerId,
            indicatorId = indicatorSelected.value?.customerId,
            montValue = formatedPaymentValue,
            dueDate = dueDate.value.toString(),
            contractDate = contractDate.value.toString(),
            observation = observationValue.text,
            createdAt = LocalDateTime.now().toString(),
            updatedAt = null,
            synchronizedAt = null
        )

        viewModelScope.launch {
            repository.insertPayment(payment!!)
            activity.setResult(1)
            activity.finish()
        }
    }

    // Get
    suspend fun getPaymentById(paymentId : Long){
        payment = withContext(Dispatchers.IO) {
            repository.getPaymentById(paymentId)
        }

        customerSelected.value = withContext(Dispatchers.IO) {
            repository.getCustomerSelectedByPaymentId(paymentId)
        }

        paymentValue = TextFieldValue(payment!!.montValue.toString())

    }

    // Show
    fun showDatePicker(
        context: Context,
        date: LocalDate,
        onDateChanged: (LocalDate) -> Unit
    ) {
        val dateListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            val tempDate = LocalDate.of(year, month, dayOfMonth)
            onDateChanged(tempDate)
        }

        val dialog = DatePickerDialog(
            context,
            dateListener,
            date.year,
            date.monthValue - 1,
            date.dayOfMonth
        )

        dialog.show()
    }

    // Update
    fun updateDialog(value: Boolean) {
        showDialog.update { value }
    }

    fun updatePaymentValue(tempNewPaymentValue: TextFieldValue) {
        paymentValue = tempNewPaymentValue
    }

    fun updateObservation(tempNewObservation: TextFieldValue) {
        observationValue = tempNewObservation
    }

    fun updateDueDate(tempDate: LocalDate) {
        dueDate.value = tempDate
    }

    fun updateContractDate(tempDate: LocalDate) {
        contractDate.value = tempDate
    }

    class NewPaymentViewModelFactory(private val repository: PaymentsRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NewPaymentViewModel(repository) as T
        }
    }
}