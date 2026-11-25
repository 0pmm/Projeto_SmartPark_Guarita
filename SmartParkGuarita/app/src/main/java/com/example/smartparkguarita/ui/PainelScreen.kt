package com.example.smartparkguarita.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartparkguarita.viewmodel.AppViewModelProvider
import com.example.smartparkguarita.viewmodel.PainelViewModel

/** US11 e US12: ocupação atual e entradas e saídas por turno. */
@Composable
fun PainelScreen(
    aoVoltar: () -> Unit,
    viewModel: PainelViewModel = viewModel<PainelViewModel>(factory = AppViewModelProvider.Fabrica)
) {
    val ocupacao by viewModel.ocupacaoAtual.collectAsState()
    val movimento by viewModel.movimentoPorTurno.collectAsState()

    TelaBase(titulo = "Painel", aoVoltar = aoVoltar) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("Ocupação atual", style = MaterialTheme.typography.titleMedium)
                Text(
                    text = if (ocupacao == 1) "1 veículo no estacionamento" else "$ocupacao veículos no estacionamento",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Text("Entradas e saídas por turno", style = MaterialTheme.typography.titleMedium)
        movimento.forEach { item ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(item.turno.rotulo, fontWeight = FontWeight.Bold)
                    Text("Entradas: ${item.entradas}   Saídas: ${item.saidas}")
                }
            }
        }
    }
}
