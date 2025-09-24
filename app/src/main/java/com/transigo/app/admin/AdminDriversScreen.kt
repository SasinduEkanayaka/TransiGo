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
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*

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
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: DriverViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            // Handle error - could show snackbar
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            // Header with back button and title
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.navigateUp() }
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Text(
                    text = "Drivers",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            }

            // Section title
            Text(
                text = "Drivers",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Drivers List
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) { 
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary) 
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 80.dp) // Space for FAB
                ) {
                    items(uiState.filteredDrivers) { driver ->
                        ModernDriverCard(
                            driver = driver,
                            onEditClick = { viewModel.showEditDriverDialog(driver) },
                            onDeleteClick = { viewModel.deleteDriver(driver.id) }
                        )
                    }
                    
                    // Add empty space if no drivers
                    if (uiState.filteredDrivers.isEmpty()) {
                        item {
                            EmptyDriversState(
                                onAddClick = { viewModel.showAddDriverDialog() }
                            )
                        }
                    }
                }
            }
        }

        // Floating Action Button
        FloatingActionButton(
            onClick = { viewModel.showAddDriverDialog() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = Color(0xFF6B46C1), // Purple color matching design
            contentColor = Color.White
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = "Add Driver",
                modifier = Modifier.size(24.dp)
            )
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
    AdminDriversScreenContent(navController = navController)
}

@Composable
fun ModernDriverCard(
    driver: Driver,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Date (formatted from createdAt if available, or current date)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.DateRange,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = formatDriverDate(driver.createdAt),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Driver Name
            Text(
                text = driver.fullName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Vehicle Info
            Text(
                text = "Vehicle - ${driver.vehicleType.name.lowercase().replaceFirstChar { it.uppercase() }}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            // Vehicle ID/Number
            Text(
                text = "${getVehiclePrefix(driver.vehicleType)} - ${driver.vehicleNumber}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Vehicle ID formatted
            Text(
                text = "VehID-${generateVehicleId(driver.id)}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun EmptyDriversState(
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Default.Person,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = Color.Gray
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "No drivers found",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Gray
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Add your first driver to get started",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Button(
            onClick = onAddClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6B46C1)
            )
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Add Driver")
        }
    }
}

// Helper functions
private fun formatDriverDate(timestamp: Timestamp?): String {
    val dateFormat = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
    return if (timestamp != null) {
        dateFormat.format(timestamp.toDate())
    } else {
        dateFormat.format(Date())
    }
}

private fun getVehiclePrefix(vehicleType: VehicleType): String {
    return when (vehicleType) {
        VehicleType.CAR -> "CAA"
        VehicleType.VAN -> "VAA" 
        VehicleType.BUS -> "BUS"
    }
}

private fun generateVehicleId(driverId: String): String {
    // Generate a simple 4-digit ID based on driver ID hash
    val hash = driverId.hashCode()
    return String.format("%04d", kotlin.math.abs(hash % 10000))
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
