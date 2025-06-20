package org.dsm.fixpoint.ui.viewmodel.chiefVM

import android.app.Application // Import Application
import androidx.lifecycle.AndroidViewModel // Change ViewModel to AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.dsm.fixpoint.database.AppDatabase
import org.dsm.fixpoint.database.entities.Incidente // Import the Incidente entity

// Change ViewModel() to AndroidViewModel(application: Application)
class AssignIncidentsViewModel(application: Application) : AndroidViewModel(application) {

    // Incidents to be displayed (filtered by codigoTecnico = null)
    private val _incidents = MutableStateFlow<List<Incidente>>(emptyList())
    val incidents: StateFlow<List<Incidente>> = _incidents.asStateFlow()

    // Get an instance of your AppDatabase and then the IncidenteDao
    private val incidenteDao = AppDatabase.getDatabase(application).incidenteDao()

    init {
        loadUnassignedIncidents()
    }

    // Function to load incidents from the database
    fun loadUnassignedIncidents() {
        viewModelScope.launch {
            try {
                // Fetch all incidents and filter those where codigoTecnico is null
                incidenteDao.getAllIncidents().collect { allIncidents ->
                    val unassignedIncidents = allIncidents.filter { it.codigoTecnico == null }
                    _incidents.value = unassignedIncidents
                }
            } catch (e: Exception) {
                // Handle potential database errors (e.g., log, show error message)
                println("Error loading unassigned incidents: ${e.localizedMessage}")
                // Optionally, clear the list or show an error state
                _incidents.value = emptyList()
            }
        }
    }

    // This function will be called when an "Asignar" button is clicked for a specific incident.
    // It should only pass the incident's code (ID) for further processing.
    fun onAssignClick(incidentCode: Int) {
        // Here, you would typically navigate to another screen where the chief
        // can assign a technician to this specific incident.
        // This function just "sends" the incident's code.
        println("Asignar button clicked for Incident Code: $incidentCode")
        // No modification to the list here, as the assignment will happen on another screen
        // and then loadUnassignedIncidents() will refresh the list.
    }
}