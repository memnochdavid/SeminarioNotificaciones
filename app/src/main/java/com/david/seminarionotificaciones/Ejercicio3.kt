package com.david.seminarionotificaciones

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.david.seminarionotificaciones.ui.theme.Pink40
import com.david.seminarionotificaciones.ui.theme.Purple40
import com.david.seminarionotificaciones.ui.theme.SeminarioNotificacionesTheme

class Ej03Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            SeminarioNotificacionesTheme {
                FormularioCreaNotificacion()
            }
        }
    }
}

@Composable
fun FormularioCreaNotificacion(){
    var titulo by remember { mutableStateOf("") }
    var texto by remember { mutableStateOf("") }
    var num_botones by remember { mutableStateOf(1) }
    var lista_nombres by remember { mutableStateOf("") }

    var lista_iconos = listOf(
        R.drawable.dick,
        R.drawable.caca,
    )
    var icono by remember { mutableIntStateOf(lista_iconos[0]) }
    val context = LocalContext.current
    val channelId = "my_channel_id"

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            selectedImageUri = uri
        }
    )
    val shape = RoundedCornerShape(5.dp)
    val colores_boton = ButtonDefaults.buttonColors(
        containerColor = Pink40,
        contentColor = Color.White
    )
    val colores_textfield = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Color.White,
        unfocusedBorderColor = Color.White,
        focusedLabelColor = Color.Black,
        unfocusedLabelColor = Color.Gray,
        cursorColor = Color.Black,
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Purple40),
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = "Generador de Notificaciones",
            fontSize = 25.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
        ConstraintLayout(
            modifier = Modifier
                .padding(vertical = 50.dp, horizontal = 30.dp),
        ){
            val (titulo_form, texto_form, botones_form, icono_form, icono_texto, enviar,imagen, preview_imagen, preview_icono) = createRefs()
            TextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text("Título") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
                    .constrainAs(titulo_form) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    },
                colors = colores_textfield
            )
            TextField(
                value = texto,
                onValueChange = { texto = it },
                label = { Text("Texto") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
                    .constrainAs(texto_form) {
                        top.linkTo(titulo_form.bottom)
                        start.linkTo(parent.start)
                    },
                colors = colores_textfield
            )
            Text(
                text = "Icono",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .fillMaxHeight()
                    .constrainAs(icono_texto) {
                        top.linkTo(texto_form.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(icono_form.start)

                    },
            )
            SeleccionaIcono(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth(0.7f)
                    .constrainAs(icono_form) {
                        top.linkTo(texto_form.bottom)
                        start.linkTo(icono_texto.end)
                        end.linkTo(parent.end)
                    },
                icono = icono,
                lista_iconos = lista_iconos,
                onIconoChange = { icono = it }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(botones_form) {
                        top.linkTo(icono_form.bottom)
                        start.linkTo(parent.start)
                    },
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                TextField(
                    value = num_botones.toString(),
                    onValueChange = { num_botones = it.toIntOrNull() ?: 0 },
                    label = { Text("N. botones") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth(0.35f)
                        .padding(bottom = 10.dp),
                    colors = colores_textfield
                )
                TextField(
                    value = lista_nombres,
                    onValueChange = { lista_nombres = it },
                    label = { Text("nom., nom.") },
                    modifier = Modifier
                        .fillMaxWidth(0.65f)
                        .padding(bottom = 10.dp),
                    colors = colores_textfield
                )
            }
            Button(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .constrainAs(imagen) {
                        top.linkTo(botones_form.bottom)
                        end.linkTo(parent.end)
                    },
                onClick = { launcher.launch("image/*") },
                shape = shape,
                colors = colores_boton
            ) {
                Text(text = "Imagen")
            }
            Button(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .constrainAs(enviar) {
                        top.linkTo(botones_form.bottom)
                        start.linkTo(parent.start)
                    },
                onClick = {
                    val lista_nombres_botones = lista_nombres.split(",")
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                        sendNotificationE3(context, channelId, 666, titulo, texto,icono,num_botones,lista_nombres_botones,selectedImageUri)
                    } else {
                        ActivityCompat.requestPermissions(context as ComponentActivity, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 0)
                    }
                },
                shape = shape,
                colors = colores_boton
            ) {
                Text(text = "Enviar Notificación")
            }
            val imageRequest = ImageRequest.Builder(context)
                .data(selectedImageUri)
                .build()
            val painter = rememberAsyncImagePainter(imageRequest)
            Image(
                painter = painter,
                contentDescription = "",
                modifier = Modifier
                    .constrainAs(preview_imagen) {
                        top.linkTo(enviar.bottom)
                        end.linkTo(parent.end)
                    }
                    .size(150.dp)
            )
            Image(
                painter = painterResource(id = icono),
                contentDescription = "",
                modifier = Modifier
                    .constrainAs(preview_icono) {
                        top.linkTo(enviar.bottom)
                        start.linkTo(parent.start)
                    }
                    .size(150.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeleccionaIcono(
    modifier: Modifier = Modifier,
    icono: Int,
    lista_iconos: List<Int>,
    onIconoChange: (Int) -> Unit,
) {
    val context = LocalContext.current
    val resources = context.resources
    var selectedIcono by remember { mutableStateOf(icono) }
    var expanded by remember { mutableStateOf(false) }
    val colores_textfield = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Color.White,
        unfocusedBorderColor = Color.White,
        focusedLabelColor = Color.Black,
        unfocusedLabelColor = Color.Gray,
        cursorColor = Color.Black,
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White
    )
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        TextField(
            value = resources.getResourceEntryName(selectedIcono),
            onValueChange = {
                resources.getResourceEntryName(selectedIcono)
            },
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            modifier = Modifier
                .menuAnchor()
                .wrapContentWidth(),
            colors = colores_textfield
            )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            lista_iconos.forEach { icono ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = resources.getResourceEntryName(icono),
                            color = Color.Black
                        )
                    },
                    onClick = {
                        selectedIcono = icono
                        expanded = false
                        onIconoChange(icono)
                    },
                    modifier = Modifier
                        .padding(vertical = 0.dp),
                )
            }
        }
    }
}