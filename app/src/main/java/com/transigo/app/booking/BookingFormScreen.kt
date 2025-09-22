package com.transigo.app.booking

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
    
    // Handle result from payment method screen
    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
    val selectedPaymentMethod by savedStateHandle?.getLiveData<PaymentMethod>("selected_payment_method")?.observeAsState()
    
    LaunchedEffect(selectedPaymentMethod) {
        selectedPaymentMethod?.let { paymentMethod ->
            bookingViewModel.updatePaymentMethod(paymentMethod)
            savedStateHandle?.remove<PaymentMethod>("selected_payment_method")
        }
    }
    
    Box(modifier = Modifier.fillMaxSize()) {
        // Map View
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
            
            // Map
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                factory = { mapContext ->
                    MapView(mapContext).apply {
                        setTileSource(TileSourceFactory.MAPNIK)
                        setBuiltInZoomControls(true)
                        setMultiTouchControls(true)
                        controller.setZoom(15.0)
                        
                        // Default location (you can set to current location or city center)
                        controller.setCenter(GeoPoint(40.7128, -74.0060)) // New York as example
                        
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
        }
        
        // Bottom Sheet with booking details
        Card(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // From/To Fields
                OutlinedTextField(
                    value = state.fromLocation.address,
                    onValueChange = { /* Read-only, updated via map tap */ },
                    label = { Text("From") },
                    leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color.Green) },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    placeholder = { Text("Tap on map to set pickup location") }
                )
                
                OutlinedTextField(
                    value = state.toLocation.address,
                    onValueChange = { /* Read-only, updated via map tap */ },
                    label = { Text("To") },
                    leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color.Red) },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    placeholder = { Text("Tap on map to set destination") }
                )
                
                // Payment Method Selection
                OutlinedTextField(
                    value = state.paymentMethod?.displayName ?: "Select Payment Method",
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
                
                // Date Selection
                OutlinedTextField(
                    value = selectedDate?.let { SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(it) } ?: "Select Start Date",
                    onValueChange = { },
                    label = { Text("Pick a Date") },
                    leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                    trailingIcon = { 
                        IconButton(onClick = { showDatePicker = true }) {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true
                )
                
                // Time Selection
                OutlinedTextField(
                    value = selectedTime?.let { "${String.format("%02d", it.first)}:${String.format("%02d", it.second)}" } ?: "Select Start Time",
                    onValueChange = { },
                    label = { Text("Pick a Time") },
                    leadingIcon = { Icon(Icons.Default.AccessTime, contentDescription = null) },
                    trailingIcon = { 
                        IconButton(onClick = { showTimePicker = true }) {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true
                )
                
                // Distance and Total Cost
                if (state.pricing.distance > 0) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Distance: ${DistanceUtils.formatDistance(state.pricing.distance)}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Rate: ${DistanceUtils.formatPrice(state.pricing.pricePerKm)}/km",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Total:",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = DistanceUtils.formatPrice(state.pricing.totalCost),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                
                // Confirm Button
                Button(
                    onClick = {
                        showBookingSummary = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    enabled = !state.isLoading && 
                             state.fromLocation.latitude != 0.0 && 
                             state.toLocation.latitude != 0.0 &&
                             state.paymentMethod != null &&
                             selectedDate != null &&
                             selectedTime != null
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text("Confirm", fontSize = 16.sp)
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
                                if (method == PaymentMethod.CARD || method == PaymentMethod.MOBILE_BANKING) {
                                    // Navigate to payment method screen
                                    navController.navigate("${NavigationRoutes.PAYMENT_METHOD}/${method.name}")
                                } else {
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
                                CircularProgressIndicator(size = 16.dp)
                            } else {
                                Text("Confirm Booking")
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
    
    // Add "From" marker
    if (fromLocation.latitude != 0.0) {
        val fromMarker = Marker(mapView)
        fromMarker.position = GeoPoint(fromLocation.latitude, fromLocation.longitude)
        fromMarker.title = "From: ${fromLocation.address}"
        fromMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        
        // Set green marker icon
        val drawable = ContextCompat.getDrawable(context, R.drawable.ic_pickup_location)
        if (drawable != null) {
            fromMarker.icon = drawable
        }
        
        mapView.overlays.add(fromMarker)
    }
    
    // Add "To" marker
    if (toLocation.latitude != 0.0) {
        val toMarker = Marker(mapView)
        toMarker.position = GeoPoint(toLocation.latitude, toLocation.longitude)
        toMarker.title = "To: ${toLocation.address}"
        toMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        
        // Set red marker icon
        val drawable = ContextCompat.getDrawable(context, R.drawable.ic_dropoff_location)
        if (drawable != null) {
            toMarker.icon = drawable
        }
        
        mapView.overlays.add(toMarker)
    }
    
    // Add route line if both points are set
    if (fromLocation.latitude != 0.0 && toLocation.latitude != 0.0) {
        val polyline = Polyline()
        polyline.addPoint(GeoPoint(fromLocation.latitude, fromLocation.longitude))
        polyline.addPoint(GeoPoint(toLocation.latitude, toLocation.longitude))
        polyline.color = ContextCompat.getColor(context, R.color.purple_500)
        polyline.width = 8f
        mapView.overlays.add(polyline)
        
        // Center map on both points
        val boundingBox = org.osmdroid.util.BoundingBox.fromGeoPoints(
            listOf(
                GeoPoint(fromLocation.latitude, fromLocation.longitude),
                GeoPoint(toLocation.latitude, toLocation.longitude)
            )
        )
        mapView.zoomToBoundingBox(boundingBox, true, 100)
    }
    
    mapView.invalidate()
}
