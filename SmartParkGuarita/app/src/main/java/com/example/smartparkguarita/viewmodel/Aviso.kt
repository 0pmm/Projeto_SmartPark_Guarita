package com.example.smartparkguarita.viewmodel

import com.example.smartparkguarita.domain.ResultadoOperacao

/** Mensagem curta mostrada ao usuário depois de uma ação, de sucesso ou de erro. */
data class Aviso(val texto: String, val erro: Boolean)

fun ResultadoOperacao.paraAviso(): Aviso = when (this) {
    is ResultadoOperacao.Sucesso -> Aviso(mensagem, erro = false)
    is ResultadoOperacao.Erro -> Aviso(mensagem, erro = true)
}
