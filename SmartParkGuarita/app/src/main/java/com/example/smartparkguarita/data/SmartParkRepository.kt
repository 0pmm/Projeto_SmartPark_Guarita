package com.example.smartparkguarita.data

import android.database.sqlite.SQLiteConstraintException
import com.example.smartparkguarita.data.entity.RegistroAcesso
import com.example.smartparkguarita.data.entity.Servidor
import com.example.smartparkguarita.data.entity.TrocaTemporaria
import com.example.smartparkguarita.data.entity.Veiculo
import com.example.smartparkguarita.domain.MovimentoTurno
import com.example.smartparkguarita.domain.Placa
import com.example.smartparkguarita.domain.ResultadoConsulta
import com.example.smartparkguarita.domain.ResultadoOperacao
import com.example.smartparkguarita.domain.StatusSolicitacao
import com.example.smartparkguarita.domain.Turno
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SmartParkRepository(db: AppDatabase) {
    private val servidorDao = db.servidorDao()
    private val veiculoDao = db.veiculoDao()
    private val trocaDao = db.trocaTemporariaDao()
    private val registroDao = db.registroAcessoDao()

    fun servidores(): Flow<List<Servidor>> = servidorDao.todos()

    fun veiculos(): Flow<List<Veiculo>> = veiculoDao.todos()

    fun trocas(): Flow<List<TrocaTemporaria>> = trocaDao.todas()

    fun solicitacoesPendentes(): Flow<List<TrocaTemporaria>> = trocaDao.pendentes()

    fun solicitacoesDoServidor(servidorId: Long): Flow<List<TrocaTemporaria>> = trocaDao.porServidor(servidorId)

    fun presentes(): Flow<List<RegistroAcesso>> = registroDao.presentes()

    fun ocupacaoAtual(): Flow<Int> = registroDao.presentes().map { it.size }

    fun movimentoPorTurno(): Flow<List<MovimentoTurno>> = registroDao.todos().map { registros ->
        Turno.entries.map { turno ->
            MovimentoTurno(
                turno = turno,
                entradas = registros.count { Turno.deInstante(it.entrada) == turno },
                saidas = registros.count { it.saida != null && Turno.deInstante(it.saida) == turno }
            )
        }
    }

    /**
     * Regra de autorização: vale a troca temporária aprovada e dentro da validade;
     * senão, vale o veículo cadastrado; senão, a placa não é autorizada.
     */
    suspend fun consultarPlaca(textoPlaca: String, agora: Long = System.currentTimeMillis()): ResultadoConsulta {
        if (!Placa.valida(textoPlaca)) return ResultadoConsulta.PlacaInvalida
        val placa = Placa.normalizar(textoPlaca)

        val troca = trocaDao.validaPara(placa, agora)
        if (troca != null) {
            val nome = servidorDao.buscar(troca.servidorId)?.nome ?: "Servidor"
            return ResultadoConsulta.AutorizadoTroca(placa, nome, troca.fim)
        }

        val veiculo = veiculoDao.buscarPorPlaca(placa)
        if (veiculo != null) {
            val nome = servidorDao.buscar(veiculo.servidorId)?.nome ?: "Servidor"
            return ResultadoConsulta.Autorizado(placa, nome, veiculo.modelo)
        }

        return ResultadoConsulta.NaoAutorizado(placa)
    }

    suspend fun registrarEntrada(
        textoPlaca: String,
        porteiro: String,
        agora: Long = System.currentTimeMillis()
    ): ResultadoOperacao {
        val placa = when (val consulta = consultarPlaca(textoPlaca, agora)) {
            is ResultadoConsulta.Autorizado -> consulta.placa
            is ResultadoConsulta.AutorizadoTroca -> consulta.placa
            is ResultadoConsulta.NaoAutorizado -> return ResultadoOperacao.Erro("Veículo não autorizado: entrada não registrada")
            ResultadoConsulta.PlacaInvalida -> return ResultadoOperacao.Erro("Placa inválida")
        }
        if (registroDao.buscarAberto(placa) != null) {
            return ResultadoOperacao.Erro("O veículo $placa já está dentro do câmpus")
        }
        registroDao.inserir(RegistroAcesso(placa = placa, entrada = agora, porteiro = porteiro))
        return ResultadoOperacao.Sucesso("Entrada registrada: $placa")
    }

    suspend fun registrarSaida(textoPlaca: String, agora: Long = System.currentTimeMillis()): ResultadoOperacao {
        if (!Placa.valida(textoPlaca)) return ResultadoOperacao.Erro("Placa inválida")
        val placa = Placa.normalizar(textoPlaca)
        val aberto = registroDao.buscarAberto(placa)
            ?: return ResultadoOperacao.Erro("Não há entrada registrada para $placa")
        registroDao.atualizar(aberto.copy(saida = agora))
        return ResultadoOperacao.Sucesso("Saída registrada: $placa")
    }

    suspend fun salvarServidor(servidor: Servidor): ResultadoOperacao {
        if (servidor.nome.isBlank()) return ResultadoOperacao.Erro("Informe o nome do servidor")
        if (servidor.matricula.isBlank()) return ResultadoOperacao.Erro("Informe a matrícula")
        if (servidor.id == 0L) servidorDao.inserir(servidor) else servidorDao.atualizar(servidor)
        return ResultadoOperacao.Sucesso("Servidor salvo")
    }

    suspend fun excluirServidor(servidor: Servidor) = servidorDao.excluir(servidor)

    suspend fun salvarVeiculo(veiculo: Veiculo): ResultadoOperacao {
        if (!Placa.valida(veiculo.placa)) return ResultadoOperacao.Erro("Placa inválida")
        if (veiculo.modelo.isBlank() || veiculo.cor.isBlank()) return ResultadoOperacao.Erro("Informe modelo e cor")
        val ajustado = veiculo.copy(placa = Placa.normalizar(veiculo.placa))
        return try {
            if (ajustado.id == 0L) veiculoDao.inserir(ajustado) else veiculoDao.atualizar(ajustado)
            ResultadoOperacao.Sucesso("Veículo salvo")
        } catch (e: SQLiteConstraintException) {
            ResultadoOperacao.Erro("Já existe um veículo com essa placa")
        }
    }

    suspend fun excluirVeiculo(veiculo: Veiculo) = veiculoDao.excluir(veiculo)

    /** Cadastro feito pelo administrador: já nasce aprovado. */
    suspend fun cadastrarTroca(
        servidorId: Long, placa: String, modelo: String, cor: String, motivo: String, inicio: Long, fim: Long
    ): ResultadoOperacao =
        criarTroca(servidorId, placa, modelo, cor, motivo, inicio, fim, StatusSolicitacao.APROVADA)

    /** Solicitação feita pelo servidor: nasce pendente, à espera do administrador. */
    suspend fun solicitarTroca(
        servidorId: Long, placa: String, modelo: String, cor: String, motivo: String, inicio: Long, fim: Long
    ): ResultadoOperacao =
        criarTroca(servidorId, placa, modelo, cor, motivo, inicio, fim, StatusSolicitacao.PENDENTE)

    suspend fun avaliarSolicitacao(id: Long, aprovar: Boolean): ResultadoOperacao {
        val troca = trocaDao.buscar(id) ?: return ResultadoOperacao.Erro("Solicitação não encontrada")
        if (troca.status != StatusSolicitacao.PENDENTE) return ResultadoOperacao.Erro("Solicitação já decidida")
        val novoStatus = if (aprovar) StatusSolicitacao.APROVADA else StatusSolicitacao.RECUSADA
        trocaDao.atualizar(troca.copy(status = novoStatus))
        return ResultadoOperacao.Sucesso(if (aprovar) "Solicitação aprovada" else "Solicitação recusada")
    }

    private suspend fun criarTroca(
        servidorId: Long, placa: String, modelo: String, cor: String, motivo: String,
        inicio: Long, fim: Long, status: StatusSolicitacao
    ): ResultadoOperacao {
        if (!Placa.valida(placa)) return ResultadoOperacao.Erro("Placa inválida")
        if (modelo.isBlank() || cor.isBlank()) return ResultadoOperacao.Erro("Informe modelo e cor")
        if (motivo.isBlank()) return ResultadoOperacao.Erro("Informe o motivo")
        if (fim < inicio) return ResultadoOperacao.Erro("O fim da validade não pode ser anterior ao início")
        if (servidorDao.buscar(servidorId) == null) return ResultadoOperacao.Erro("Servidor não encontrado")
        trocaDao.inserir(
            TrocaTemporaria(
                servidorId = servidorId, placa = Placa.normalizar(placa), modelo = modelo.trim(),
                cor = cor.trim(), motivo = motivo.trim(), inicio = inicio, fim = fim, status = status
            )
        )
        val mensagem = if (status == StatusSolicitacao.APROVADA) "Troca temporária cadastrada" else "Solicitação enviada"
        return ResultadoOperacao.Sucesso(mensagem)
    }

    /** Cria dados fictícios na primeira execução, para testes e para a demonstração. */
    suspend fun popularDadosDeExemplo(agora: Long = System.currentTimeMillis()) {
        if (servidorDao.quantidade() > 0) return
        val professor = servidorDao.inserir(Servidor(nome = "Prof. Exemplo Silva", matricula = "2020001"))
        val servidor = servidorDao.inserir(Servidor(nome = "Servidor Teste Souza", matricula = "2020002"))
        veiculoDao.inserir(Veiculo(placa = "ABC1D23", modelo = "Gol", cor = "Branco", servidorId = professor))
        veiculoDao.inserir(Veiculo(placa = "XYZ9K88", modelo = "Civic", cor = "Preto", servidorId = servidor))
        // Troca temporária de exemplo (carro da esposa), válida por 7 dias a partir da primeira execução.
        trocaDao.inserir(
            TrocaTemporaria(
                servidorId = professor, placa = "BRA2E19", modelo = "Onix", cor = "Prata",
                motivo = "Carro da esposa", inicio = agora, fim = agora + 7L * 24 * 60 * 60 * 1000,
                status = StatusSolicitacao.APROVADA
            )
        )
    }
}
