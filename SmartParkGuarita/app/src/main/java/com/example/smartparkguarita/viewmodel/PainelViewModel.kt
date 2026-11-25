package com.example.smartparkguarita.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartparkguarita.data.SmartParkRepository
import com.example.smartparkguarita.domain.MovimentoTurno
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class PainelViewModel(repositorio: SmartParkRepository) : ViewModel() {

    val ocupacaoAtual: StateFlow<Int> = repositorio.ocupacaoAtual()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0)

    val movimentoPorTurno: StateFlow<List<MovimentoTurno>> = repositorio.movimentoPorTurno()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
}
