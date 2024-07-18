package com.example.farmaid.pages

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.farmaid.AuthViewModel


@Composable
fun Classification(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel) {
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    val navController = rememberNavController()



    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()){ uri: Uri? ->
        if (uri != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source = ImageDecoder.createSource(context.contentResolver, uri)
                bitmap.value = ImageDecoder.decodeBitmap(source)
            } else {
                // For devices below Android P, use BitmapFactory
                try {
                    val inputStream = context.contentResolver.openInputStream(uri)
                    bitmap.value = BitmapFactory.decodeStream(inputStream)
                } catch (e: Exception) {// Handle exceptions (e.g., file not found, invalid format)
                    Log.e("ImageUpload", "Error loading image: ${e.message}")
                }
            }
        }
    }
    Column (
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        // Display the selected image if available
        bitmap.value?.let {
            Image(bitmap = it.asImageBitmap(), contentDescription = "Selected Image",modifier = Modifier
                .width(350.dp)
                .height(350.dp)
                .padding(8.dp),// Add padding around the image)
            )}
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = { launcher.launch("image/*") }) {
                Text("Upload a pic")
            }
            Button(onClick = {
                navController.navigate("home")
            }) { // Add back button
                Text("Home")
            }

        }
    }

}
