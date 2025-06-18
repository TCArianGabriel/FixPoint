package org.dsm.fixpoint

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.dsm.fixpoint.ui.theme.FixPointTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FixPointTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                }
            }
        }
    }
}

// Anotación para indicar que es una función Composable experimental de Material 3
@Composable
fun LoginScreen() {
    Box(
        modifier = Modifier.fillMaxSize() // Ocupa todo el tamaño disponible
    ) {
        // Imagen de fondo (asegúrate de tener background_tech en tu carpeta drawable)
        Image(
            painter = painterResource(id = R.drawable.background_tech),
            contentDescription = "Fondo tecnológico",
            modifier = Modifier.fillMaxSize(), // Ocupa todo el tamaño del Box
            contentScale = ContentScale.Crop // Recorta la imagen para llenar el espacio
        )

        // Columna para organizar los elementos verticalmente
        Column(
            modifier = Modifier
                .fillMaxSize() // Ocupa todo el tamaño del Box
                .padding(16.dp), // Añade un padding general
            horizontalAlignment = Alignment.CenterHorizontally, // Centra los elementos horizontalmente
            verticalArrangement = Arrangement.Top // Alinea los elementos en la parte superior
        ) {
            // Texto "Iniciar Sesion"
            Text(
                text = "Iniciar Sesion",
                color = Color.White, // Color del texto blanco
                fontSize = 18.sp, // Tamaño de la fuente
                modifier = Modifier
                    .align(Alignment.Start) // Alinea este texto a la izquierda
                    .padding(top = 16.dp) // Padding superior para separarlo del borde
            )

            // Espaciador para crear espacio entre el título y el logo
            Spacer(modifier = Modifier.height(60.dp))

            // Logo de la aplicación (asegúrate de tener mobile_gear_logo en tu carpeta drawable)
            Image(
                painter = painterResource(id = R.drawable.mobile_gear_logo),
                contentDescription = "Logo de la aplicación: Móvil con engranaje",
                modifier = Modifier.size(150.dp) // Tamaño del logo
            )

            // Espaciador para crear espacio entre el logo y los campos de texto
            Spacer(modifier = Modifier.height(50.dp))

            // Fila para el campo de entrada de Usuario
            Row(
                modifier = Modifier
                    .fillMaxWidth() // Ocupa todo el ancho
                    .padding(horizontal = 32.dp), // Padding horizontal
                verticalAlignment = Alignment.CenterVertically // Centra verticalmente los elementos de la fila
            ) {
                // Etiqueta "Usuario"
                Text(
                    text = "Usuario",
                    color = Color.White,
                    fontSize = 16.sp,
                    modifier = Modifier.width(90.dp) // Ancho fijo para alinear la etiqueta
                )
                // Campo de texto para el usuario
                OutlinedTextField(
                   // Valor actual del campo
                    onValueChange = { }, // Actualiza el estado cuando el texto cambia
                    singleLine = true, // Permite una sola línea de texto
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.White.copy(alpha = 0.8f), // Fondo semi-transparente
                        unfocusedBorderColor = Color.LightGray, // Color del borde cuando no está enfocado
                        focusedBorderColor = Color.Blue // Color del borde cuando está enfocado
                    ),
                    modifier = Modifier.weight(1f) // Ocupa el espacio restante en la fila
                )
            }

            // Espaciador entre los campos de usuario y contraseña
            Spacer(modifier = Modifier.height(20.dp))

            // Fila para el campo de entrada de Contraseña
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Etiqueta "Contraseña"
                Text(
                    text = "Contraseña",
                    color = Color.White,
                    fontSize = 16.sp,
                    modifier = Modifier.width(90.dp) // Ancho fijo para alinear la etiqueta
                )
                // Campo de texto para la contraseña
                OutlinedTextField(
                    onValueChange = { },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(), // Oculta el texto como contraseña
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), // Teclado tipo contraseña
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.White.copy(alpha = 0.8f), // Fondo semi-transparente
                        unfocusedBorderColor = Color.LightGray,
                        focusedBorderColor = Color.Blue
                    ),
                    modifier = Modifier.weight(1f)
                )
            }

            // Espaciador entre el campo de contraseña y el botón
            Spacer(modifier = Modifier.height(40.dp))

            // Botón de "Ingresar"
            Button(
                onClick = {
                    // Lógica para manejar el inicio de sesión cuando se hace clic en el botón
                    // Aquí podrías añadir tu lógica de autenticación (ej. llamar a una API)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0E0E0)), // Color gris claro para el botón
                modifier = Modifier
                    .width(200.dp) // Ancho fijo del botón
                    .height(50.dp) // Altura fija del botón
            ) {
                Text(
                    text = "Ingresar",
                    color = Color.Black, // Color del texto del botón
                    fontSize = 18.sp // Tamaño de la fuente del botón
                )
            }
        }
    }
}

// Función Composable de vista previa para ver el diseño en Android Studio
@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginUITheme {
        LoginScreen()
    }
}