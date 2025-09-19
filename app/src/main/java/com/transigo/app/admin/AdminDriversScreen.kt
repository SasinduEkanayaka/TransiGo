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

// Compose/UI imports
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.transigo.app.core.ui.theme.*

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
                                driver.phoneNumber.contains(currentState.searchQuery) ||
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
                                driver.phoneNumber.contains(query) ||
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
fun AdminDriversScreenContent(
    modifier: Modifier = Modifier,
    viewModel: DriverViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            // Handle error - could show snackbar
        }
    }

    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
            MaterialTheme.colorScheme.secondary.copy(alpha = 0.05f),
            MaterialTheme.colorScheme.surface
        )
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundGradient)
            .padding(20.dp)
    ) {
        // Header
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Drivers Management",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                ExtendedFloatingActionButton(onClick = { viewModel.showAddDriverDialog() }) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Add Driver")
                }
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
            ) { CircularProgressIndicator() }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
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

// Wrapper matching NavGraph signature
@Composable
fun AdminDriversScreen(navController: NavController) {
    AdminDriversScreenContent()
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
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
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
                        text = "Phone: ${driver.phoneNumber}",
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DriverDialog(
    driver: Driver?,
    onDismiss: () -> Unit,
    onConfirm: (Driver) -> Unit,
    modifier: Modifier = Modifier
) {
    var fullName by remember { mutableStateOf(driver?.fullName ?: "") }
    var phone by remember { mutableStateOf(driver?.phoneNumber ?: "") }
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
                        phoneNumber = phone,
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

// End of file
