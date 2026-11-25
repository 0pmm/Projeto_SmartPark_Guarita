package com.example.smartparkguarita.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartparkguarita.data.entity.Servidor
import com.example.smartparkguarita.data.entity.TrocaTemporaria
import com.example.smartparkguarita.data.entity.Veiculo
import com.example.smartparkguarita.domain.Duracao
import com.example.smartparkguarita.domain.Placa
import com.example.smartparkguarita.viewmodel.AdminViewModel
import com.example.smartparkguarita.viewmodel.AppViewModelProvider
import com.example.smartparkguarita.viewmodel.Aviso

@Composable
fun AdminHomeScreen(
    aoVoltar: () -> Unit,
    aoNavegar: (String) -> Unit,
    viewModel: AdminViewModel = viewModel<AdminViewModel>(factory = AppViewModelProvider.Fabrica)
) {
    val pendentes by viewModel.pendentes.collectAsState()
    TelaBase(titulo = "Administrador", aoVoltar = aoVoltar) {
        CartaoMenu(
            titulo = "Solicitações",
            descricao = "Aprovar ou recusar pedidos de troca temporária",
            aoClicar = { aoNavegar(Rotas.ADMIN_SOLICITACOES) },
            selo = if (pendentes.isEmpty()) null else "${pendentes.size} pendente(s)"
        )
        CartaoMenu("Servidores", "Cadastrar, editar e excluir servidores", { aoNavegar(Rotas.ADMIN_SERVIDORES) })
        CartaoMenu("Veículos", "Veículos de cada servidor", { aoNavegar(Rotas.ADMIN_VEICULOS) })
        CartaoMenu("Trocas temporárias", "Autorizar outro veículo por um período", { aoNavegar(Rotas.ADMIN_TROCAS) })
        CartaoMenu("Painel", "Ocupação e movimento por turno", { aoNavegar(Rotas.PAINEL) })
    }
}

@Composable
fun ServidoresScreen(
    aoVoltar: () -> Unit,
    viewModel: AdminViewModel = viewModel<AdminViewModel>(factory = AppViewModelProvider.Fabrica)
) {
    val servidores by viewModel.servidores.collectAsState()
    val aviso by viewModel.aviso.collectAsState()
    var novo by remember { mutableStateOf(false) }
    var edicao by remember { mutableStateOf<Servidor?>(null) }
    var exclusao by remember { mutableStateOf<Servidor?>(null) }
    val formularioAberto = novo || edicao != null

    TelaBase(titulo = "Servidores", aoVoltar = aoVoltar, rolavel = false) {
        Button(
            onClick = { viewModel.limparAviso(); novo = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Novo servidor")
        }
        if (!formularioAberto) AvisoTexto(aviso)
        if (servidores.isEmpty()) EstadoVazio("Nenhum servidor cadastrado.")
        LazyColumn(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(servidores, key = { it.id }) { servidor ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(servidor.nome, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text(
                            "Matrícula: ${servidor.matricula}",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Row {
                            TextButton(onClick = { viewModel.limparAviso(); edicao = servidor }) { Text("Editar") }
                            TextButton(onClick = { exclusao = servidor }) { Text("Excluir") }
                        }
                    }
                }
            }
        }
    }

    if (formularioAberto) {
        FormularioServidor(
            inicial = edicao,
            aviso = aviso,
            aoSalvar = { nome, matricula ->
                viewModel.salvarServidor(edicao?.id ?: 0L, nome, matricula) { novo = false; edicao = null }
            },
            aoCancelar = { novo = false; edicao = null }
        )
    }
    exclusao?.let { servidor ->
        ConfirmarDialogo(
            titulo = "Excluir servidor",
            texto = "Excluir ${servidor.nome}? Os veículos e as trocas temporárias dele também serão excluídos.",
            aoConfirmar = { viewModel.excluirServidor(servidor); exclusao = null },
            aoCancelar = { exclusao = null }
        )
    }
}

@Composable
private fun FormularioServidor(
    inicial: Servidor?,
    aviso: Aviso?,
    aoSalvar: (String, String) -> Unit,
    aoCancelar: () -> Unit
) {
    var nome by remember { mutableStateOf(inicial?.nome ?: "") }
    var matricula by remember { mutableStateOf(inicial?.matricula ?: "") }
    AlertDialog(
        onDismissRequest = aoCancelar,
        title = { Text(if (inicial == null) "Novo servidor" else "Editar servidor") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                CampoTexto(nome, { nome = it }, "Nome")
                CampoTexto(matricula, { matricula = it }, "Matrícula")
                AvisoTexto(aviso?.takeIf { it.erro })
            }
        },
        confirmButton = { TextButton(onClick = { aoSalvar(nome, matricula) }) { Text("Salvar") } },
        dismissButton = { TextButton(onClick = aoCancelar) { Text("Cancelar") } }
    )
}

