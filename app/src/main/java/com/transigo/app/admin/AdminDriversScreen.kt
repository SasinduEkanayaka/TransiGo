// DriverViewModel - TODO: Move to separate file
package com.transigo.app.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.transigo.app.data.model.Driver
import com.transigo.app.data.model.VehicleType
import com.transigo.app.data.repository.DriverRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DriverUiState(
    val drivers: List<Driver> = emptyList(),
    val filteredDrivers: List<Driver> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val showAddEditDialog: Boolean = false,
    val editingDriver: Driver? = null
)

@HiltViewModel
class DriverViewModel @Inject constructor(
    private val driverRepository: DriverRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DriverUiState())
    val uiState: StateFlow<DriverUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")

    init {
        observeDrivers()
        observeSearchQuery()
    }

    private fun observeDrivers() {
        viewModelScope.launch {
            driverRepository.getDriversFlow()
                .catch { error ->
                    _uiState.update { it.copy(error = error.message, isLoading = false) }
                }
                .collect { drivers ->
                    _uiState.update { currentState ->
                        val filtered = if (currentState.searchQuery.isBlank()) {
                            drivers
                        } else {
                            drivers.filter { driver ->
                                driver.fullName.contains(currentState.searchQuery, ignoreCase = true) ||
                                driver.phone.contains(currentState.searchQuery) ||
                                driver.vehicleNumber.contains(currentState.searchQuery, ignoreCase = true)
                            }
                        }
                        currentState.copy(
                            drivers = drivers,
                            filteredDrivers = filtered,
                            isLoading = false
                        )
                    }
                }
        }
    }

    private fun observeSearchQuery() {
        viewModelScope.launch {
            _searchQuery
                .debounce(300)
                .distinctUntilChanged()
                .collect { query ->
                    _uiState.update { currentState ->
                        val filtered = if (query.isBlank()) {
                            currentState.drivers
                        } else {
                            currentState.drivers.filter { driver ->
                                driver.fullName.contains(query, ignoreCase = true) ||
                                driver.phone.contains(query) ||
                                driver.vehicleNumber.contains(query, ignoreCase = true)
                            }
                        }
                        currentState.copy(
                            searchQuery = query,
                            filteredDrivers = filtered
                        )
                    }
                }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun showAddDriverDialog() {
        _uiState.update { 
            it.copy(showAddEditDialog = true, editingDriver = null) 
        }
    }

    fun showEditDriverDialog(driver: Driver) {
        _uiState.update { 
            it.copy(showAddEditDialog = true, editingDriver = driver) 
        }
    }

    fun hideAddEditDialog() {
        _uiState.update { 
            it.copy(showAddEditDialog = false, editingDriver = null) 
        }
    }

    fun addDriver(driver: Driver) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            driverRepository.addDriver(driver)
                .onSuccess {
                    _uiState.update { 
                        it.copy(isLoading = false, showAddEditDialog = false) 
                    }
                }
                .onFailure { error ->
                    _uiState.update { 
                        it.copy(isLoading = false, error = error.message) 
                    }
                }
        }
    }

    fun updateDriver(driverId: String, driver: Driver) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            driverRepository.updateDriver(driverId, driver)
                .onSuccess {
                    _uiState.update { 
                        it.copy(isLoading = false, showAddEditDialog = false) 
                    }
                }
                .onFailure { error ->
                    _uiState.update { 
                        it.copy(isLoading = false, error = error.message) 
                    }
                }
        }
    }

    fun deleteDriver(driverId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            driverRepository.deleteDriver(driverId)
                .onSuccess {
                    _uiState.update { it.copy(isLoading = false) }
                }
                .onFailure { error ->
                    _uiState.update { 
                        it.copy(isLoading = false, error = error.message) 
                    }
                }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}

