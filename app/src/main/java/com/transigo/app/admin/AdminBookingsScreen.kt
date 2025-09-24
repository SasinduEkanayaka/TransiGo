package com.transigo.app.admin

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.transigo.app.data.model.Booking
import com.transigo.app.data.model.BookingStatus
import com.transigo.app.data.model.Driver
import com.transigo.app.core.ui.theme.*
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
    val showingAllBookings by viewModel.showingAllBookings.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val actionInProgress by viewModel.actionInProgress.collectAsState()
    val activeDrivers by viewModel.activeDrivers.collectAsState()

    var showDriverDialog by remember { mutableStateOf(false) }
    var selectedBookingForDriver by remember { mutableStateOf<Booking?>(null) }

    // Initialize data loading
    LaunchedEffect(Unit) {
        viewModel.loadAllBookings()
        viewModel.loadActiveDrivers()
    }

    // Simple clean background
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Simple Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
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
                text = "Booking Management",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
            
            IconButton(
                onClick = { 
                    if (showingAllBookings) {
                        viewModel.loadAllBookings()
                    } else {
                        viewModel.loadBookings()
                    }
                }
            ) {
                Icon(
                    Icons.Default.Refresh,
                    contentDescription = "Refresh",
                    tint = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Simple Filter Tabs
        SimpleFilterTabs(
            showingAllBookings = showingAllBookings,
            currentFilter = currentFilter,
            onFilterChanged = { filter ->
                if (filter == null) {
                    viewModel.loadAllBookings()
                } else {
                    viewModel.setFilter(filter)
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Content with better error handling
        when {
            isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Loading bookings...", color = Color.Gray)
                    }
                }
            }
            error != null -> {
                SimpleErrorContent(
                    error = error!!,
                    onRetry = { 
                        viewModel.clearError()
                        if (showingAllBookings) {
                            viewModel.loadAllBookings()
                        } else {
                            viewModel.loadBookings()
                        }
                    },
                    onCreateSample = { viewModel.createSampleData() }
                )
            }
            bookings.isEmpty() -> {
                SimpleEmptyState(
                    onCreateSampleData = { viewModel.createSampleData() },
                    onRefresh = { viewModel.loadAllBookings() }
                )
            }
            else -> {
                SimpleBookingsList(
                    bookings = bookings,
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
fun SimpleFilterTabs(
    showingAllBookings: Boolean,
    currentFilter: BookingStatus,
    onFilterChanged: (BookingStatus?) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // All Bookings Tab
        FilterTab(
            text = "All",
            isSelected = showingAllBookings,
            onClick = { onFilterChanged(null) },
            modifier = Modifier.weight(1f)
        )
        
        // Requested Tab
        FilterTab(
            text = "Requested",
            isSelected = !showingAllBookings && currentFilter == BookingStatus.REQUESTED,
            onClick = { onFilterChanged(BookingStatus.REQUESTED) },
            modifier = Modifier.weight(1f)
        )
        
        // Approved Tab
        FilterTab(
            text = "Approved",
            isSelected = !showingAllBookings && currentFilter == BookingStatus.APPROVED,
            onClick = { onFilterChanged(BookingStatus.APPROVED) },
            modifier = Modifier.weight(1f)
        )
        
        // Completed Tab
        FilterTab(
            text = "Completed",
            isSelected = !showingAllBookings && currentFilter == BookingStatus.COMPLETED,
            onClick = { onFilterChanged(BookingStatus.COMPLETED) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun FilterTab(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
            contentColor = if (isSelected) Color.White else Color.Black
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = if (isSelected) 2.dp else 0.dp
        ),
        border = if (!isSelected) BorderStroke(1.dp, Color.Gray.copy(alpha = 0.3f)) else null
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}

@Composable
fun SimpleBookingsList(
    bookings: List<Booking>,
    actionInProgress: String?,
    viewModel: AdminBookingViewModel,
    onAssignDriver: (Booking) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(bookings) { booking ->
            SimpleBookingCard(
                booking = booking,
                actionInProgress = actionInProgress,
                viewModel = viewModel,
                onAssignDriver = onAssignDriver
            )
        }
    }
}

@Composable
fun SimpleErrorContent(
    error: String,
    onRetry: () -> Unit,
    onCreateSample: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Default.Error,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.error
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Error Loading Bookings",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = error,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(Icons.Default.Refresh, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Retry")
            }
            
            OutlinedButton(
                onClick = onCreateSample
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add Sample")
            }
        }
    }
}

@Composable
fun SimpleEmptyState(
    onCreateSampleData: () -> Unit,
    onRefresh: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Default.BookmarkBorder,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = Color.Gray
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "No Bookings Found",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "There are no bookings to display at the moment.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = onRefresh,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(Icons.Default.Refresh, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Refresh")
            }
            
            OutlinedButton(
                onClick = onCreateSampleData
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add Sample Data")
            }
        }
    }
}

