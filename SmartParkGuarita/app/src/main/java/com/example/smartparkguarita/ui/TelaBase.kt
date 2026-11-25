package com.example.smartparkguarita.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Estrutura comum das telas: barra de título com botão Voltar e conteúdo centralizado,
 * com largura máxima pensada para o tablet da portaria.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaBase(
    titulo: String,
    aoVoltar: (() -> Unit)? = null,
    rolavel: Boolean = true,
    conteudo: @Composable ColumnScope.() -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(titulo, fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    if (aoVoltar != null) {
                        TextButton(
                            onClick = aoVoltar,
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            Text("Voltar")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { espacamento ->
        Box(
            modifier = Modifier.fillMaxSize().padding(espacamento),
            contentAlignment = Alignment.TopCenter
        ) {
            val base = Modifier.widthIn(max = 640.dp)
            val modificador = if (rolavel) {
                base.fillMaxWidth().verticalScroll(rememberScrollState())
            } else {
                base.fillMaxSize()
            }
            Column(
                modifier = modificador.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                content = conteudo
            )
        }
    }
}

private val formatoDataHora = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.forLanguageTag("pt-BR"))

fun formatarDataHora(milissegundos: Long): String = formatoDataHora.format(Date(milissegundos))
