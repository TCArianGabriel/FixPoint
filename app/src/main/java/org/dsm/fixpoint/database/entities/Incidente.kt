package org.dsm.fixpoint.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Incidente",
    foreignKeys = [ForeignKey(
        entity = Usuario::class,
        parentColumns = ["Codigo"],
        childColumns = ["codigotecnico"],
        onDelete = ForeignKey.SET_NULL // O CASCADE, RESTRICT, etc., según tu lógica
    )]
)
data class Incidente(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "codigo") val codigo: Int = 0, // Auto-generado
    @ColumnInfo(name = "nombreUsuario") val nombreUsuario: String,
    @ColumnInfo(name = "area_de_usuario") val areaDeUsuario: String,
    @ColumnInfo(name = "descripcion") val descripcion: String,
    @ColumnInfo(name = "estado") val estado: String, // "Sin atender", "Asignado", "Pendiente", "Solucionado"
    @ColumnInfo(name = "codigotecnico", index = true) val codigoTecnico: Int?, // Puede ser nulo
    @ColumnInfo(name = "codigoEquipo") val codigoEquipo: String // Added new field for equipment code
)