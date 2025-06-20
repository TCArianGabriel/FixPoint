package org.dsm.fixpoint.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.dsm.fixpoint.database.dao.IncidenteDao
import org.dsm.fixpoint.database.dao.UsuarioDao
import org.dsm.fixpoint.database.entities.Incidente
import org.dsm.fixpoint.database.entities.Usuario

@Database(entities = [Usuario::class, Incidente::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao
    abstract fun incidenteDao(): IncidenteDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "incident_management_db" // Nombre de tu archivo de base de datos
                )
                    .addCallback(AppDatabaseCallback(context)) // Opcional: para pre-poblar o ejecutar migraciones
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class AppDatabaseCallback(
        private val context: Context // Necesitas el contexto para los recursos si pre-poblas
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            // Lógica para pre-poblar la base de datos la primera vez que se crea
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    val usuarioDao = database.usuarioDao()
                    // Ejemplo de pre-población de usuarios
                    usuarioDao.insertUsuario(Usuario(usuario = "jefe1", nombre = "Jefe Area 1", contrasena = "123", tipo = "jefe"))
                    usuarioDao.insertUsuario(Usuario(usuario = "tec1", nombre = "Tecnico Uno", contrasena = "123", tipo = "tecnico"))
                    usuarioDao.insertUsuario(Usuario(usuario = "tec2", nombre = "Tecnico Dos", contrasena = "123", tipo = "tecnico"))
                    usuarioDao.insertUsuario(Usuario(usuario = "user1", nombre = "Usuario General", contrasena = "123", tipo = "comun"))
                    val incidenteDao = database.incidenteDao()
                    // Add initial incidents here
                    val incidente1 = Incidente(
                        nombreUsuario = "Usuario1",
                        areaDeUsuario = "Area1",
                        descripcion = "Descripción de la incidencia 1",
                        estado = "Sin atender",
                        codigoTecnico = null,
                        codigoEquipo = "Equipo1"
                    )
                    val incidente2 = Incidente(
                        nombreUsuario = "Usuario2",
                        areaDeUsuario = "Area2",
                        descripcion = "Descripción de la incidencia 2",
                        estado = "Sin atender",
                        codigoTecnico = null,
                        codigoEquipo = "Equipo2"
                    )
                    incidenteDao.insertIncidente(incidente1)
                    incidenteDao.insertIncidente(incidente2)

                }
            }
        }
    }
}