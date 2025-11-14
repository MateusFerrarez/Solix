package br.lumago.solix.data.viewModels.home

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.lumago.solix.ui.counter.CounterScreen
import br.lumago.solix.ui.customers.CustomersScreen
import br.lumago.solix.ui.map.MapScreen
import br.lumago.solix.ui.payments.PaymentsScreen
import br.lumago.solix.ui.sync.SyncScreen

class HomeViewModel() : ViewModel() {

    // Open
    fun openCustomersScreen(activity: Activity){
        val activityCustomersScreen = Intent(activity, CustomersScreen::class.java)
        activity.startActivity(activityCustomersScreen)
    }
    fun openPaymentsScreen(activity: Activity) {
        val activityPaymentsScreen = Intent(activity, PaymentsScreen::class.java)
        activity.startActivity(activityPaymentsScreen)
    }

    fun openCounterScreen(activity: Activity) {
        val activityCounterScreen = Intent(activity, CounterScreen::class.java)
        activity.startActivity(activityCounterScreen)
    }

    fun openMapScreen(activity: Activity){
        val activityMapScreen = Intent(activity, MapScreen::class.java)
        activity.startActivity(activityMapScreen)
    }

    fun openSyncScreen(activity: Activity) {
        val activitySyncScreen = Intent(activity, SyncScreen::class.java)
        activity.startActivity(activitySyncScreen)
    }

    class HomeViewModelFactory() : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel() as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }

    }
}