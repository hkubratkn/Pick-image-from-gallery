package com.example.exampleeee

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.exampleeee.ui.theme.ExampleeeeTheme
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.example.exampleeee.ui.theme.Purple500

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExampleeeeTheme {
                PickPhoto()
            }
        }
    }
}

@Composable
fun PickPhoto(){
    val context = LocalContext.current
    var imageUrl by remember { mutableStateOf<Uri?>(null)}
    var bitmap by remember { mutableStateOf<Bitmap?>(null)}

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()){ uri: Uri? ->
        imageUrl = uri
    }

    Column {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Purple500),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "pick galerry image")
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            imageUrl?.let {
                if (Build.VERSION.SDK_INT < 28){
                    bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                }else {
                    val source = ImageDecoder.createSource(context.contentResolver, it)
                    bitmap = ImageDecoder.decodeBitmap(source)
                }
                bitmap.let { bitmap ->
                    Image(
                        bitmap = bitmap!!.asImageBitmap(),
                        contentDescription = "nullll",
                        modifier = Modifier.size(400.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.padding(20.dp))
            Button(
                onClick = {
                    launcher.launch("image/*")
                }
            ) {
                Text(text = "click meee")
            }
        }
    }
}
