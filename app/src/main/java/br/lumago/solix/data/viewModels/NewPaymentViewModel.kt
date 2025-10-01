package br.lumago.solix.data.viewModels

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
import br.lumago.solix.ui.utils.FormatDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime

class NewPaymentViewModel(private val repository: PaymentsRepository) : ViewModel() {
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

    fun mock() {
        customerSelected.value = CustomerSelected("11 - MATEUS TESTE", 1L)
        indicatorSelected.value = CustomerSelected("12 - MATEUS TESTE 2", 2L)
    }

    fun insertPayment() {
        val payment = Payments(
            paymentId = 0L,
            enterpriseId = 1L,
            customerId = customerSelected.value!!.customerID,
            indicatorId = null,
            montValue = paymentValue.text.toDouble(),
            dueDate = dueDate.value.toString(),
            contractDate = contractDate.value.toString(),
            observation = observationValue.text,
            createdAt = LocalDateTime.now().toString(),
            updatedAt = null,
            synchronizedAt = null
        )

        viewModelScope.launch {
            repository.insertPayment(payment)
        }
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
    fun updatePaymentValue(tempNewPaymentValue: TextFieldValue) {
        paymentValue = tempNewPaymentValue
    }

    fun updateObservation(tempNewObservation: TextFieldValue) {
        observationValue = tempNewObservation
    }

    fun updateDueDate(tempDate: LocalDate){
        dueDate.value = tempDate
    }

    fun updateContractDate(tempDate: LocalDate){
        contractDate.value = tempDate
    }

    class NewPaymentViewModelFactory(private val repository: PaymentsRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NewPaymentViewModel(repository) as T
        }
    }
}