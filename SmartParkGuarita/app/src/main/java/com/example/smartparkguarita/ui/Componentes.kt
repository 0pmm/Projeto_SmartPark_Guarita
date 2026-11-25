package com.example.smartparkguarita.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.example.smartparkguarita.data.entity.Servidor
import com.example.smartparkguarita.domain.Duracao
import com.example.smartparkguarita.domain.StatusSolicitacao
import com.example.smartparkguarita.ui.theme.AlertaFundo
import com.example.smartparkguarita.ui.theme.AlertaTexto
import com.example.smartparkguarita.ui.theme.ErroFundo
import com.example.smartparkguarita.ui.theme.ErroTexto
import com.example.smartparkguarita.ui.theme.SucessoFundo
import com.example.smartparkguarita.ui.theme.SucessoTexto
import com.example.smartparkguarita.viewmodel.Aviso

@Composable
fun AvisoTexto(aviso: Aviso?, modifier: Modifier = Modifier) {
    if (aviso == null) return
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (aviso.erro) ErroFundo else SucessoFundo,
            contentColor = if (aviso.erro) ErroTexto else SucessoTexto
        ),
        modifier = modifier.fillMaxWidth()
    ) {
        Text(aviso.texto, modifier = Modifier.padding(12.dp), fontWeight = FontWeight.Medium)
    }
}

/** Cartão grande e clicável, usado nos menus. O [selo] mostra um número ou aviso, como "3 pendentes". */
@Composable
fun CartaoMenu(titulo: String, descricao: String, aoClicar: () -> Unit, selo: String? = null) {
    Card(modifier = Modifier.fillMaxWidth().clickable(onClick = aoClicar)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(titulo, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(
                    descricao,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            if (selo != null) {
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        selo,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
fun EtiquetaStatus(status: StatusSolicitacao) {
    val (fundo, texto) = when (status) {
        StatusSolicitacao.PENDENTE -> AlertaFundo to AlertaTexto
        StatusSolicitacao.APROVADA -> SucessoFundo to SucessoTexto
        StatusSolicitacao.RECUSADA -> ErroFundo to ErroTexto
    }
    Surface(color = fundo, contentColor = texto, shape = RoundedCornerShape(50)) {
        Text(
            status.rotulo,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun EstadoVazio(texto: String) {
    Text(texto, color = MaterialTheme.colorScheme.onSurfaceVariant)
}

@Composable
fun CampoTexto(
    valor: String,
    aoMudar: (String) -> Unit,
    rotulo: String,
    modifier: Modifier = Modifier.fillMaxWidth(),
    letrasMaiusculas: Boolean = false
) {
    OutlinedTextField(
        value = valor,
        onValueChange = aoMudar,
        label = { Text(rotulo) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            capitalization = if (letrasMaiusculas) KeyboardCapitalization.Characters else KeyboardCapitalization.Sentences
        ),
        modifier = modifier
    )
}

@Composable
fun ConfirmarDialogo(titulo: String, texto: String, aoConfirmar: () -> Unit, aoCancelar: () -> Unit) {
    AlertDialog(
        onDismissRequest = aoCancelar,
        title = { Text(titulo) },
        text = { Text(texto) },
        confirmButton = { TextButton(onClick = aoConfirmar) { Text("Excluir") } },
        dismissButton = { TextButton(onClick = aoCancelar) { Text("Cancelar") } }
    )
}

/** Botão que mostra o servidor escolhido e abre uma lista para trocar. */
@Composable
fun SeletorServidor(
    servidores: List<Servidor>,
    selecionado: Servidor?,
    aoSelecionar: (Servidor) -> Unit
) {
    var aberto by remember { mutableStateOf(false) }
    OutlinedButton(onClick = { aberto = true }, modifier = Modifier.fillMaxWidth()) {
        Text(selecionado?.let { "Servidor: ${it.nome}" } ?: "Escolher servidor")
    }
    if (aberto) {
        AlertDialog(
            onDismissRequest = { aberto = false },
            title = { Text("Escolha o servidor") },
            text = {
                if (servidores.isEmpty()) {
                    Text("Cadastre um servidor primeiro.")
                } else {
                    LazyColumn(modifier = Modifier.heightIn(max = 320.dp)) {
                        items(servidores, key = { it.id }) { servidor ->
                            TextButton(
                                onClick = { aoSelecionar(servidor); aberto = false },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(servidor.nome)
                            }
                        }
                    }
                }
            },
            confirmButton = { TextButton(onClick = { aberto = false }) { Text("Fechar") } }
        )
    }
}

/** Escolha da validade da troca temporária: hoje, 3 dias ou 7 dias. */
@Composable
fun SeletorDuracao(selecionada: Duracao, aoSelecionar: (Duracao) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Validade (termina às 23h59)", style = MaterialTheme.typography.labelLarge)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Duracao.entries.forEach { duracao ->
                FilterChip(
                    selected = duracao == selecionada,
                    onClick = { aoSelecionar(duracao) },
                    label = { Text(duracao.rotulo) }
                )
            }
        }
    }
}
