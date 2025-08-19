package com.transigo.app.booking

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.firebase.Timestamp
import com.transigo.app.auth.AuthViewModel
import com.transigo.app.core.navigation.NavigationRoutes
import com.transigo.app.data.model.BookingType
import com.transigo.app.data.model.RideType
import com.transigo.app.R
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingFormScreen(
    navController: NavController,
    bookingViewModel: BookingViewModel = viewModel(),
    authViewModel: AuthViewModel = viewModel()
) {
    val state by bookingViewModel.state.collectAsState()
    val user by authViewModel.user.collectAsState()
    
    // Handle successful booking creation
    LaunchedEffect(state.isCreated) {
        if (state.isCreated) {
            navController.navigate(NavigationRoutes.BOOKING_HISTORY) {
                popUpTo(NavigationRoutes.BOOKING_FORM) { inclusive = true }
            }
            bookingViewModel.resetForm()
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
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
                text = "Book a Ride",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        // Error message
        state.error?.let { error ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
            ) {
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        // Booking form
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Service Type Selection
                Text(
                    text = "Service Type",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectableGroup(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    BookingType.values().forEach { type ->
                        Row(
                            modifier = Modifier
                                .selectable(
                                    selected = (state.type == type),
                                    onClick = { bookingViewModel.updateType(type) },
                                    role = Role.RadioButton
                                )
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (state.type == type),
                                onClick = null
                            )
                            Text(
                                text = type.name.replace("_", " "),
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
                
                Divider()
                
                // Pickup Location with improved accessibility and custom icon
                OutlinedTextField(
                    value = state.pickupName,
                    onValueChange = bookingViewModel::updatePickupName,
                    label = { Text("Pickup Location") },
                    placeholder = { Text("Enter pickup address") },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_pickup_location),
                            contentDescription = "Pickup location icon",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 48.dp), // Minimum touch target
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium
                )
                
                // Drop Location with improved accessibility and custom icon
                OutlinedTextField(
                    value = state.dropName,
                    onValueChange = bookingViewModel::updateDropName,
                    label = { Text("Drop Location") },
                    placeholder = { Text("Enter destination address") },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_dropoff_location),
                            contentDescription = "Drop-off location icon", 
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 48.dp), // Minimum touch target
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium
                )
                
                Divider()
                
                // Ride Type Selection
                Text(
                    text = "Ride Type",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Column(
                    modifier = Modifier.selectableGroup(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    RideType.values().forEach { rideType ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = (state.rideType == rideType),
                                    onClick = { bookingViewModel.updateRideType(rideType) },
                                    role = Role.RadioButton
                                )
                                .padding(horizontal = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (state.rideType == rideType),
                                onClick = null
                            )
                            Column(modifier = Modifier.padding(start = 8.dp)) {
                                Text(
                                    text = rideType.name,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = when (rideType) {
                                        RideType.STANDARD -> "Economy ride for up to 4 passengers"
                                        RideType.VAN -> "Spacious van for up to 8 passengers"
                                        RideType.LUX -> "Premium luxury vehicle"
                                    },
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
                
                Divider()
                
                // Optional Scheduling (simplified for now)
                Text(
                    text = "Schedule (Optional)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                OutlinedButton(
                    onClick = {
                        // TODO: Implement date/time picker
                        // For now, just set to 1 hour from now
                        val calendar = Calendar.getInstance()
                        calendar.add(Calendar.HOUR_OF_DAY, 1)
                        bookingViewModel.updateScheduledAt(Timestamp(calendar.time))
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.DateRange, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        if (state.scheduledAt != null) {
                            "Scheduled: ${SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault()).format(state.scheduledAt!!.toDate())}"
                        } else {
                            "Schedule for later"
                        }
                    )
                }
                
                if (state.scheduledAt != null) {
                    TextButton(
                        onClick = { bookingViewModel.updateScheduledAt(null) },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text("Clear Schedule")
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Submit button
        Button(
            onClick = {
                user?.id?.let { userId ->
                    bookingViewModel.createBooking(userId)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = !state.isLoading && user != null
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Book Now", fontSize = 16.sp)
            }
        }
    }
}
