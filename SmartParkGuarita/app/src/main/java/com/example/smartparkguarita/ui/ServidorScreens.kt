package com.example.smartparkguarita.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartparkguarita.viewmodel.AppViewModelProvider
import com.example.smartparkguarita.viewmodel.ServidorViewModel

/** O perfil do servidor não tem login: ele escolhe o próprio nome na lista. */
@Composable
fun ServidorEscolherScreen(
    aoVoltar: () -> Unit,
    aoEscolher: (Long) -> Unit,
    viewModel: ServidorViewModel = viewModel<ServidorViewModel>(factory = AppViewModelProvider.Fabrica)
) {
    val servidores by viewModel.servidores.collectAsState()
    TelaBase(titulo = "Quem é você?", aoVoltar = aoVoltar) {
        Text(
            "Escolha o seu nome para fazer e acompanhar pedidos de troca temporária.",
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        if (servidores.isEmpty()) {
            EstadoVazio("Nenhum servidor cadastrado. Peça ao administrador para cadastrar.")
        }
        servidores.forEach { servidor ->
            CartaoMenu(
                titulo = servidor.nome,
                descricao = "Matrícula ${servidor.matricula}",
                aoClicar = { aoEscolher(servidor.id) }
            )
        }
    }
}

/** US09 e US10: pedir uma troca temporária e acompanhar o andamento. */
@Composable
fun ServidorHomeScreen(
    servidorId: Long,
    aoVoltar: () -> Unit,
    viewModel: ServidorViewModel = viewModel<ServidorViewModel>(factory = AppViewModelProvider.Fabrica)
) {
    LaunchedEffect(servidorId) { viewModel.selecionar(servidorId) }
    val servidores by viewModel.servidores.collectAsState()
    val solicitacoes by viewModel.solicitacoes.collectAsState()
    val aviso by viewModel.aviso.collectAsState()
    val servidor = servidores.firstOrNull { it.id == servidorId }
    var nova by remember { mutableStateOf(false) }

    TelaBase(titulo = "Servidor/Professor", aoVoltar = aoVoltar, rolavel = false) {
        Text("Olá, ${servidor?.nome ?: "servidor"}", style = MaterialTheme.typography.titleLarge)
        Button(
            onClick = { viewModel.limparAviso(); nova = true },
            enabled = servidor != null,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Nova solicitação de troca temporária")
        }
        if (!nova) AvisoTexto(aviso)
        Text("Meus pedidos", style = MaterialTheme.typography.titleMedium)
        if (solicitacoes.isEmpty()) EstadoVazio("Você ainda não fez nenhum pedido.")
        LazyColumn(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(solicitacoes, key = { it.id }) { troca ->
                CartaoTroca(troca = troca, nomeServidor = null)
            }
        }
    }

    if (nova && servidor != null) {
        FormularioTroca(
            titulo = "Nova solicitação",
            servidores = emptyList(),
            servidorFixo = servidor,
            aviso = aviso,
            aoSalvar = { _, placa, modelo, cor, motivo, duracao ->
                viewModel.solicitar(servidor.id, placa, modelo, cor, motivo, duracao) { nova = false }
            },
            aoCancelar = { nova = false }
        )
    }
}
