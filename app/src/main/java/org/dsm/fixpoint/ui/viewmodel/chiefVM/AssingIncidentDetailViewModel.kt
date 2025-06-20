package org.dsm.fixpoint.ui.viewmodel.chiefVM

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.dsm.fixpoint.database.AppDatabase
import org.dsm.fixpoint.database.entities.Incidente
import org.dsm.fixpoint.database.entities.Usuario // Import Usuario entity

class AssignIncidentDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val _technicianCode = MutableStateFlow("")
    val technicianCode: StateFlow<String> = _technicianCode.asStateFlow()

    private val _technicianName = MutableStateFlow("")
    val technicianName: StateFlow<String> = _technicianName.asStateFlow()

    private val _incidentCode = MutableStateFlow("")
    val incidentCode: StateFlow<String> = _incidentCode.asStateFlow()

    private val _assignButtonEnabled = MutableStateFlow(false)
    val assignButtonEnabled: StateFlow<Boolean> = _assignButtonEnabled.asStateFlow()

    // New StateFlow for list of technicians
    private val _technicians = MutableStateFlow<List<Usuario>>(emptyList())
    val technicians: StateFlow<List<Usuario>> = _technicians.asStateFlow()

    // New StateFlow to indicate assignment success (for navigation)
    private val _assignmentSuccess = MutableStateFlow(false)
    val assignmentSuccess: StateFlow<Boolean> = _assignmentSuccess.asStateFlow()

    // Database DAOs
    private val incidenteDao = AppDatabase.getDatabase(application).incidenteDao()
    private val usuarioDao = AppDatabase.getDatabase(application).usuarioDao()

    init {
        // Observe changes in all fields to enable/disable the assign button
        viewModelScope.launch {
            combine(
                _technicianCode,
                _technicianName,
                _incidentCode
            ) { techCode, techName, incCode ->
                techCode.isNotBlank() && techName.isNotBlank() && incCode.isNotBlank() && incCode != "0" // Ensure incidentCode is not "0"
            }.collect { isEnabled ->
                _assignButtonEnabled.value = isEnabled
            }
        }
        loadTechnicians() // Load technicians when ViewModel is created
    }

    // Function to load only users of type "tecnico"
    private fun loadTechnicians() {
        viewModelScope.launch {
            try {
                usuarioDao.getUsersByType("tecnico").collect { techList ->
                    _technicians.value = techList
                }
            } catch (e: Exception) {
                println("Error loading technicians: ${e.localizedMessage}")
                _technicians.value = emptyList()
            }
        }
    }

    fun onTechnicianCodeChange(newCode: String) {
        _technicianCode.value = newCode
        // Auto-complete technician name when code changes
        val selectedTechnician = _technicians.value.find { it.codigo.toString() == newCode }
        _technicianName.value = selectedTechnician?.nombre ?: ""
    }

    fun onTechnicianNameChange(newName: String) {
        // This function might be less used now if name is auto-completed
        _technicianName.value = newName
    }

    // This is called from the NavGraph to set the incident code received
    fun setIncidentCode(code: String?) {
        _incidentCode.value = code ?: ""
    }


    fun onAssignClick() {
        // Here you would implement your logic to assign the incident to the technician:
        _assignmentSuccess.value = false // Reset success flag

        val currentIncidentCode = _incidentCode.value.toIntOrNull()
        val currentTechnicianCode = _technicianCode.value.toIntOrNull()

        if (currentIncidentCode == null || currentTechnicianCode == null) {
            println("Assignment failed: Invalid incident or technician code.")
            return
        }

        viewModelScope.launch {
            try {
                // Fetch the incident to update it
                val incidentToUpdate = incidenteDao.getIncidentById(currentIncidentCode)

                if (incidentToUpdate != null) {
                    val updatedIncident = incidentToUpdate.copy(
                        estado = "Asignado", // Change status to "Asignado"
                        codigoTecnico = currentTechnicianCode // Assign the technician code
                    )
                    incidenteDao.updateIncidente(updatedIncident)
                    println("Incident $currentIncidentCode assigned to technician $currentTechnicianCode successfully!")
                    _assignmentSuccess.value = true // Indicate success
                    clearFields() // Clear fields after successful assignment
                } else {
                    println("Incident with code $currentIncidentCode not found.")
                }
            } catch (e: Exception) {
                println("Error assigning incident: ${e.localizedMessage}")
                e.printStackTrace()
            }
        }
    }

    private fun clearFields() {
        _technicianCode.value = ""
        _technicianName.value = ""
        // _incidentCode.value = "" // Don't clear incident code if staying on the same detail screen
        // or if it's set once from navigation args.
    }

    fun resetAssignmentSuccess() {
        _assignmentSuccess.value = false
    }
}