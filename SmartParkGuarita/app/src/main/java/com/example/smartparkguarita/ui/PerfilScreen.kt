package com.example.smartparkguarita.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.smartparkguarita.domain.Perfil

/** Tela inicial: o perfil é escolhido aqui, sem login (suposição do TAP, a confirmar na entrevista). */
@Composable
fun PerfilScreen(aoEscolher: (Perfil) -> Unit) {
    TelaBase(titulo = "SmartPark Guarita") {
        Text(
            "Controle do estacionamento do câmpus",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text("Escolha o seu perfil", style = MaterialTheme.typography.titleLarge)
        Perfil.entries.forEach { perfil ->
            CartaoMenu(
                titulo = perfil.rotulo,
                descricao = perfil.descricao,
                aoClicar = { aoEscolher(perfil) }
            )
        }
    }
}
