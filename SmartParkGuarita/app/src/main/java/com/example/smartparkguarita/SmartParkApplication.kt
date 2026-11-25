package com.example.smartparkguarita

import android.app.Application
import com.example.smartparkguarita.data.AppDatabase
import com.example.smartparkguarita.data.SmartParkRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/** Cria o banco e o repositório uma única vez, para todo o aplicativo. */
class SmartParkApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.criar(this) }
    val repository: SmartParkRepository by lazy { SmartParkRepository(database) }

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            repository.popularDadosDeExemplo()
        }
    }
}
