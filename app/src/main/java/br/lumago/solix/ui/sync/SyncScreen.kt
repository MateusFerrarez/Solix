package br.lumago.solix.ui.sync

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import br.lumago.solix.data.viewModels.sync.SyncViewModel
import br.lumago.solix.ui.sync.components.SyncView

class SyncScreen : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel = ViewModelProvider(
            this,
            SyncViewModel.SyncViewModelFactory()
        )[SyncViewModel::class]

        super.onCreate(savedInstanceState)
        setContent {
            SyncView(viewModel)
        }
    }
}