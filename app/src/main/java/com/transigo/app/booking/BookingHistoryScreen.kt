package com.transigo.app.booking

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.transigo.app.auth.AuthViewModel
import com.transigo.app.data.model.Booking
import com.transigo.app.data.model.BookingStatus
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingHistoryScreen(
    navController: NavController,
    bookingViewModel: BookingViewModel = viewModel(),
    authViewModel: AuthViewModel = viewModel()
) {
    val state by bookingViewModel.state.collectAsState()
    val user by authViewModel.user.collectAsState()
    var showRatingBottomSheet by remember { mutableStateOf(false) }
    var selectedBookingForRating by remember { mutableStateOf<Booking?>(null) }
    
    // Load bookings when screen opens
    LaunchedEffect(user?.id) {
        user?.id?.let { userId ->
            bookingViewModel.loadMyBookings(userId)
        }
    }

    // Handle rating success
    LaunchedEffect(state.ratingSuccess) {
        if (state.ratingSuccess) {
            showRatingBottomSheet = false
            selectedBookingForRating = null
            bookingViewModel.clearRatingState()
            // Reload bookings to reflect the updated rating
            user?.id?.let { userId ->
                bookingViewModel.loadMyBookings(userId)
            }
        }
    }
    
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
                text = "Booking History",
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

        // Bookings list
        if (state.bookings.isEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No Bookings Yet",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Your booking history will appear here once you make your first booking.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.bookings) { booking ->
                    BookingCard(
                        booking = booking,
                        onRateClick = { bookingToRate ->
                            selectedBookingForRating = bookingToRate
                            showRatingBottomSheet = true
                        }
                    )
                }
            }
        }

        // Rating Bottom Sheet
        if (showRatingBottomSheet) {
            selectedBookingForRating?.let { booking ->
                RatingBottomSheet(
                    booking = booking,
                    isLoading = state.isRating,
                    error = state.error,
                    onDismiss = { 
                        showRatingBottomSheet = false
                        selectedBookingForRating = null
                        bookingViewModel.clearRatingState()
                    },
                    onSubmitRating = { stars, comment ->
                        bookingViewModel.rateBooking(booking.id, stars, comment)
                    }
                )
            }
        }
    }
}

@Composable
fun BookingCard(
    booking: Booking,
    onRateClick: (Booking) -> Unit = {}
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header with status and type
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${booking.type.name} Transfer",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Surface(
                    color = when (booking.status) {
                        BookingStatus.REQUESTED -> MaterialTheme.colorScheme.primary
                        BookingStatus.CONFIRMED -> MaterialTheme.colorScheme.tertiary
                        BookingStatus.IN_PROGRESS -> MaterialTheme.colorScheme.secondary
                        BookingStatus.COMPLETED -> MaterialTheme.colorScheme.primaryContainer
                        BookingStatus.CANCELLED -> MaterialTheme.colorScheme.error
                    },
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = booking.status.name,
                        color = when (booking.status) {
                            BookingStatus.COMPLETED -> MaterialTheme.colorScheme.onPrimaryContainer
                            else -> MaterialTheme.colorScheme.onPrimary
                        },
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Route information
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.MyLocation,
                            contentDescription = "Pickup",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = booking.pickupName,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    // Arrow indication
                    Text(
                        text = "→",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(start = 24.dp)
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.LocationOn,
                            contentDescription = "Drop",
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = booking.dropName,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                
                // Ride type
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = booking.rideType.name,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
            
            // Timing information
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    booking.requestedAt?.let { timestamp ->
                        Text(
                            text = "Requested: ${SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault()).format(timestamp.toDate())}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    
                    booking.scheduledAt?.let { timestamp ->
                        Text(
                            text = "Scheduled: ${SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault()).format(timestamp.toDate())}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                
                // Rate button for completed bookings without rating
                if (booking.status == BookingStatus.COMPLETED && booking.rating.stars == 0) {
                    Button(
                        onClick = { onRateClick(booking) },
                        modifier = Modifier.height(32.dp)
                    ) {
                        Text(
                            text = "Rate",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                } else if (booking.status == BookingStatus.COMPLETED && booking.rating.stars > 0) {
                    // Show existing rating
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        repeat(booking.rating.stars) {
                            Icon(
                                Icons.Filled.Star,
                                contentDescription = null,
                                tint = Color(0xFFFFD700),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Text(
                            text = " (${booking.rating.stars})",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RatingBottomSheet(
    booking: Booking,
    isLoading: Boolean,
    error: String?,
    onDismiss: () -> Unit,
    onSubmitRating: (stars: Int, comment: String) -> Unit
) {
    var selectedStars by remember { mutableIntStateOf(0) }
    var comment by remember { mutableStateOf("") }
    val bottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = bottomSheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                text = "Rate Your Trip",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "${booking.pickupName} → ${booking.dropName}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Star rating
            Text(
                text = "How was your experience?",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(5) { index ->
                    val starNumber = index + 1
                    Icon(
                        imageVector = if (starNumber <= selectedStars) Icons.Filled.Star else Icons.Outlined.Star,
                        contentDescription = "Star $starNumber",
                        tint = if (starNumber <= selectedStars) Color(0xFFFFD700) else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .size(40.dp)
                            .clickable { selectedStars = starNumber }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Comment section
            Text(
                text = "Add a comment (optional)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            OutlinedTextField(
                value = comment,
                onValueChange = { comment = it },
                placeholder = { Text("Share your feedback...") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5
            )
            
            // Error message
            error?.let { errorMessage ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Submit button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier.weight(1f),
                    enabled = !isLoading
                ) {
                    Text("Cancel")
                }
                
                Button(
                    onClick = {
                        if (selectedStars > 0) {
                            onSubmitRating(selectedStars, comment)
                        }
                    },
                    modifier = Modifier.weight(1f),
                    enabled = selectedStars > 0 && !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text("Submit Rating")
                    }
                }
            }
            
            // Bottom spacing for gesture navigation
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
