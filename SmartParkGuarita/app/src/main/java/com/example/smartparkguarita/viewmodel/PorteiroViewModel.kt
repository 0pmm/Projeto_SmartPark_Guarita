package com.example.smartparkguarita.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartparkguarita.data.SmartParkRepository
import com.example.smartparkguarita.data.entity.RegistroAcesso
import com.example.smartparkguarita.domain.Placa
import com.example.smartparkguarita.domain.ResultadoConsulta
import com.example.smartparkguarita.domain.ResultadoOperacao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PorteiroUiState(
    val placa: String = "",
    val resultado: ResultadoConsulta? = null,
    val mensagem: String? = null,
    val mensagemEhErro: Boolean = false
) {
    /** A entrada só pode ser registrada depois de uma consulta que autorizou o veículo. */
    val podeRegistrarEntrada: Boolean
        get() = resultado is ResultadoConsulta.Autorizado || resultado is ResultadoConsulta.AutorizadoTroca

    val podeConsultar: Boolean
        get() = placa.isNotBlank()
}

class PorteiroViewModel(
    private val repositorio: SmartParkRepository,
    private val nomePorteiro: String = "Porteiro"
) : ViewModel() {

    private val _estado = MutableStateFlow(PorteiroUiState())
    val estado: StateFlow<PorteiroUiState> = _estado.asStateFlow()

    val presentes: StateFlow<List<RegistroAcesso>> = repositorio.presentes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun aoMudarPlaca(texto: String) {
        _estado.update {
            it.copy(placa = Placa.normalizar(texto).take(7), resultado = null, mensagem = null)
        }
    }

    fun consultar() {
        viewModelScope.launch {
            val resultado = repositorio.consultarPlaca(_estado.value.placa)
            _estado.update { it.copy(resultado = resultado, mensagem = null) }
        }
    }

    fun registrarEntrada() {
        viewModelScope.launch {
            aplicar(repositorio.registrarEntrada(_estado.value.placa, nomePorteiro))
        }
    }

    fun registrarSaida() {
        viewModelScope.launch {
            aplicar(repositorio.registrarSaida(_estado.value.placa))
        }
    }

    private fun aplicar(resultado: ResultadoOperacao) {
        when (resultado) {
            is ResultadoOperacao.Sucesso -> _estado.value = PorteiroUiState(
                mensagem = resultado.mensagem, mensagemEhErro = false
            )
            is ResultadoOperacao.Erro -> _estado.update {
                it.copy(mensagem = resultado.mensagem, mensagemEhErro = true)
            }
        }
    }
}
