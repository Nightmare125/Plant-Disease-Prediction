package com.example.farmaid.pages

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream

@Composable
fun ImageCapture(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    var predictionResult by remember { mutableStateOf<String?>(null) }
    val mlPredictor = remember { MLPredictor(context) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { capturedBitmap: Bitmap? ->
        if (capturedBitmap != null) {
            bitmap.value = capturedBitmap
            // Save the image (replace with your actual saving logic)
            val file = File(context.filesDir, "captured_image.jpg")
            try {
                val outputStream = FileOutputStream(file)
                capturedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                outputStream.flush()
                outputStream.close()
                Log.d("ImageCapture", "Image saved to ${file.absolutePath}")
            } catch (e: Exception) {
                Log.e("ImageCapture", "Error saving image: ${e.message}")
            }
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            launcher.launch()
        } else {
            // Handle permission denied
            Toast.makeText(context, "Camera permission is required to take a photo", Toast.LENGTH_LONG).show()
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    launcher.launch()
                } else {
                    permissionLauncher.launch(Manifest.permission.CAMERA)
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Take Photo")
        }

        Spacer(modifier = Modifier.height(16.dp))

        bitmap.value?.let { capturedBitmap ->
            Image(
                bitmap = capturedBitmap.asImageBitmap(),
                contentDescription = "Captured Image",
                modifier = Modifier
                    .width(350.dp)
                    .height(350.dp)
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    bitmap.value?.let { capturedBitmap ->
                        predictionResult = mlPredictor.predict(capturedBitmap)
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Predict")
            }

            Spacer(modifier = Modifier.height(16.dp))

            predictionResult?.let { result ->
                Text(
                    text = "Prediction: $result",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}