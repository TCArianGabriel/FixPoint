package org.dsm.fixpoint.ui.viewmodel.userVM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.dsm.fixpoint.model.Incident

class IncidentStatusViewModel : ViewModel() {

    private val _incidentStatusList = MutableStateFlow<List<Incident>>(emptyList())
    val incidentStatusList: StateFlow<List<Incident>> = _incidentStatusList

    init {
        // In a real application, you would fetch all incidents related to the current user
        // (or all incidents if this is for an admin view) from a repository/API here.
        // For demonstration, we'll populate with dummy data including various statuses.
        loadIncidentStatuses()
    }

    private fun loadIncidentStatuses() {
        viewModelScope.launch {
            // Simulate network call or database query for incident statuses
            val dummyIncidents = listOf(
                Incident("INC001", "Usuario A", "Ventas", "No funciona mouse.", /*"Sin atender"*/),
                Incident("INC002", "Usuario B", "Marketing", "Falla de red.", /*"Asignado"*/),
                Incident("INC003", "Usuario C", "Soporte", "Error de software.", /*"Pendiente"*/),
                Incident(
                    "INC004",
                    "Usuario D",
                    "Diseño",
                    "Monitor no enciende.", /*"Solucionado"*/
                ),
                Incident(
                    "INC005",
                    "Usuario E",
                    "Contabilidad",
                    "Problema con impresora.", /*"Sin atender"*/
                ),
                Incident(
                    "INC006",
                    "Usuario F",
                    "Recursos Humanos",
                    "Configuración de email.", /*"Asignado"*/
                ),
                Incident(
                    "INC007",
                    "Usuario G",
                    "Finanzas",
                    "Acceso a sistema lento.", /*"Pendiente"*/
                ),
                Incident("INC008", "Usuario H", "IT", "Fallo de servidor.", /*"Solucionado"*/),
                Incident(
                    "INC009",
                    "Usuario I",
                    "Legal",
                    "Error en aplicación web.", /*"Pendiente"*/
                ),
                Incident(
                    "INC010",
                    "Usuario J",
                    "Logística",
                    "Problema con escáner.", /*"Solucionado"*/
                )
            )
            _incidentStatusList.value = dummyIncidents
        }
    }

    // This screen is for displaying status, so typically no click actions on individual cards
    // If you wanted to view incident details on click, you'd add a function here and pass it down.
}