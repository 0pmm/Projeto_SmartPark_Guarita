package com.example.smartparkguarita.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.smartparkguarita.data.dao.RegistroAcessoDao
import com.example.smartparkguarita.data.dao.ServidorDao
import com.example.smartparkguarita.data.dao.TrocaTemporariaDao
import com.example.smartparkguarita.data.dao.VeiculoDao
import com.example.smartparkguarita.data.entity.RegistroAcesso
import com.example.smartparkguarita.data.entity.Servidor
import com.example.smartparkguarita.data.entity.TrocaTemporaria
import com.example.smartparkguarita.data.entity.Veiculo

@Database(
    entities = [Servidor::class, Veiculo::class, TrocaTemporaria::class, RegistroAcesso::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun servidorDao(): ServidorDao
    abstract fun veiculoDao(): VeiculoDao
    abstract fun trocaTemporariaDao(): TrocaTemporariaDao
    abstract fun registroAcessoDao(): RegistroAcessoDao

    companion object {
        fun criar(contexto: Context): AppDatabase =
            Room.databaseBuilder(contexto.applicationContext, AppDatabase::class.java, "smartpark.db").build()
    }
}
