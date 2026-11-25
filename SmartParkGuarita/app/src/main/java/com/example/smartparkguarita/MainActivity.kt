package com.example.smartparkguarita

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.smartparkguarita.ui.SmartParkNavHost
import com.example.smartparkguarita.ui.theme.SmartParkGuaritaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartParkGuaritaTheme {
                SmartParkNavHost()
            }
        }
    }
}
