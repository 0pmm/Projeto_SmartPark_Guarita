package com.example.smartparkguarita.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.smartparkguarita.viewmodel.PorteiroViewModel

/** US04: veículos que entraram e ainda não saíram. */
@Composable
fun PresentesScreen(
    aoVoltar: () -> Unit,
    viewModel: PorteiroViewModel = viewModel<PorteiroViewModel>(factory = AppViewModelProvider.Fabrica)
) {
    val presentes by viewModel.presentes.collectAsState()

    TelaBase(titulo = "Veículos presentes (${presentes.size})", aoVoltar = aoVoltar, rolavel = false) {
        if (presentes.isEmpty()) {
            Text("Não há veículos no câmpus no momento.")
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(presentes, key = { it.id }) { registro ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = registro.placa,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text("Entrada: ${formatarDataHora(registro.entrada)}")
                            Text("Porteiro: ${registro.porteiro}")
                        }
                    }
                }
            }
        }
    }
}