@Composable
fun VeiculosScreen(
    aoVoltar: () -> Unit,
    viewModel: AdminViewModel = viewModel<AdminViewModel>(factory = AppViewModelProvider.Fabrica)
) {
    val veiculos by viewModel.veiculos.collectAsState()
    val servidores by viewModel.servidores.collectAsState()
    val aviso by viewModel.aviso.collectAsState()
    var novo by remember { mutableStateOf(false) }
    var edicao by remember { mutableStateOf<Veiculo?>(null) }
    var exclusao by remember { mutableStateOf<Veiculo?>(null) }
    val formularioAberto = novo || edicao != null
    val nomes = servidores.associate { it.id to it.nome }

    TelaBase(titulo = "Veículos", aoVoltar = aoVoltar, rolavel = false) {
        Button(
            onClick = { viewModel.limparAviso(); novo = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Novo veículo")
        }
        if (!formularioAberto) AvisoTexto(aviso)
        if (veiculos.isEmpty()) EstadoVazio("Nenhum veículo cadastrado.")
        LazyColumn(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(veiculos, key = { it.id }) { veiculo ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(veiculo.placa, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text("${veiculo.modelo}, ${veiculo.cor}")
                        Text(
                            "Servidor: ${nomes[veiculo.servidorId] ?: "não encontrado"}",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Row {
                            TextButton(onClick = { viewModel.limparAviso(); edicao = veiculo }) { Text("Editar") }
                            TextButton(onClick = { exclusao = veiculo }) { Text("Excluir") }
                        }
                    }
                }
            }
        }
    }

    if (formularioAberto) {
        FormularioVeiculo(
            inicial = edicao,
            servidores = servidores,
            aviso = aviso,
            aoSalvar = { placa, modelo, cor, servidorId ->
                viewModel.salvarVeiculo(edicao?.id ?: 0L, placa, modelo, cor, servidorId) {
                    novo = false
                    edicao = null
                }
            },
            aoCancelar = { novo = false; edicao = null }
        )
    }
    exclusao?.let { veiculo ->
        ConfirmarDialogo(
            titulo = "Excluir veículo",
            texto = "Excluir o veículo ${veiculo.placa}?",
            aoConfirmar = { viewModel.excluirVeiculo(veiculo); exclusao = null },
            aoCancelar = { exclusao = null }
        )
    }
}

@Composable
private fun FormularioVeiculo(
    inicial: Veiculo?,
    servidores: List<Servidor>,
    aviso: Aviso?,
    aoSalvar: (String, String, String, Long?) -> Unit,
    aoCancelar: () -> Unit
) {
    var placa by remember { mutableStateOf(inicial?.placa ?: "") }
    var modelo by remember { mutableStateOf(inicial?.modelo ?: "") }
    var cor by remember { mutableStateOf(inicial?.cor ?: "") }
    var servidor by remember { mutableStateOf(servidores.firstOrNull { it.id == inicial?.servidorId }) }
    AlertDialog(
        onDismissRequest = aoCancelar,
        title = { Text(if (inicial == null) "Novo veículo" else "Editar veículo") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                CampoTexto(placa, { placa = Placa.normalizar(it).take(7) }, "Placa", letrasMaiusculas = true)
                CampoTexto(modelo, { modelo = it }, "Modelo")
                CampoTexto(cor, { cor = it }, "Cor")
                SeletorServidor(servidores, servidor) { servidor = it }
                AvisoTexto(aviso?.takeIf { it.erro })
            }
        },
        confirmButton = { TextButton(onClick = { aoSalvar(placa, modelo, cor, servidor?.id) }) { Text("Salvar") } },
        dismissButton = { TextButton(onClick = aoCancelar) { Text("Cancelar") } }
    )
}