@Composable
fun SimpleBookingCard(
    booking: Booking,
    actionInProgress: String?,
    viewModel: AdminBookingViewModel,
    onAssignDriver: (Booking) -> Unit
) {
    val isActionLoading = actionInProgress == booking.id
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.2f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header with status and date
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SimpleStatusChip(status = booking.status)
                
                Text(
                    text = formatBookingDate(booking.requestedAt),
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Route information
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
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
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // User info
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = viewModel.getUserEmail(booking.userId),
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
            
            // Fare
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.AttachMoney,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "LKR ${booking.fare ?: "200"}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            }
            
            // Driver info (if assigned)
            if (booking.status == BookingStatus.APPROVED && booking.driverId != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.DirectionsCar,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Driver: ${viewModel.getDriverName(booking.driverId)}",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
            
            // Action buttons
            if (booking.status == BookingStatus.REQUESTED) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { onAssignDriver(booking) },
                        enabled = !isActionLoading,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        if (isActionLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                color = Color.White
                            )
                        } else {
                            Text("Approve", fontSize = 14.sp)
                        }
                    }
                    
                    OutlinedButton(
                        onClick = { viewModel.rejectBooking(booking.id) },
                        enabled = !isActionLoading,
                        modifier = Modifier.weight(1f)
                    ) {
                        if (isActionLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                color = MaterialTheme.colorScheme.primary
                            )
                        } else {
                            Text("Reject", fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SimpleStatusChip(status: BookingStatus) {
    val (backgroundColor, textColor, text) = when (status) {
        BookingStatus.REQUESTED -> Triple(Color(0xFFFFF3E0), Color(0xFFE65100), "Requested")
        BookingStatus.APPROVED -> Triple(Color(0xFFE8F5E8), Color(0xFF2E7D32), "Approved")
        BookingStatus.CONFIRMED -> Triple(Color(0xFFE8F5E8), Color(0xFF2E7D32), "Confirmed")
        BookingStatus.IN_PROGRESS -> Triple(Color(0xFFFFF3E0), Color(0xFFE65100), "In Progress")
        BookingStatus.COMPLETED -> Triple(Color(0xFFE3F2FD), Color(0xFF1565C0), "Completed")
        BookingStatus.REJECTED -> Triple(Color(0xFFFFEBEE), Color(0xFFC62828), "Rejected")
        BookingStatus.CANCELLED -> Triple(Color(0xFFFFEBEE), Color(0xFFC62828), "Cancelled")
    }
    
    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
        )
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
fun EmptyStateContent(filter: BookingStatus, onCreateSampleData: () -> Unit) {
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
                text = "No bookings found",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Bookings will appear here when users make requests",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = onCreateSampleData,
                colors = ButtonDefaults.buttonColors(containerColor = Primary)
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add Test Data")
            }
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

private fun formatBookingDate(timestamp: com.google.firebase.Timestamp?): String {
    return timestamp?.let {
        val date = Date(it.seconds * 1000)
        SimpleDateFormat("d MMMM yyyy", Locale.getDefault()).format(date)
    } ?: "9 April 2025"
}
