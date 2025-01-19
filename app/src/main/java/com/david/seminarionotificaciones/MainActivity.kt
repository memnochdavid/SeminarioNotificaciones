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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.david.seminarionotificaciones.ui.theme.*
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
    val modifier = Modifier
        .fillMaxWidth(.75f)
        .padding(vertical = 10.dp)
        .clip(shape = MaterialTheme.shapes.medium)
    val shape = RoundedCornerShape(5.dp)
    val colores_boton = ButtonDefaults.buttonColors(
        containerColor = Pink40,
        contentColor = Color.White
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Purple40)
            .padding(horizontal = 30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Seminario de Notificaciones",
            fontSize = 25.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
        // ejercicio 01
        Button(
            modifier = modifier,
            shape = shape,
            colors = colores_boton,
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
            modifier = modifier,
            shape = shape,
            colors = colores_boton,
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
            modifier = modifier,
            shape = shape,
            colors = colores_boton,
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
            modifier = modifier,
            shape = shape,
            colors = colores_boton,
            onClick = {
                val intent = Intent(context, Ej03Activity::class.java)
                context.startActivity(intent)
            }
        ) {
            Text(text = "Ejercicio 03")
        }
    }
}