@Composable
fun TrocasScreen(
    aoVoltar: () -> Unit,
    viewModel: AdminViewModel = viewModel<AdminViewModel>(factory = AppViewModelProvider.Fabrica)
) {
    val trocas by viewModel.trocas.collectAsState()
    val servidores by viewModel.servidores.collectAsState()
    val aviso by viewModel.aviso.collectAsState()
    var novo by remember { mutableStateOf(false) }
    val nomes = servidores.associate { it.id to it.nome }

    TelaBase(titulo = "Trocas temporárias", aoVoltar = aoVoltar, rolavel = false) {
        Button(
            onClick = { viewModel.limparAviso(); novo = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Nova troca temporária")
        }
        if (!novo) AvisoTexto(aviso)
        if (trocas.isEmpty()) EstadoVazio("Nenhuma troca temporária cadastrada.")
        LazyColumn(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(trocas, key = { it.id }) { troca ->
                CartaoTroca(troca = troca, nomeServidor = nomes[troca.servidorId])
            }
        }
    }

    if (novo) {
        FormularioTroca(
            titulo = "Nova troca temporária",
            servidores = servidores,
            servidorFixo = null,
            aviso = aviso,
            aoSalvar = { servidorId, placa, modelo, cor, motivo, duracao ->
                viewModel.cadastrarTroca(servidorId, placa, modelo, cor, motivo, duracao) { novo = false }
            },
            aoCancelar = { novo = false }
        )
    }
}

/** Dados de uma troca temporária, usado na lista do administrador e na do servidor. */
@Composable
fun CartaoTroca(troca: TrocaTemporaria, nomeServidor: String?, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(troca.placa, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                EtiquetaStatus(troca.status)
            }
            Text("${troca.modelo}, ${troca.cor}")
            if (nomeServidor != null) {
                Text("Servidor: $nomeServidor", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Text("Motivo: ${troca.motivo}", color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(
                "De ${formatarDataHora(troca.inicio)} até ${formatarDataHora(troca.fim)}",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/**
 * Formulário de troca temporária. No administrador o servidor é escolhido na lista;
 * no perfil do servidor ele já vem fixo ([servidorFixo]).
 */
@Composable
fun FormularioTroca(
    titulo: String,
    servidores: List<Servidor>,
    servidorFixo: Servidor?,
    aviso: Aviso?,
    aoSalvar: (Long?, String, String, String, String, Duracao) -> Unit,
    aoCancelar: () -> Unit
) {
    var servidor by remember { mutableStateOf(servidorFixo) }
    var placa by remember { mutableStateOf("") }
    var modelo by remember { mutableStateOf("") }
    var cor by remember { mutableStateOf("") }
    var motivo by remember { mutableStateOf("") }
    var duracao by remember { mutableStateOf(Duracao.HOJE) }
    AlertDialog(
        onDismissRequest = aoCancelar,
        title = { Text(titulo) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                if (servidorFixo == null) {
                    SeletorServidor(servidores, servidor) { servidor = it }
                }
                CampoTexto(placa, { placa = Placa.normalizar(it).take(7) }, "Placa do veículo", letrasMaiusculas = true)
                CampoTexto(modelo, { modelo = it }, "Modelo")
                CampoTexto(cor, { cor = it }, "Cor")
                CampoTexto(motivo, { motivo = it }, "Motivo (ex.: carro da esposa)")
                SeletorDuracao(duracao) { duracao = it }
                AvisoTexto(aviso?.takeIf { it.erro })
            }
        },
        confirmButton = {
            TextButton(onClick = { aoSalvar(servidor?.id, placa, modelo, cor, motivo, duracao) }) { Text("Salvar") }
        },
        dismissButton = { TextButton(onClick = aoCancelar) { Text("Cancelar") } }
    )
}

@Composable
fun SolicitacoesScreen(
    aoVoltar: () -> Unit,
    viewModel: AdminViewModel = viewModel<AdminViewModel>(factory = AppViewModelProvider.Fabrica)
) {
    val pendentes by viewModel.pendentes.collectAsState()
    val servidores by viewModel.servidores.collectAsState()
    val aviso by viewModel.aviso.collectAsState()
    val nomes = servidores.associate { it.id to it.nome }

    TelaBase(titulo = "Solicitações pendentes", aoVoltar = aoVoltar, rolavel = false) {
        AvisoTexto(aviso)
        if (pendentes.isEmpty()) EstadoVazio("Não há solicitações pendentes.")
        LazyColumn(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(pendentes, key = { it.id }) { troca ->
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    CartaoTroca(troca = troca, nomeServidor = nomes[troca.servidorId])
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(
                            onClick = { viewModel.avaliar(troca.id, aprovar = false) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Recusar")
                        }
                        Button(
                            onClick = { viewModel.avaliar(troca.id, aprovar = true) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Aprovar")
                        }
                    }
                }
            }
        }
    }
}
