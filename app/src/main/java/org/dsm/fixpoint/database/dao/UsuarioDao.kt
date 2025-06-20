package org.dsm.fixpoint.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.dsm.fixpoint.database.entities.Usuario

@Dao
interface UsuarioDao {
    @Insert
    suspend fun insertUsuario(usuario: Usuario): Long // Retorna el id de la fila insertada

    @Update
    suspend fun updateUsuario(usuario: Usuario)

    @Query("SELECT * FROM Usuarios WHERE Usuario = :username AND Contrasena = :password")
    suspend fun login(username: String, password: String): Usuario?

    // In UsuarioDao.kt
    @Query("SELECT * FROM Usuarios WHERE Tipo = :userType")
    fun getUsersByType(userType: String): Flow<List<Usuario>>

    @Query("SELECT * FROM Usuarios WHERE Codigo = :codigo")
    suspend fun getUsuarioByCodigo(codigo: Int): Usuario?
}