package com.transigo.app.booking

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.transigo.app.auth.AuthViewModel
import com.transigo.app.core.navigation.NavigationRoutes
import com.transigo.app.data.model.*
import com.transigo.app.utils.DistanceUtils
import com.transigo.app.R
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun BookingFormScreen(
    navController: NavController,
    bookingViewModel: BookingViewModel = viewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val state by bookingViewModel.state.collectAsState()
    val user by authViewModel.user.collectAsState()
    val context = LocalContext.current
    
    // Permission handling
    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )
    
    // Initialize OSMDroid configuration
    LaunchedEffect(Unit) {
        Configuration.getInstance().userAgentValue = context.packageName
    }
    
    // Handle successful booking creation
    LaunchedEffect(state.isCreated) {
        if (state.isCreated) {
            navController.navigate(NavigationRoutes.BOOKING_HISTORY) {
                popUpTo(NavigationRoutes.BOOKING_FORM) { inclusive = true }
            }
            bookingViewModel.resetForm()
        }
    }
    
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<Date?>(null) }
    var selectedTime by remember { mutableStateOf<Pair<Int, Int>?>(null) }
    var showPaymentSelector by remember { mutableStateOf(false) }
    var showBookingSummary by remember { mutableStateOf(false) }
    var showLocationSearch by remember { mutableStateOf(false) }
    var isSearchingFromLocation by remember { mutableStateOf(true) }
    
    // Handle result from payment method screen - simplified approach
    LaunchedEffect(navController.currentBackStackEntry?.savedStateHandle) {
        navController.currentBackStackEntry?.savedStateHandle?.let { handle ->
            handle.get<PaymentMethod>("selected_payment_method")?.let { paymentMethod ->
                bookingViewModel.updatePaymentMethod(paymentMethod)
                handle.remove<PaymentMethod>("selected_payment_method")
            }
        }
    }
    
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Top App Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
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
            
            // Map at the top - Larger and prominent
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp), // Larger map at top
                factory = { mapContext ->
                    MapView(mapContext).apply {
                        setTileSource(TileSourceFactory.MAPNIK)
                        setBuiltInZoomControls(true)
                        setMultiTouchControls(true)
                        controller.setZoom(8.0)
                        
                        // Set map center to Sri Lanka (approximately Kandy)
                        controller.setCenter(GeoPoint(7.2906, 80.6337))
                        
                        // Set map bounds to Sri Lanka only
                        val sriLankaBounds = org.osmdroid.util.BoundingBox(
                            9.8, 81.9, // North, East
                            5.9, 79.6  // South, West
                        )
                        setScrollableAreaLimitDouble(sriLankaBounds)
                        
                        // Handle map clicks
                        overlays.add(object : org.osmdroid.views.overlay.Overlay() {
                            override fun onSingleTapConfirmed(
                                e: android.view.MotionEvent?,
                                mapView: MapView?
                            ): Boolean {
                                if (e != null && mapView != null) {
                                    val projection = mapView.projection
                                    val geoPoint = projection.fromPixels(e.x.toInt(), e.y.toInt()) as GeoPoint
                                    
                                    // Determine which field to update based on current selection
                                    if (state.fromLocation.latitude == 0.0) {
                                        bookingViewModel.updateFromLocation(
                                            Location(
                                                latitude = geoPoint.latitude,
                                                longitude = geoPoint.longitude,
                                                address = "Lat: ${String.format("%.4f", geoPoint.latitude)}, Lng: ${String.format("%.4f", geoPoint.longitude)}"
                                            )
                                        )
                                    } else {
                                        bookingViewModel.updateToLocation(
                                            Location(
                                                latitude = geoPoint.latitude,
                                                longitude = geoPoint.longitude,
                                                address = "Lat: ${String.format("%.4f", geoPoint.latitude)}, Lng: ${String.format("%.4f", geoPoint.longitude)}"
                                            )
                                        )
                                    }
                                    
                                    // Update markers and route
                                    updateMapMarkers(this@apply, state.fromLocation, state.toLocation)
                                    return true
                                }
                                return false
                            }
                        })
                    }
                }
            ) { mapView ->
                // Update markers when state changes
                updateMapMarkers(mapView, state.fromLocation, state.toLocation)
            }
            
            // Scrollable Form Section Below Map
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    // From/To Fields - Editable with manual search only
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Trip Details",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                OutlinedTextField(
                                    value = state.fromLocation.address,
                                    onValueChange = { address ->
                                        // Only update the address, don't auto-search while typing
                                        bookingViewModel.updateFromLocationAddress(address)
                                    },
                                    label = { Text("From") },
                                    leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color.Green) },
                                    modifier = Modifier.weight(1f),
                                    placeholder = { Text("Enter pickup location") },
                                    trailingIcon = {
                                        IconButton(onClick = {
                                            showLocationSearch = true
                                            isSearchingFromLocation = true
                                        }) {
                                            Icon(Icons.Default.Search, contentDescription = "Search from suggestions")
                                        }
                                    }
                                )
                                
                                // Set Location Button for From
                                OutlinedButton(
                                    onClick = {
                                        if (state.fromLocation.address.isNotBlank()) {
                                            setLocationWithCoordinates(state.fromLocation.address, true, bookingViewModel)
                                        }
                                    },
                                    modifier = Modifier.padding(top = 8.dp),
                                    enabled = state.fromLocation.address.isNotBlank()
                                ) {
                                    Text("Set", fontSize = 12.sp)
                                }
                            }
                            
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                OutlinedTextField(
                                    value = state.toLocation.address,
                                    onValueChange = { address ->
                                        // Only update the address, don't auto-search while typing
                                        bookingViewModel.updateToLocationAddress(address)
                                    },
                                    label = { Text("To") },
                                    leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color.Red) },
                                    modifier = Modifier.weight(1f),
                                    placeholder = { Text("Enter destination") },
                                    trailingIcon = {
                                        IconButton(onClick = {
                                            showLocationSearch = true
                                            isSearchingFromLocation = false
                                        }) {
                                            Icon(Icons.Default.Search, contentDescription = "Search from suggestions")
                                        }
                                    }
                                )
                                
                                // Set Location Button for To
                                OutlinedButton(
                                    onClick = {
                                        if (state.toLocation.address.isNotBlank()) {
                                            setLocationWithCoordinates(state.toLocation.address, false, bookingViewModel)
                                        }
                                    },
                                    modifier = Modifier.padding(top = 8.dp),
                                    enabled = state.toLocation.address.isNotBlank()
                                ) {
                                    Text("Set", fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
                
                item {
                    // Payment Method Section
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Payment",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            
                            OutlinedTextField(
                                value = when {
                                    state.selectedCard != null -> "${state.selectedCard!!.getDisplayNumber()} (${state.selectedCard!!.getCardType()})"
                                    state.paymentMethod != null -> state.paymentMethod!!.displayName
                                    else -> "Select Payment Method"
                                },
                                onValueChange = { },
                                label = { Text("Payment Method") },
                                leadingIcon = { Icon(Icons.Default.Payment, contentDescription = null) },
                                trailingIcon = { 
                                    IconButton(onClick = { showPaymentSelector = true }) {
                                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                readOnly = true
                            )
                        }
                    }
                }
                
                item {
                    // Date and Time Section
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Schedule",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                // Date Selection
                                OutlinedTextField(
                                    value = selectedDate?.let { SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(it) } ?: "Select Date",
                                    onValueChange = { },
                                    label = { Text("Date") },
                                    leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                                    trailingIcon = { 
                                        IconButton(onClick = { showDatePicker = true }) {
                                            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                                        }
                                    },
                                    modifier = Modifier.weight(1f),
                                    readOnly = true
                                )
                                
                                // Time Selection
                                OutlinedTextField(
                                    value = selectedTime?.let { "${String.format("%02d", it.first)}:${String.format("%02d", it.second)}" } ?: "Select Time",
                                    onValueChange = { },
                                    label = { Text("Time") },
                                    leadingIcon = { Icon(Icons.Default.AccessTime, contentDescription = null) },
                                    trailingIcon = { 
                                        IconButton(onClick = { showTimePicker = true }) {
                                            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                                        }
                                    },
                                    modifier = Modifier.weight(1f),
                                    readOnly = true
                                )
                            }
                        }
                    }
                }
                
                // Pricing Information
                if (state.pricing.distance > 0) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "Trip Summary",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Distance:")
                                    Text("${String.format("%.1f", state.pricing.distance)} km")
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Rate:")
                                    Text("$${String.format("%.2f", state.pricing.pricePerKm)}/km")
                                }
                                Divider()
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Total:", fontWeight = FontWeight.Bold)
                                    Text("$${String.format("%.2f", state.pricing.totalCost)}", fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
                
                item {
                    // Book Now Button
                    Button(
                        onClick = { showBookingSummary = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        enabled = state.fromLocation.latitude != 0.0 && 
                                state.toLocation.latitude != 0.0 && 
                                state.paymentMethod != null &&
                                selectedDate != null && 
                                selectedTime != null,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6C63FF)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Book Now", fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.Medium)
                    }
                }
            }
        }
    }
    
    // Date Picker Dialog
    if (showDatePicker) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                selectedDate = calendar.time
                showDatePicker = false
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
        showDatePicker = false
    }
    
    // Time Picker Dialog
    if (showTimePicker) {
        val calendar = Calendar.getInstance()
        TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                selectedTime = Pair(hourOfDay, minute)
                showTimePicker = false
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
        showTimePicker = false
    }
    
    // Payment Method Selector Dialog
    if (showPaymentSelector) {
        Dialog(onDismissRequest = { showPaymentSelector = false }) {
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Select Payment Method",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    
                    PaymentMethod.values().forEach { method ->
                        TextButton(
                            onClick = {
                                if (method == PaymentMethod.CARD) {
                                    // Navigate to payment method screen for card details
                                    navController.navigate("${NavigationRoutes.PAYMENT_METHOD}/${method.name}")
                                } else {
                                    // For cash on ride, set directly
                                    bookingViewModel.updatePaymentMethod(method)
                                }
                                showPaymentSelector = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(method.displayName)
                        }
                    }
                }
            }
        }
    }
    
    // Booking Summary Dialog
    if (showBookingSummary) {
        Dialog(onDismissRequest = { showBookingSummary = false }) {
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Booking Summary",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Divider()
                    
                    // Trip Details
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("From:", fontWeight = FontWeight.Medium)
                            Text(
                                text = state.fromLocation.address,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.End
                            )
                        }
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("To:", fontWeight = FontWeight.Medium)
                            Text(
                                text = state.toLocation.address,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.End
                            )
                        }
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Distance:", fontWeight = FontWeight.Medium)
                            Text(DistanceUtils.formatDistance(state.pricing.distance))
                        }
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Date & Time:", fontWeight = FontWeight.Medium)
                            Text(
                                selectedDate?.let { date ->
                                    selectedTime?.let { time ->
                                        "${SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(date)} at ${String.format("%02d:%02d", time.first, time.second)}"
                                    }
                                } ?: ""
                            )
                        }
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Payment Method:", fontWeight = FontWeight.Medium)
                            Text(state.paymentMethod?.displayName ?: "")
                        }
                    }
                    
                    Divider()
                    
                    // Total Cost
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Total Cost:",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            DistanceUtils.formatPrice(state.pricing.totalCost),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Action Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = { showBookingSummary = false },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Cancel")
                        }
                        
                        Button(
                            onClick = {
                                user?.id?.let { userId ->
                                    bookingViewModel.createBooking(userId, selectedDate, selectedTime)
                                    showBookingSummary = false
                                }
                            },
                            modifier = Modifier.weight(1f),
                            enabled = !state.isLoading
                        ) {
                            if (state.isLoading) {
                                CircularProgressIndicator(modifier = Modifier.size(16.dp))
                            } else {
                                Text("Confirm Booking")
                            }
                        }
                    }
                }
            }
        }
    }
    
    // Location Search Dialog
    if (showLocationSearch) {
        val currentQuery = if (isSearchingFromLocation) state.fromLocation.address else state.toLocation.address
        
        Dialog(onDismissRequest = { showLocationSearch = false }) {
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = if (isSearchingFromLocation) "Select Pickup Location" else "Select Destination",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    
                    // Show current typed location as first option
                    if (currentQuery.isNotBlank()) {
                        TextButton(
                            onClick = {
                                // Use the currently typed location and set coordinates
                                setLocationWithCoordinates(currentQuery.trim(), isSearchingFromLocation, bookingViewModel)
                                showLocationSearch = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    Icons.Default.Edit,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Use: \"$currentQuery\"",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Start
                                )
                            }
                        }
                        
                        Divider()
                    }
                    
                    Text(
                        text = "Popular Sri Lankan Locations:",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    // Filter suggestions based on current query
                    val suggestions = if (currentQuery.isBlank()) {
                        sriLankanLocations.entries.take(8)
                    } else {
                        sriLankanLocations.entries.filter { (locationName, _) ->
                            locationName.contains(currentQuery.lowercase()) || 
                            currentQuery.lowercase() in locationName
                        }.take(8)
                    }
                    
                    suggestions.forEach { (locationName, geoPoint) ->
                        TextButton(
                            onClick = {
                                val location = Location(
                                    latitude = geoPoint.latitude,
                                    longitude = geoPoint.longitude,
                                    address = locationName.split(" ").joinToString(" ") { 
                                        it.replaceFirstChar { char -> char.uppercaseChar() } 
                                    }
                                )
                                
                                if (isSearchingFromLocation) {
                                    bookingViewModel.updateFromLocation(location)
                                } else {
                                    bookingViewModel.updateToLocation(location)
                                }
                                
                                showLocationSearch = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    Icons.Default.LocationOn,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = locationName.split(" ").joinToString(" ") { 
                                        it.replaceFirstChar { char -> char.uppercaseChar() } 
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Start
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    
    // Error message
    state.error?.let { error ->
        LaunchedEffect(error) {
            // Show snackbar or toast - for now just print to console
            println("Booking error: $error")
        }
    }
}

private fun updateMapMarkers(mapView: MapView, fromLocation: Location, toLocation: Location) {
    // Clear existing overlays
    mapView.overlays.clear()
    
    val context = mapView.context
    
    // Add "From" marker (Green color for pickup)
    if (fromLocation.latitude != 0.0) {
        val fromMarker = Marker(mapView)
        fromMarker.position = GeoPoint(fromLocation.latitude, fromLocation.longitude)
        fromMarker.title = "From: ${fromLocation.address}"
        fromMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        
        // Set green marker icon for pickup
        fromMarker.icon = ContextCompat.getDrawable(context, R.drawable.ic_pickup_location)?.apply {
            setTint(android.graphics.Color.GREEN)
        }
        
        mapView.overlays.add(fromMarker)
    }
    
    // Add "To" marker (Red color for destination)
    if (toLocation.latitude != 0.0) {
        val toMarker = Marker(mapView)
        toMarker.position = GeoPoint(toLocation.latitude, toLocation.longitude)
        toMarker.title = "To: ${toLocation.address}"
        toMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        
        // Set red marker icon for destination
        toMarker.icon = ContextCompat.getDrawable(context, R.drawable.ic_dropoff_location)?.apply {
            setTint(android.graphics.Color.RED)
        }
        
        mapView.overlays.add(toMarker)
    }
    
    // Add route if both points are set
    if (fromLocation.latitude != 0.0 && toLocation.latitude != 0.0) {
        // Try to get a proper road route using OpenStreetMap routing
        val fromPoint = GeoPoint(fromLocation.latitude, fromLocation.longitude)
        val toPoint = GeoPoint(toLocation.latitude, toLocation.longitude)
        
        try {
            // For now, use straight line route - can be enhanced later with proper routing service
            drawStraightLineRoute(mapView, fromPoint, toPoint)
        } catch (e: Exception) {
            // Fallback to straight line if any issues occur
            drawStraightLineRoute(mapView, fromPoint, toPoint)
        }
        
        // Center map on both points with proper zoom
        val boundingBox = org.osmdroid.util.BoundingBox.fromGeoPoints(
            listOf(fromPoint, toPoint)
        )
        mapView.zoomToBoundingBox(boundingBox, true, 150) // Increased padding for better view
    } else if (fromLocation.latitude != 0.0) {
        // Center on from location
        mapView.controller.animateTo(GeoPoint(fromLocation.latitude, fromLocation.longitude))
        mapView.controller.setZoom(13.0)
    } else if (toLocation.latitude != 0.0) {
        // Center on to location
        mapView.controller.animateTo(GeoPoint(toLocation.latitude, toLocation.longitude))
        mapView.controller.setZoom(13.0)
    }
    
    mapView.invalidate()
}

private fun drawStraightLineRoute(mapView: MapView, fromPoint: GeoPoint, toPoint: GeoPoint) {
    val polyline = Polyline()
    polyline.addPoint(fromPoint)
    polyline.addPoint(toPoint)
    polyline.color = android.graphics.Color.BLUE
    polyline.width = 10f
    polyline.paint.strokeCap = android.graphics.Paint.Cap.ROUND
    polyline.paint.strokeJoin = android.graphics.Paint.Join.ROUND
    mapView.overlays.add(polyline)
}

// Sri Lankan cities and locations database
private val sriLankanLocations = mapOf(
    "colombo" to GeoPoint(6.9271, 79.8612),
    "kandy" to GeoPoint(7.2906, 80.6337),
    "galle" to GeoPoint(6.0535, 80.2210),
    "jaffna" to GeoPoint(9.6615, 80.0255),
    "negombo" to GeoPoint(7.2083, 79.8358),
    "anuradhapura" to GeoPoint(8.3114, 80.4037),
    "polonnaruwa" to GeoPoint(7.9403, 81.0188),
    "matara" to GeoPoint(5.9549, 80.5550),
    "batticaloa" to GeoPoint(7.7102, 81.6924),
    "trincomalee" to GeoPoint(8.5874, 81.2152),
    "kurunegala" to GeoPoint(7.4818, 80.3609),
    "ratnapura" to GeoPoint(6.6828, 80.4026),
    "badulla" to GeoPoint(6.9934, 81.0550),
    "kalmunai" to GeoPoint(7.4098, 81.8344),
    "gampaha" to GeoPoint(7.0873, 79.9990),
    "kalutara" to GeoPoint(6.5854, 79.9607),
    "chilaw" to GeoPoint(7.5759, 79.7951),
    "hambantota" to GeoPoint(6.1241, 81.1185),
    "nuwara eliya" to GeoPoint(6.9497, 80.7891),
    "ella" to GeoPoint(6.8707, 81.0464),
    "sigiriya" to GeoPoint(7.9568, 80.7598),
    "dambulla" to GeoPoint(7.8731, 80.6511),
    "mirissa" to GeoPoint(5.9482, 80.4617),
    "unawatuna" to GeoPoint(6.0108, 80.2492),
    "bentota" to GeoPoint(6.4261, 80.0007),
    "hikkaduwa" to GeoPoint(6.1407, 80.1003),
    "mount lavinia" to GeoPoint(6.8344, 79.8633),
    "dehiwala" to GeoPoint(6.8515, 79.8632),
    "maharagama" to GeoPoint(6.8422, 79.9265),
    "moratuwa" to GeoPoint(6.7730, 79.8816),
    "kotte" to GeoPoint(6.8905, 79.9015),
    "kelaniya" to GeoPoint(6.9553, 79.9220),
    "katunayake" to GeoPoint(7.1697, 79.8838),
    "bandarawela" to GeoPoint(6.8326, 80.9847),
    "haputale" to GeoPoint(6.7678, 80.9695),
    "tissamaharama" to GeoPoint(6.2733, 81.2866),
    "arugam bay" to GeoPoint(6.8404, 81.8361),
    "pasikudah" to GeoPoint(7.9333, 81.5500),
    "nilaveli" to GeoPoint(8.6833, 81.2167),
    "uppuveli" to GeoPoint(8.6000, 81.2167),
    "kalpitiya" to GeoPoint(8.2333, 79.7667),
    "mannar" to GeoPoint(8.9811, 79.9045),
    "vavuniya" to GeoPoint(8.7542, 80.4982),
    "kilinochchi" to GeoPoint(9.3961, 80.3592),
    "mullaitivu" to GeoPoint(9.2670, 80.8142)
)

private fun setLocationWithCoordinates(address: String, isFromLocation: Boolean, viewModel: BookingViewModel) {
    val searchQuery = address.lowercase().trim()
    
    // Find matching location in Sri Lanka database
    val matchedLocation = sriLankanLocations.entries.find { (name, _) ->
        name.contains(searchQuery) || searchQuery.contains(name)
    }
    
    val location = if (matchedLocation != null) {
        // Use predefined location with exact coordinates
        val (name, geoPoint) = matchedLocation
        Location(
            latitude = geoPoint.latitude,
            longitude = geoPoint.longitude,
            address = address // Keep user's typed text
        )
    } else {
        // For any typed location, create a location with default Sri Lanka center coordinates
        Location(
            latitude = 7.2906, // Center of Sri Lanka
            longitude = 80.6337,
            address = address
        )
    }
    
    if (isFromLocation) {
        viewModel.updateFromLocation(location)
    } else {
        viewModel.updateToLocation(location)
    }
}

private fun searchLocationInSriLanka(query: String, isFromLocation: Boolean, viewModel: BookingViewModel) {
    val searchQuery = query.lowercase().trim()
    
    // Find matching location in Sri Lanka database
    val matchedLocation = sriLankanLocations.entries.find { (name, _) ->
        name.contains(searchQuery) || searchQuery.contains(name)
    }
    
    if (matchedLocation != null) {
        // Use predefined location with exact coordinates
        val (name, geoPoint) = matchedLocation
        val location = Location(
            latitude = geoPoint.latitude,
            longitude = geoPoint.longitude,
            address = name.split(" ").joinToString(" ") { it.replaceFirstChar { char -> char.uppercaseChar() } }
        )
        
        if (isFromLocation) {
            viewModel.updateFromLocation(location)
        } else {
            viewModel.updateToLocation(location)
        }
    } else {
        // For any typed location, create a location with default Sri Lanka center coordinates
        // This allows users to type any location name
        val location = Location(
            latitude = 7.2906, // Center of Sri Lanka
            longitude = 80.6337,
            address = query.trim().split(" ").joinToString(" ") { 
                it.replaceFirstChar { char -> if (char.isLowerCase()) char.titlecase() else char.toString() } 
            }
        )
        
        if (isFromLocation) {
            viewModel.updateFromLocation(location)
        } else {
            viewModel.updateToLocation(location)
        }
    }
}
