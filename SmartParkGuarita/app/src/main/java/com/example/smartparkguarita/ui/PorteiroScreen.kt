package com.example.smartparkguarita.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartparkguarita.domain.ResultadoConsulta
import com.example.smartparkguarita.viewmodel.AppViewModelProvider
import com.example.smartparkguarita.viewmodel.PorteiroViewModel

private val VerdeFundo = Color(0xFFD9F2E1)
private val VerdeTexto = Color(0xFF14532D)
private val VermelhoFundo = Color(0xFFFBE0E0)
private val VermelhoTexto = Color(0xFF7F1D1D)

/** US01, US02 e US03: consultar a placa e registrar entrada e saída. */
@Composable
fun PorteiroScreen(
    aoVoltar: () -> Unit,
    aoVerPresentes: () -> Unit,
    viewModel: PorteiroViewModel = viewModel<PorteiroViewModel>(factory = AppViewModelProvider.Fabrica)
) {
    val estado by viewModel.estado.collectAsState()
    val presentes by viewModel.presentes.collectAsState()

    TelaBase(titulo = "Porteiro", aoVoltar = aoVoltar) {
        OutlinedTextField(
            value = estado.placa,
            onValueChange = viewModel::aoMudarPlaca,
            label = { Text("Placa") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Characters,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(onSearch = { viewModel.consultar() }),
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = viewModel::consultar,
            enabled = estado.podeConsultar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Consultar")
        }

        estado.resultado?.let { CartaoResultado(it) }

        estado.mensagem?.let {
            Text(
                text = it,
                color = if (estado.mensagemEhErro) MaterialTheme.colorScheme.error else VerdeTexto,
                fontWeight = FontWeight.Medium
            )
        }

        Button(
            onClick = viewModel::registrarEntrada,
            enabled = estado.podeRegistrarEntrada,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar entrada")
        }
        OutlinedButton(
            onClick = viewModel::registrarSaida,
            enabled = estado.podeConsultar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar saída")
        }
        TextButton(onClick = aoVerPresentes, modifier = Modifier.fillMaxWidth()) {
            Text("Veículos presentes (${presentes.size})")
        }
    }
}

@Composable
private fun CartaoResultado(resultado: ResultadoConsulta) {
    val (titulo, detalhe, autorizado) = when (resultado) {
        is ResultadoConsulta.Autorizado ->
            Triple("AUTORIZADO", "${resultado.servidor} - ${resultado.modelo}", true)
        is ResultadoConsulta.AutorizadoTroca ->
            Triple(
                "AUTORIZADO - TROCA TEMPORÁRIA",
                "${resultado.servidor}. Válida até ${formatarDataHora(resultado.validoAte)}",
                true
            )
        is ResultadoConsulta.NaoAutorizado ->
            Triple("NÃO AUTORIZADO", "A placa ${resultado.placa} não consta no cadastro", false)
        ResultadoConsulta.PlacaInvalida ->
            Triple("PLACA INVÁLIDA", "Use o formato ABC1234 ou ABC1D23", false)
    }
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (autorizado) VerdeFundo else VermelhoFundo,
            contentColor = if (autorizado) VerdeTexto else VermelhoTexto
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(titulo, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text(detalhe)
        }
    }
}
