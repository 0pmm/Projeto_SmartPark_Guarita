package com.example.smartparkguarita.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.smartparkguarita.data.entity.RegistroAcesso
import com.example.smartparkguarita.data.entity.Servidor
import com.example.smartparkguarita.data.entity.TrocaTemporaria
import com.example.smartparkguarita.data.entity.Veiculo
import kotlinx.coroutines.flow.Flow

@Dao
interface ServidorDao {
    @Insert
    suspend fun inserir(servidor: Servidor): Long

    @Update
    suspend fun atualizar(servidor: Servidor)

    @Delete
    suspend fun excluir(servidor: Servidor)

    @Query("SELECT * FROM servidor ORDER BY nome")
    fun todos(): Flow<List<Servidor>>

    @Query("SELECT * FROM servidor WHERE id = :id")
    suspend fun buscar(id: Long): Servidor?

    @Query("SELECT COUNT(*) FROM servidor")
    suspend fun quantidade(): Int
}

@Dao
interface VeiculoDao {
    @Insert
    suspend fun inserir(veiculo: Veiculo): Long

    @Update
    suspend fun atualizar(veiculo: Veiculo)

    @Delete
    suspend fun excluir(veiculo: Veiculo)

    @Query("SELECT * FROM veiculo ORDER BY placa")
    fun todos(): Flow<List<Veiculo>>

    @Query("SELECT * FROM veiculo WHERE placa = :placa LIMIT 1")
    suspend fun buscarPorPlaca(placa: String): Veiculo?
}

@Dao
interface TrocaTemporariaDao {
    @Insert
    suspend fun inserir(troca: TrocaTemporaria): Long

    @Update
    suspend fun atualizar(troca: TrocaTemporaria)

    @Query("SELECT * FROM troca_temporaria ORDER BY inicio DESC")
    fun todas(): Flow<List<TrocaTemporaria>>

    @Query("SELECT * FROM troca_temporaria WHERE status = 'PENDENTE' ORDER BY inicio")
    fun pendentes(): Flow<List<TrocaTemporaria>>

    @Query("SELECT * FROM troca_temporaria WHERE servidorId = :servidorId ORDER BY inicio DESC")
    fun porServidor(servidorId: Long): Flow<List<TrocaTemporaria>>

    @Query("SELECT * FROM troca_temporaria WHERE id = :id")
    suspend fun buscar(id: Long): TrocaTemporaria?

    /** Troca aprovada cuja validade inclui o instante [agora]. */
    @Query(
        "SELECT * FROM troca_temporaria " +
            "WHERE placa = :placa AND status = 'APROVADA' AND inicio <= :agora AND fim >= :agora " +
            "ORDER BY fim DESC LIMIT 1"
    )
    suspend fun validaPara(placa: String, agora: Long): TrocaTemporaria?
}

@Dao
interface RegistroAcessoDao {
    @Insert
    suspend fun inserir(registro: RegistroAcesso): Long

    @Update
    suspend fun atualizar(registro: RegistroAcesso)

    @Query("SELECT * FROM registro_acesso WHERE placa = :placa AND saida IS NULL LIMIT 1")
    suspend fun buscarAberto(placa: String): RegistroAcesso?

    @Query("SELECT * FROM registro_acesso WHERE saida IS NULL ORDER BY entrada DESC")
    fun presentes(): Flow<List<RegistroAcesso>>

    @Query("SELECT * FROM registro_acesso ORDER BY entrada DESC")
    fun todos(): Flow<List<RegistroAcesso>>
}
