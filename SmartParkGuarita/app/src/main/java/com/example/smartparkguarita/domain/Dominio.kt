package com.example.smartparkguarita.domain

import java.time.Instant
import java.time.ZoneId

/** Perfis do aplicativo, escolhidos na tela inicial. */
enum class Perfil(val rotulo: String, val descricao: String) {
    PORTEIRO("Porteiro", "Consultar placas e registrar entradas e saídas"),
    ADMINISTRADOR("Administrador", "Cadastros, trocas temporárias, solicitações e painel"),
    SERVIDOR("Servidor/Professor", "Pedir uma troca temporária e acompanhar o pedido")
}

/** Situação de uma solicitação de troca temporária de veículo. */
enum class StatusSolicitacao(val rotulo: String) {
    PENDENTE("Pendente"),
    APROVADA("Aprovada"),
    RECUSADA("Recusada")
}

/**
 * Turnos usados no painel.
 * Horários provisórios (pergunta 11 da entrevista): manhã 6h às 12h, tarde 12h às 18h e noite 18h às 6h.
 */
enum class Turno(val rotulo: String) {
    MANHA("Manhã"),
    TARDE("Tarde"),
    NOITE("Noite");

    companion object {
        fun deHora(hora: Int): Turno = when (hora) {
            in 6..11 -> MANHA
            in 12..17 -> TARDE
            else -> NOITE
        }

        fun deInstante(milissegundos: Long): Turno {
            val hora = Instant.ofEpochMilli(milissegundos).atZone(ZoneId.systemDefault()).hour
            return deHora(hora)
        }
    }
}

/** Validação e normalização de placas (formato antigo ABC1234 e Mercosul ABC1D23). */
object Placa {
    private val padrao = Regex("^[A-Z]{3}[0-9][A-Z0-9][0-9]{2}$")

    fun normalizar(texto: String): String = texto.uppercase().filter { it.isLetterOrDigit() }

    fun valida(texto: String): Boolean = padrao.matches(normalizar(texto))
}

/** Resultado da consulta de uma placa na portaria. */
sealed interface ResultadoConsulta {
    data class Autorizado(val placa: String, val servidor: String, val modelo: String) : ResultadoConsulta

    data class AutorizadoTroca(val placa: String, val servidor: String, val validoAte: Long) : ResultadoConsulta

    data class NaoAutorizado(val placa: String) : ResultadoConsulta

    data object PlacaInvalida : ResultadoConsulta
}

/** Resultado de uma operação que pode falhar com uma mensagem para o usuário. */
sealed interface ResultadoOperacao {
    data class Sucesso(val mensagem: String) : ResultadoOperacao

    data class Erro(val mensagem: String) : ResultadoOperacao
}

/** Validade de uma troca temporária: começa agora e termina às 23h59 do último dia. */
enum class Duracao(val rotulo: String, private val dias: Long) {
    HOJE("Hoje", 0),
    TRES_DIAS("3 dias", 2),
    SETE_DIAS("7 dias", 6);

    /** Devolve o início (o instante [agora]) e o fim da validade, em milissegundos. */
    fun intervalo(agora: Long, zona: ZoneId = ZoneId.systemDefault()): Pair<Long, Long> {
        val ultimoDia = Instant.ofEpochMilli(agora).atZone(zona).toLocalDate().plusDays(dias)
        val fim = ultimoDia.atTime(23, 59, 59).atZone(zona).toInstant().toEpochMilli()
        return agora to fim
    }
}

/** Entradas e saídas de um turno, usadas no painel. */
data class MovimentoTurno(val turno: Turno, val entradas: Int, val saidas: Int)
