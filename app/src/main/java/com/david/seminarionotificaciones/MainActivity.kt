package com.david.seminarionotificaciones

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getString
import androidx.core.content.ContextCompat.getSystemService
import android.Manifest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.core.content.ContextCompat
import com.david.seminarionotificaciones.ui.theme.SeminarioNotificacionesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SeminarioNotificacionesTheme {
                SeminarioNotificaciones()
            }
        }
    }
}

@Composable
fun SeminarioNotificaciones() {
    val context = LocalContext.current
    val channelId = "my_channel_id" // Use a descriptive ID
    var notificationId by remember { mutableStateOf(0) }
    val title = "NOTIFICACIÓN, TRONCO"
    val text = "Hola picha!"
    val texto_largo=getString(context,R.string.lorem)

    LaunchedEffect(Unit) {
        createNotificationChannel(context)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ejercicio 01
        Button(
            onClick = {

                if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                    sendNotification(context, channelId, notificationId, title, text,1)
                    notificationId++
                } else {

                    ActivityCompat.requestPermissions(context as ComponentActivity, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 0)
                }
            }
        ) {
            Text(text = "Ejercicio 01")
        }
        //ejercicio 2 - 1
        Button(
            onClick = {

                if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                    sendNotification(context, channelId, notificationId, title, text,21)
                    notificationId++
                } else {

                    ActivityCompat.requestPermissions(context as ComponentActivity, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 0)
                }
            }
        ) {
            Text(text = "Ejercicio 02 - BigPictureStyle")
        }
        //ejercicio 2 - 2
        Button(
            onClick = {

                if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                    sendNotification(context, channelId, notificationId, title, texto_largo,22)
                    notificationId++
                } else {

                    ActivityCompat.requestPermissions(context as ComponentActivity, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 0)
                }
            }
        ) {
            Text(text = "Ejercicio 02 - BigTextStyle")
        }
        //ejercicio 3
        Button(
            onClick = {
                val intent = Intent(context, Ej03Activity::class.java)
                context.startActivity(intent)
            }
        ) {
            Text(text = "Ejercicio 03")
        }


    }
}
