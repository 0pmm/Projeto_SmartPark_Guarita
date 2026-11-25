package com.example.smartparkguarita.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartparkguarita.data.SmartParkRepository
import com.example.smartparkguarita.data.entity.Servidor
import com.example.smartparkguarita.data.entity.TrocaTemporaria
import com.example.smartparkguarita.domain.Duracao
import com.example.smartparkguarita.domain.ResultadoOperacao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/** US09 e US10: o servidor pede uma troca temporária e acompanha os pedidos. */
@OptIn(ExperimentalCoroutinesApi::class)
class ServidorViewModel(private val repositorio: SmartParkRepository) : ViewModel() {

    val servidores: StateFlow<List<Servidor>> = repositorio.servidores()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    private val servidorAtual = MutableStateFlow<Long?>(null)

    val solicitacoes: StateFlow<List<TrocaTemporaria>> = servidorAtual
        .flatMapLatest { id ->
            if (id == null) flowOf(emptyList()) else repositorio.solicitacoesDoServidor(id)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    private val _aviso = MutableStateFlow<Aviso?>(null)
    val aviso: StateFlow<Aviso?> = _aviso.asStateFlow()

    fun selecionar(servidorId: Long) {
        servidorAtual.value = servidorId
    }

    fun limparAviso() {
        _aviso.value = null
    }

    fun solicitar(
        servidorId: Long, placa: String, modelo: String, cor: String, motivo: String,
        duracao: Duracao, aoConcluir: () -> Unit
    ) {
        viewModelScope.launch {
            val (inicio, fim) = duracao.intervalo(System.currentTimeMillis())
            val resultado = repositorio.solicitarTroca(servidorId, placa, modelo, cor, motivo, inicio, fim)
            _aviso.value = resultado.paraAviso()
            if (resultado is ResultadoOperacao.Sucesso) aoConcluir()
        }
    }
}
