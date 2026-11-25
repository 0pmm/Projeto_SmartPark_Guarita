package com.example.smartparkguarita.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PlacaTest {
    @Test
    fun aceitaPlacaNoFormatoMercosul() {
        assertTrue(Placa.valida("BRA2E19"))
    }

    @Test
    fun aceitaPlacaNoFormatoAntigoComHifen() {
        assertTrue(Placa.valida("abc-1234"))
    }

    @Test
    fun rejeitaPlacaCurta() {
        assertFalse(Placa.valida("12"))
    }

    @Test
    fun rejeitaPlacaSemLetrasNoInicio() {
        assertFalse(Placa.valida("1234567"))
    }

    @Test
    fun normalizaParaMaiusculasESemSeparadores() {
        assertEquals("ABC1234", Placa.normalizar("abc-1234"))
    }
}

class DuracaoTest {
    private val utc = java.time.ZoneOffset.UTC
    private val agora = java.time.Instant.parse("2026-11-25T10:00:00Z").toEpochMilli()

    @Test
    fun hojeTerminaAs2359DoMesmoDia() {
        val (inicio, fim) = Duracao.HOJE.intervalo(agora, utc)
        assertEquals(agora, inicio)
        assertEquals(java.time.Instant.parse("2026-11-25T23:59:59Z").toEpochMilli(), fim)
    }

    @Test
    fun seteDiasTerminaNoSetimoDiaContandoHoje() {
        val (_, fim) = Duracao.SETE_DIAS.intervalo(agora, utc)
        assertEquals(java.time.Instant.parse("2026-12-01T23:59:59Z").toEpochMilli(), fim)
    }

    @Test
    fun oFimNuncaEAnteriorAoInicio() {
        Duracao.entries.forEach { duracao ->
            val (inicio, fim) = duracao.intervalo(agora, utc)
            assertTrue(fim >= inicio)
        }
    }
}

class TurnoTest {
    @Test
    fun manhaVaiDas6AsOnze() {
        assertEquals(Turno.MANHA, Turno.deHora(6))
        assertEquals(Turno.MANHA, Turno.deHora(11))
    }

    @Test
    fun tardeVaiDas12AsDezessete() {
        assertEquals(Turno.TARDE, Turno.deHora(12))
        assertEquals(Turno.TARDE, Turno.deHora(17))
    }

    @Test
    fun noiteCobreDas18AteAMadrugada() {
        assertEquals(Turno.NOITE, Turno.deHora(18))
        assertEquals(Turno.NOITE, Turno.deHora(0))
        assertEquals(Turno.NOITE, Turno.deHora(5))
    }
}