// UI Components for AdminDriversScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDriversScreen(
    modifier: Modifier = Modifier,
    viewModel: DriverViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            // Handle error - could show snackbar
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Drivers Management",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            
            FloatingActionButton(
                onClick = { viewModel.showAddDriverDialog() },
                modifier = Modifier.size(56.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Driver")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Search Bar
        OutlinedTextField(
            value = uiState.searchQuery,
            onValueChange = viewModel::updateSearchQuery,
            label = { Text("Search drivers...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Drivers List
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.filteredDrivers) { driver ->
                    DriverItem(
                        driver = driver,
                        onEditClick = { viewModel.showEditDriverDialog(driver) },
                        onDeleteClick = { viewModel.deleteDriver(driver.id) }
                    )
                }
            }
        }
    }

    // Add/Edit Dialog
    if (uiState.showAddEditDialog) {
        DriverDialog(
            driver = uiState.editingDriver,
            onDismiss = { viewModel.hideAddEditDialog() },
            onConfirm = { driver ->
                if (uiState.editingDriver != null) {
                    viewModel.updateDriver(uiState.editingDriver!!.id, driver)
                } else {
                    viewModel.addDriver(driver)
                }
            }
        )
    }
}

@Composable
fun DriverItem(
    driver: Driver,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = driver.fullName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Phone: ${driver.phone}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Vehicle: ${driver.vehicleType} - ${driver.vehicleNumber}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Row {
                    // Active/Inactive Badge
                    AssistChip(
                        onClick = { },
                        label = { Text(if (driver.isActive) "Active" else "Inactive") },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = if (driver.isActive) 
                                MaterialTheme.colorScheme.primaryContainer 
                            else 
                                MaterialTheme.colorScheme.errorContainer
                        )
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    // Actions
                    IconButton(onClick = onEditClick) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit Driver")
                    }
                    IconButton(onClick = onDeleteClick) {
                        Icon(
                            Icons.Default.Delete, 
                            contentDescription = "Delete Driver",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DriverDialog(
    driver: Driver?,
    onDismiss: () -> Unit,
    onConfirm: (Driver) -> Unit,
    modifier: Modifier = Modifier
) {
    var fullName by remember { mutableStateOf(driver?.fullName ?: "") }
    var phone by remember { mutableStateOf(driver?.phone ?: "") }
    var vehicleType by remember { mutableStateOf(driver?.vehicleType ?: VehicleType.CAR) }
    var vehicleNumber by remember { mutableStateOf(driver?.vehicleNumber ?: "") }
    var isActive by remember { mutableStateOf(driver?.isActive ?: true) }
    var showVehicleTypeMenu by remember { mutableStateOf(false) }

    val isEditing = driver != null
    val isFormValid = fullName.isNotBlank() && phone.isNotBlank() && vehicleNumber.isNotBlank()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { 
            Text(if (isEditing) "Edit Driver" else "Add New Driver") 
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    label = { Text("Full Name") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone Number") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    singleLine = true
                )

                // Vehicle Type Dropdown
                ExposedDropdownMenuBox(
                    expanded = showVehicleTypeMenu,
                    onExpandedChange = { showVehicleTypeMenu = it }
                ) {
                    OutlinedTextField(
                        value = vehicleType.toString(),
                        onValueChange = { },
                        readOnly = true,
                        label = { Text("Vehicle Type") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showVehicleTypeMenu) },
                        modifier = Modifier.fillMaxWidth().menuAnchor()
                    )
                    
                    ExposedDropdownMenu(
                        expanded = showVehicleTypeMenu,
                        onDismissRequest = { showVehicleTypeMenu = false }
                    ) {
                        VehicleType.values().forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type.toString()) },
                                onClick = {
                                    vehicleType = type
                                    showVehicleTypeMenu = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = vehicleNumber,
                    onValueChange = { vehicleNumber = it },
                    label = { Text("Vehicle Number") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isActive,
                        onCheckedChange = { isActive = it }
                    )
                    Text("Active Driver")
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val newDriver = Driver(
                        id = driver?.id ?: "",
                        fullName = fullName,
                        phone = phone,
                        vehicleType = vehicleType,
                        vehicleNumber = vehicleNumber,
                        isActive = isActive,
                        createdAt = driver?.createdAt,
                        updatedAt = driver?.updatedAt
                    )
                    onConfirm(newDriver)
                },
                enabled = isFormValid
            ) {
                Text(if (isEditing) "Update" else "Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

// Adding imports and UI components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.transigo.app.data.model.Driver
import com.transigo.app.data.model.VehicleType

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDriversScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top App Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = "Manage Drivers",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        // Driver management content
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.DirectionsCar,
                    contentDescription = "Drivers",
                    modifier = Modifier.size(80.dp),
                    tint = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Driver Management",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Manage driver registrations, approvals, and assignments.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
