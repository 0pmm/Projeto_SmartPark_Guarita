package com.example.smartparkguarita.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.smartparkguarita.domain.Perfil

object Rotas {
    const val PERFIL = "perfil"
    const val PORTEIRO = "porteiro"
    const val PRESENTES = "presentes"
    const val ADMINISTRADOR = "administrador"
    const val ADMIN_SERVIDORES = "administrador/servidores"
    const val ADMIN_VEICULOS = "administrador/veiculos"
    const val ADMIN_TROCAS = "administrador/trocas"
    const val ADMIN_SOLICITACOES = "administrador/solicitacoes"
    const val SERVIDOR = "servidor"
    const val SERVIDOR_HOME = "servidor/{servidorId}"
    const val PAINEL = "painel"

    fun servidorHome(servidorId: Long) = "servidor/$servidorId"
}

@Composable
fun SmartParkNavHost() {
    val navegacao = rememberNavController()
    val voltar: () -> Unit = { navegacao.popBackStack() }

    NavHost(navController = navegacao, startDestination = Rotas.PERFIL) {
        composable(Rotas.PERFIL) {
            PerfilScreen(
                aoEscolher = { perfil ->
                    navegacao.navigate(
                        when (perfil) {
                            Perfil.PORTEIRO -> Rotas.PORTEIRO
                            Perfil.ADMINISTRADOR -> Rotas.ADMINISTRADOR
                            Perfil.SERVIDOR -> Rotas.SERVIDOR
                        }
                    )
                }
            )
        }

        // Porteiro
        composable(Rotas.PORTEIRO) {
            PorteiroScreen(aoVoltar = voltar, aoVerPresentes = { navegacao.navigate(Rotas.PRESENTES) })
        }
        composable(Rotas.PRESENTES) { PresentesScreen(aoVoltar = voltar) }

        // Administrador
        composable(Rotas.ADMINISTRADOR) {
            AdminHomeScreen(aoVoltar = voltar, aoNavegar = { rota -> navegacao.navigate(rota) })
        }
        composable(Rotas.ADMIN_SERVIDORES) { ServidoresScreen(aoVoltar = voltar) }
        composable(Rotas.ADMIN_VEICULOS) { VeiculosScreen(aoVoltar = voltar) }
        composable(Rotas.ADMIN_TROCAS) { TrocasScreen(aoVoltar = voltar) }
        composable(Rotas.ADMIN_SOLICITACOES) { SolicitacoesScreen(aoVoltar = voltar) }
        composable(Rotas.PAINEL) { PainelScreen(aoVoltar = voltar) }

        // Servidor/Professor
        composable(Rotas.SERVIDOR) {
            ServidorEscolherScreen(
                aoVoltar = voltar,
                aoEscolher = { id -> navegacao.navigate(Rotas.servidorHome(id)) }
            )
        }
        composable(
            route = Rotas.SERVIDOR_HOME,
            arguments = listOf(navArgument("servidorId") { type = NavType.LongType })
        ) { entrada ->
            ServidorHomeScreen(
                servidorId = entrada.arguments?.getLong("servidorId") ?: 0L,
                aoVoltar = voltar
            )
        }
    }
}
