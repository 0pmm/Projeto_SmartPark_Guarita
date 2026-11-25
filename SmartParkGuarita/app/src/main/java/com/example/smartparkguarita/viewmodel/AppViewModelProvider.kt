package com.example.smartparkguarita.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.smartparkguarita.SmartParkApplication

/** Fábrica que entrega o repositório da aplicação aos ViewModels. */
object AppViewModelProvider {
    val Fabrica = viewModelFactory {
        initializer { PorteiroViewModel(aplicacao().repository) }
        initializer { PainelViewModel(aplicacao().repository) }
        initializer { AdminViewModel(aplicacao().repository) }
        initializer { ServidorViewModel(aplicacao().repository) }
    }
}

private fun CreationExtras.aplicacao(): SmartParkApplication =
    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SmartParkApplication
