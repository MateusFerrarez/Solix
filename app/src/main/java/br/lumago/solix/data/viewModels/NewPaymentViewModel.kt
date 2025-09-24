package br.lumago.solix.data.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.lumago.solix.data.entities.Payments
import br.lumago.solix.data.repositories.PaymentsRepository
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class NewPaymentViewModel(private val repository: PaymentsRepository) : ViewModel() {

    fun insertPayment(){
        val payment = Payments(
            paymentId = 0L,
            enterpriseId = 1L,
            customerId = 1L,
            indicatorId = null,
            montValue = 50.0,
            dueDate = LocalDateTime.now().toString(),
            contractDate = LocalDateTime.now().toString(),
            observation = null,
            createdAt = LocalDateTime.now().toString(),
            updatedAt = null,
            synchronizedAt = null
        )

        viewModelScope.launch {
            repository.insertPayment(payment)
        }
    }


    class NewPaymentViewModelFactory(private val repository: PaymentsRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NewPaymentViewModel(repository) as T
        }
    }
}