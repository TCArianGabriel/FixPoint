package org.dsm.fixpoint.ui.userUI

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.dsm.fixpoint.model.Incident
import org.dsm.fixpoint.ui.theme.FixPointTheme
import org.dsm.fixpoint.ui.viewmodel.userVM.IncidentStatusViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncidentStatusScreen(
    incidentStatusViewModel: IncidentStatusViewModel = viewModel(),
    onBackClick: () -> Unit = {} // Lambda for back button navigation
) {
    val incidents by incidentStatusViewModel.incidentStatusList.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Estado de Incidencias", color = Color.White) }, // Screen title
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF3F51B5)) // Deep blue for AppBar
            )
        },
        containerColor = Color(0xFFEEEEEE) // Light gray background for the content area
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp), // Space between incident cards
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(incidents.chunked(2)) { rowIncidents -> // Chunk incidents into pairs for two columns
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround // Distribute cards evenly
                ) {
                    rowIncidents.forEach { incident ->
                        IncidentStatusCard( // Specific card for status display
                            incident = incident,
                            modifier = Modifier.weight(1f) // Each card takes equal weight in the row
                        )
                    }
                    // If there's an odd number of incidents, add an empty space for alignment
                    if (rowIncidents.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
fun IncidentStatusCard(
    incident: Incident,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(4.dp) // Smaller padding inside the row
            .border(
                BorderStroke(2.dp, Color.Gray), // Solid gray border to match visual
                MaterialTheme.shapes.small
            ),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Código de incidencia: ${incident.id}",
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Nombre de usuario: ${incident.username}",
                fontSize = 12.sp,
                color = Color.DarkGray,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Area del usuario: ${incident.userArea}",
                fontSize = 12.sp,
                color = Color.DarkGray,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Descripción de la incidencia: ${incident.description}",
                fontSize = 12.sp,
                color = Color.DarkGray,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            /*
            Text(
                text = "Estado: ${incident.status}", // Displaying the status
                fontSize = 12.sp,
                color = Color.DarkGray,
                modifier = Modifier.padding(bottom = 4.dp)
            )
             */
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewIncidentStatusScreen() {
    FixPointTheme {
        IncidentStatusScreen()
    }
}