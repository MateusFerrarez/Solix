package br.lumago.solix.ui.barcodeReader.components

import android.Manifest
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.vision.CameraSource
import java.io.IOException

@RequiresPermission(Manifest.permission.CAMERA)
@Composable
fun BarcodeReaderView(cameraSource: CameraSource) {
    val context = LocalContext.current

    val previewView = remember { SurfaceView(context) }

    AndroidView(
        factory = {
            previewView.apply {
                holder.addCallback(object : SurfaceHolder.Callback {
                    override fun surfaceCreated(holder: SurfaceHolder) {
                        try {
                            cameraSource.start(holder)
                        } catch (e: IOException) {
                            Toast.makeText(context, "Erro ao iniciar a câmera: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                        } catch (_: SecurityException) {
                            Toast.makeText(context, "Permissão de câmera negada. Por favor, conceda a permissão.", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

                    }

                    override fun surfaceDestroyed(holder: SurfaceHolder) {
                        cameraSource.stop()
                        Log.d("BarcodeReaderView", "CameraSource parada via SurfaceHolder.")
                    }
                })
            }
        },
        modifier = Modifier.fillMaxSize()
    )

    DisposableEffect(cameraSource) {
        onDispose {
            cameraSource.stop()
            Log.d("BarcodeReaderView", "CameraSource parada via DisposableEffect onDispose.")
        }
    }
}