package com.example.smartparkguarita.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val EsquemaClaro = lightColorScheme(
    primary = Verde700,
    onPrimary = Color.White,
    primaryContainer = Verde100,
    onPrimaryContainer = Verde900,
    secondary = Cinza600,
    onSecondary = Color.White,
    secondaryContainer = Cinza200,
    onSecondaryContainer = Azul900,
    background = Cinza100,
    onBackground = Azul900,
    surface = Cinza100,
    onSurface = Azul900,
    surfaceVariant = Cinza200,
    onSurfaceVariant = Cinza600,
    surfaceContainerHighest = Color.White,
    error = Color(0xFFB91C1C),
    errorContainer = ErroFundo,
    onErrorContainer = ErroTexto
)

private val EsquemaEscuro = darkColorScheme(
    primary = Verde300,
    onPrimary = Verde900,
    primaryContainer = Verde800,
    onPrimaryContainer = Verde100,
    secondary = Color(0xFFCBD5E1),
    onSecondary = Azul900,
    background = Azul900,
    onBackground = Cinza200,
    surface = Azul900,
    onSurface = Cinza200,
    surfaceVariant = Azul800,
    onSurfaceVariant = Color(0xFFCBD5E1),
    surfaceContainerHighest = Azul800,
    error = Color(0xFFFCA5A5),
    errorContainer = Color(0xFF7F1D1D),
    onErrorContainer = Color(0xFFFBE0E0)
)

/** Tema do aplicativo. Usa uma paleta própria, sem as cores dinâmicas do Android 12. */
@Composable
fun SmartParkGuaritaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) EsquemaEscuro else EsquemaClaro,
        typography = Typography,
        content = content
    )
}
