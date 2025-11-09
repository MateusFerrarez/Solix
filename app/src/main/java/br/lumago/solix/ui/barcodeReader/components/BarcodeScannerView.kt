package br.lumago.solix.ui.barcodeReader.components

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import androidx.core.util.isNotEmpty

@SuppressLint("MissingPermission")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun BarcodeScannerView() {
    val activity = LocalActivity.current!!
    val context = LocalContext.current

    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    LaunchedEffect(Unit) {
        if (!cameraPermissionState.status.isGranted && !cameraPermissionState.status.shouldShowRationale) {
            cameraPermissionState.launchPermissionRequest()
        }
    }

    if (cameraPermissionState.status.isGranted) {
        val barcodeDetector = remember {
            BarcodeDetector.Builder(context)
                .setBarcodeFormats(Barcode.EAN_13)
                .build()
        }

        DisposableEffect(barcodeDetector) {
            if (!barcodeDetector.isOperational) {
                Log.w("BarcodeScannerScreen", "Dependências do detector (ex: Google Play Services) ainda não estão disponíveis.")
                Toast.makeText(context, "Dependências do detector não disponíveis.", Toast.LENGTH_LONG).show()
            } else {
                barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
                    override fun release() {
                        Log.d("BarcodeScannerScreen", "Barcode detector liberado.")
                    }

                    override fun receiveDetections(detections: Detector.Detections<Barcode>?) {
                        val barcodes = detections?.detectedItems
                        if (barcodes != null && barcodes.isNotEmpty()) {
                            val barcode = barcodes.valueAt(0)
                            barcodeDetector.release()
                            val result = Intent()
                            result.putExtra("product", barcode.rawValue)
                            activity.setResult(1, result)
                            activity.finish()
                        }
                    }
                })
            }

            // Garante que o BarcodeDetector seja liberado quando o Composable sair da composição
            onDispose {
                barcodeDetector.release()
                Log.d("BarcodeScannerScreen", "Barcode detector liberado em onDispose.")
            }
        }

        // Inicializa CameraSource usando remember
        val cameraSource = remember {
            CameraSource.Builder(context, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080) // Alta resolução para melhor detecção
                .setAutoFocusEnabled(true)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .build()
        }

        // Layout principal: Câmera na parte superior, texto detectado na parte inferior
        Column(Modifier.fillMaxSize()) {
            // Pré-visualização da câmera preenche a maior parte da tela
            Box(Modifier.weight(1f)) {
                BarcodeReaderView(cameraSource)
            }

            Text(
                text = "Aponte a câmera para um código de barras...",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall
            )
        }

    } else {
        // Se a permissão não foi concedida, exibe uma mensagem e um botão para solicitá-la
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val textToShow = if (cameraPermissionState.status.shouldShowRationale) {
                "A permissão da câmera é essencial para a leitura de códigos de barras. Por favor, conceda a permissão nas configurações."
            } else {
                "Permissão de câmera necessária para usar o leitor de código de barras."
            }
            Text(textToShow, textAlign = TextAlign.Center, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                Text("Solicitar Permissão")
            }
        }
    }
}