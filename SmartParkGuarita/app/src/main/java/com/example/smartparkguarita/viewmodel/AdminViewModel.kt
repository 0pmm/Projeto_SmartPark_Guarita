package com.example.smartparkguarita.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartparkguarita.data.SmartParkRepository
import com.example.smartparkguarita.data.entity.Servidor
import com.example.smartparkguarita.data.entity.TrocaTemporaria
import com.example.smartparkguarita.data.entity.Veiculo
import com.example.smartparkguarita.domain.Duracao
import com.example.smartparkguarita.domain.ResultadoOperacao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/** US05 a US08: cadastros, trocas temporárias e avaliação de solicitações. */
class AdminViewModel(private val repositorio: SmartParkRepository) : ViewModel() {

    val servidores: StateFlow<List<Servidor>> = repositorio.servidores()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val veiculos: StateFlow<List<Veiculo>> = repositorio.veiculos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val trocas: StateFlow<List<TrocaTemporaria>> = repositorio.trocas()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val pendentes: StateFlow<List<TrocaTemporaria>> = repositorio.solicitacoesPendentes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    private val _aviso = MutableStateFlow<Aviso?>(null)
    val aviso: StateFlow<Aviso?> = _aviso.asStateFlow()

    fun limparAviso() {
        _aviso.value = null
    }

    private fun executar(aoConcluir: () -> Unit = {}, acao: suspend () -> ResultadoOperacao) {
        viewModelScope.launch {
            val resultado = acao()
            _aviso.value = resultado.paraAviso()
            if (resultado is ResultadoOperacao.Sucesso) aoConcluir()
        }
    }

    fun salvarServidor(id: Long, nome: String, matricula: String, aoConcluir: () -> Unit) =
        executar(aoConcluir) {
            repositorio.salvarServidor(Servidor(id = id, nome = nome.trim(), matricula = matricula.trim()))
        }

    fun excluirServidor(servidor: Servidor) = executar {
        repositorio.excluirServidor(servidor)
        ResultadoOperacao.Sucesso("Servidor excluído")
    }

    fun salvarVeiculo(
        id: Long, placa: String, modelo: String, cor: String, servidorId: Long?, aoConcluir: () -> Unit
    ) = executar(aoConcluir) {
        if (servidorId == null) {
            ResultadoOperacao.Erro("Escolha o servidor dono do veículo")
        } else {
            repositorio.salvarVeiculo(
                Veiculo(id = id, placa = placa, modelo = modelo.trim(), cor = cor.trim(), servidorId = servidorId)
            )
        }
    }

    fun excluirVeiculo(veiculo: Veiculo) = executar {
        repositorio.excluirVeiculo(veiculo)
        ResultadoOperacao.Sucesso("Veículo excluído")
    }

    fun cadastrarTroca(
        servidorId: Long?, placa: String, modelo: String, cor: String, motivo: String,
        duracao: Duracao, aoConcluir: () -> Unit
    ) = executar(aoConcluir) {
        if (servidorId == null) {
            ResultadoOperacao.Erro("Escolha o servidor")
        } else {
            val (inicio, fim) = duracao.intervalo(System.currentTimeMillis())
            repositorio.cadastrarTroca(servidorId, placa, modelo, cor, motivo, inicio, fim)
        }
    }

    fun avaliar(id: Long, aprovar: Boolean) = executar {
        repositorio.avaliarSolicitacao(id, aprovar)
    }
}
