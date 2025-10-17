package br.lumago.solix.ui.barcodeReader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.lumago.solix.ui.barcodeReader.components.BarcodeScannerView

class BarcodeReaderScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent() {
            BarcodeScannerView()
        }
    }


}