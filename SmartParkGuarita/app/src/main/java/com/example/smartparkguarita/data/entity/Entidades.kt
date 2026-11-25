package com.example.smartparkguarita.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.smartparkguarita.domain.StatusSolicitacao

@Entity(tableName = "servidor")
data class Servidor(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nome: String,
    val matricula: String
)

@Entity(
    tableName = "veiculo",
    foreignKeys = [
        ForeignKey(
            entity = Servidor::class,
            parentColumns = ["id"],
            childColumns = ["servidorId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["placa"], unique = true), Index(value = ["servidorId"])]
)
data class Veiculo(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val placa: String,
    val modelo: String,
    val cor: String,
    val servidorId: Long
)

@Entity(
    tableName = "troca_temporaria",
    foreignKeys = [
        ForeignKey(
            entity = Servidor::class,
            parentColumns = ["id"],
            childColumns = ["servidorId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["servidorId"]), Index(value = ["placa"])]
)
data class TrocaTemporaria(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val servidorId: Long,
    val placa: String,
    val modelo: String,
    val cor: String,
    val motivo: String,
    /** Início da validade, em milissegundos desde 1970. */
    val inicio: Long,
    /** Fim da validade, em milissegundos desde 1970. */
    val fim: Long,
    val status: StatusSolicitacao = StatusSolicitacao.PENDENTE
)

@Entity(tableName = "registro_acesso", indices = [Index(value = ["placa"])])
data class RegistroAcesso(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val placa: String,
    val entrada: Long,
    /** Nula enquanto o veículo está dentro do câmpus. */
    val saida: Long? = null,
    val porteiro: String
)
