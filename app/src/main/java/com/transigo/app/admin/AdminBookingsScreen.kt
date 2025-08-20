package com.transigo.app.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.transigo.app.data.model.Booking
import com.transigo.app.data.model.BookingStatus
import com.transigo.app.data.model.Driver
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminBookingsScreen(
    navController: NavController,
    viewModel: AdminBookingViewModel = hiltViewModel()
) {
    val bookings by viewModel.bookings.collectAsState()
    val currentFilter by viewModel.currentFilter.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val actionInProgress by viewModel.actionInProgress.collectAsState()
    val activeDrivers by viewModel.activeDrivers.collectAsState()

    var showDriverDialog by remember { mutableStateOf(false) }
    var selectedBookingForDriver by remember { mutableStateOf<Booking?>(null) }

    Column(modifier = Modifier.fillMaxSize()) {
        // Top App Bar
        CenterAlignedTopAppBar(
            title = { Text("Manage Bookings") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        )

        // Filter Tabs
        FilterTabs(
            currentFilter = currentFilter,
            onFilterChanged = viewModel::setFilter
        )

        // Content
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                error != null -> {
                    error?.let { errMsg ->
                        ErrorContent(
                            error = errMsg,
                            onRetry = viewModel::loadBookings,
                            onDismiss = viewModel::clearError
                        )
                    }
                }
                bookings.isEmpty() -> {
                    EmptyStateContent(currentFilter)
                }
                else -> {
                    BookingsList(
                        bookings = bookings,
                        currentFilter = currentFilter,
                        actionInProgress = actionInProgress,
                        viewModel = viewModel,
                        onAssignDriver = { booking ->
                            selectedBookingForDriver = booking
                            showDriverDialog = true
                        }
                    )
                }
            }
        }
    }

    // Driver Selection Dialog
    if (showDriverDialog && selectedBookingForDriver != null) {
        DriverSelectionDialog(
            booking = selectedBookingForDriver!!,
            drivers = activeDrivers,
            onDriverSelected = { booking, driverId ->
                viewModel.approveBooking(booking.id, driverId)
                showDriverDialog = false
                selectedBookingForDriver = null
            },
            onDismiss = {
                showDriverDialog = false
                selectedBookingForDriver = null
            }
        )
    }
}

@Composable
fun FilterTabs(
    currentFilter: BookingStatus,
    onFilterChanged: (BookingStatus) -> Unit
) {
    val filters = listOf(
        BookingStatus.REQUESTED,
        BookingStatus.APPROVED,
        BookingStatus.COMPLETED
    )

    ScrollableTabRow(
        selectedTabIndex = filters.indexOf(currentFilter),
        modifier = Modifier.fillMaxWidth()
    ) {
        filters.forEach { filter ->
            Tab(
                selected = currentFilter == filter,
                onClick = { onFilterChanged(filter) },
                text = { 
                    Text(
                        text = filter.name,
                        fontWeight = if (currentFilter == filter) FontWeight.Bold else FontWeight.Normal
                    )
                }
            )
        }
    }
}

@Composable
fun BookingsList(
    bookings: List<Booking>,
    currentFilter: BookingStatus,
    actionInProgress: String?,
    viewModel: AdminBookingViewModel,
    onAssignDriver: (Booking) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(bookings) { booking ->
            BookingCard(
                booking = booking,
                currentFilter = currentFilter,
                actionInProgress = actionInProgress,
                viewModel = viewModel,
                onAssignDriver = onAssignDriver
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingCard(
    booking: Booking,
    currentFilter: BookingStatus,
    actionInProgress: String?,
    viewModel: AdminBookingViewModel,
    onAssignDriver: (Booking) -> Unit
) {
    val isActionLoading = actionInProgress == booking.id
    val userEmail = viewModel.getUserEmail(booking.userId)
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header with status and timestamp
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatusChip(booking.status)
                Text(
                    text = formatTimestamp(booking.requestedAt),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // User email
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = userEmail,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Route information
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.LocationOn,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${booking.pickupName} → ${booking.dropName}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Ride type
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.DirectionsCar,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Ride Type: ${booking.rideType.name}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            // Driver assignment (for approved bookings)
            if (booking.status == BookingStatus.APPROVED && booking.driverId != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Badge,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Driver: ${viewModel.getDriverName(booking.driverId)}",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Action buttons
            BookingActions(
                booking = booking,
                currentFilter = currentFilter,
                isLoading = isActionLoading,
                viewModel = viewModel,
                onAssignDriver = onAssignDriver
            )
        }
    }
}

@Composable
fun StatusChip(status: BookingStatus) {
    val (backgroundColor, textColor) = when (status) {
        BookingStatus.REQUESTED -> MaterialTheme.colorScheme.primaryContainer to MaterialTheme.colorScheme.onPrimaryContainer
        BookingStatus.APPROVED -> MaterialTheme.colorScheme.secondaryContainer to MaterialTheme.colorScheme.onSecondaryContainer
        BookingStatus.COMPLETED -> MaterialTheme.colorScheme.tertiaryContainer to MaterialTheme.colorScheme.onTertiaryContainer
        BookingStatus.REJECTED -> MaterialTheme.colorScheme.errorContainer to MaterialTheme.colorScheme.onErrorContainer
        BookingStatus.CANCELLED -> MaterialTheme.colorScheme.errorContainer to MaterialTheme.colorScheme.onErrorContainer
        else -> MaterialTheme.colorScheme.surfaceVariant to MaterialTheme.colorScheme.onSurfaceVariant
    }
    
    Surface(
        modifier = Modifier.background(backgroundColor, MaterialTheme.shapes.small),
        shape = MaterialTheme.shapes.small,
        color = backgroundColor
    ) {
        Text(
            text = status.name,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = textColor
        )
    }
}

@Composable
fun BookingActions(
    booking: Booking,
    currentFilter: BookingStatus,
    isLoading: Boolean,
    viewModel: AdminBookingViewModel,
    onAssignDriver: (Booking) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        when (currentFilter) {
            BookingStatus.REQUESTED -> {
                // Approve and Reject buttons
                OutlinedButton(
                    onClick = { onAssignDriver(booking) },
                    enabled = !isLoading,
                    modifier = Modifier.weight(1f)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(16.dp))
                    } else {
                        Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Approve")
                    }
                }
                
                Button(
                    onClick = { viewModel.rejectBooking(booking.id) },
                    enabled = !isLoading,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(16.dp))
                    } else {
                        Icon(Icons.Default.Close, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Reject")
                    }
                }
            }
            
            BookingStatus.APPROVED -> {
                // Complete button
                OutlinedButton(
                    onClick = { viewModel.completeBooking(booking.id) },
                    enabled = !isLoading,
                    modifier = Modifier.weight(1f)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(16.dp))
                    } else {
                        Icon(Icons.Default.Done, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Complete")
                    }
                }
            }
            
            else -> {
                // For completed bookings, no primary action needed
            }
        }
        
        // Cancel button (available for all statuses except completed/cancelled)
        if (booking.status !in listOf(BookingStatus.COMPLETED, BookingStatus.CANCELLED, BookingStatus.REJECTED)) {
            Button(
                onClick = { viewModel.cancelBooking(booking.id) },
                enabled = !isLoading,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(16.dp))
                } else {
                    Icon(Icons.Default.Cancel, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Cancel")
                }
            }
        }
    }
}

@Composable
fun DriverSelectionDialog(
    booking: Booking,
    drivers: List<Driver>,
    onDriverSelected: (Booking, String) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedDriverId by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Assign Driver") },
        text = {
            Column {
                Text("Select a driver for this booking:")
                Spacer(modifier = Modifier.height(16.dp))
                
                Column(modifier = Modifier.selectableGroup()) {
                    // Option to approve without driver
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        RadioButton(
                            selected = selectedDriverId == null,
                            onClick = { selectedDriverId = null }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Approve without driver assignment")
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Available drivers
                    drivers.forEach { driver ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            RadioButton(
                                selected = selectedDriverId == driver.id,
                                onClick = { selectedDriverId = driver.id }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(driver.fullName, fontWeight = FontWeight.Medium)
                                Text(
                                    text = "${driver.vehicleType.name} • Rating: ${String.format("%.1f", driver.rating)}",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDriverSelected(booking, selectedDriverId ?: "")
                }
            ) {
                Text("Approve")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun EmptyStateContent(filter: BookingStatus) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Default.BookOnline,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "No ${filter.name.lowercase()} bookings",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Bookings will appear here when available",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ErrorContent(
    error: String,
    onRetry: () -> Unit,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Default.Error,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Error loading bookings",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.error
            )
            Text(
                text = error,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                TextButton(onClick = onDismiss) {
                    Text("Dismiss")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = onRetry) {
                    Text("Retry")
                }
            }
        }
    }
}

private fun formatTimestamp(timestamp: com.google.firebase.Timestamp?): String {
    return timestamp?.let {
        val date = Date(it.seconds * 1000)
        SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault()).format(date)
    } ?: "Unknown"
}
