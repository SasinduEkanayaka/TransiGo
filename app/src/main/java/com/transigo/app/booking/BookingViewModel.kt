package com.transigo.app.booking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.transigo.app.data.repository.BookingRepository
import com.transigo.app.data.repository.BookingForm
import com.transigo.app.data.model.*
import com.transigo.app.utils.DistanceUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.Date

data class BookingState(
    val isLoading: Boolean = false,
    val type: BookingType = BookingType.AIRPORT,
    val pickupName: String = "",
    val dropName: String = "",
    val fromLocation: Location = Location(),
    val toLocation: Location = Location(),
    val paymentMethod: PaymentMethod? = null,
    val pricing: BookingPricing = BookingPricing(),
    val rideType: RideType = RideType.STANDARD,
    val scheduledAt: Timestamp? = null,
    val error: String? = null,
    val isCreated: Boolean = false,
    val bookings: List<Booking> = emptyList(),
    val isRating: Boolean = false,
    val ratingSuccess: Boolean = false
)

class BookingViewModel(
    private val bookingRepository: BookingRepository = BookingRepository(FirebaseFirestore.getInstance())
) : ViewModel() {
    
    private val _state = MutableStateFlow(BookingState())
    val state: StateFlow<BookingState> = _state.asStateFlow()
    
    fun updateType(type: BookingType) {
        _state.value = _state.value.copy(type = type)
    }
    
    fun updatePickupName(pickupName: String) {
        _state.value = _state.value.copy(pickupName = pickupName)
    }
    
    fun updateDropName(dropName: String) {
        _state.value = _state.value.copy(dropName = dropName)
    }
    
    fun updateFromLocation(location: Location) {
        _state.value = _state.value.copy(fromLocation = location)
        calculatePricing()
    }
    
    fun updateToLocation(location: Location) {
        _state.value = _state.value.copy(toLocation = location)
        calculatePricing()
    }
    
    fun updatePaymentMethod(paymentMethod: PaymentMethod) {
        _state.value = _state.value.copy(paymentMethod = paymentMethod)
    }
    
    fun updateRideType(rideType: RideType) {
        _state.value = _state.value.copy(rideType = rideType)
    }
    
    fun updateScheduledAt(scheduledAt: Timestamp?) {
        _state.value = _state.value.copy(scheduledAt = scheduledAt)
    }
    
    private fun calculatePricing() {
        val currentState = _state.value
        if (currentState.fromLocation.latitude != 0.0 && currentState.toLocation.latitude != 0.0) {
            val distance = DistanceUtils.calculateDistance(
                currentState.fromLocation.latitude,
                currentState.fromLocation.longitude,
                currentState.toLocation.latitude,
                currentState.toLocation.longitude
            )
            val pricing = BookingPricing.calculate(distance)
            _state.value = currentState.copy(pricing = pricing)
        }
    }
    
    fun clearError() {
        _state.value = _state.value.copy(error = null)
    }
    
    fun createBooking(userId: String, selectedDate: Date?, selectedTime: Pair<Int, Int>?) {
        val currentState = _state.value
        
        // Validate form
        if (currentState.fromLocation.latitude == 0.0) {
            _state.value = currentState.copy(error = "Please select pickup location on the map")
            return
        }
        
        if (currentState.toLocation.latitude == 0.0) {
            _state.value = currentState.copy(error = "Please select destination on the map")
            return
        }
        
        if (currentState.paymentMethod == null) {
            _state.value = currentState.copy(error = "Please select a payment method")
            return
        }
        
        if (selectedDate == null) {
            _state.value = currentState.copy(error = "Please select a date")
            return
        }
        
        if (selectedTime == null) {
            _state.value = currentState.copy(error = "Please select a time")
            return
        }
        
        viewModelScope.launch {
            _state.value = currentState.copy(isLoading = true, error = null)
            
            // Combine date and time
            val calendar = java.util.Calendar.getInstance()
            calendar.time = selectedDate
            calendar.set(java.util.Calendar.HOUR_OF_DAY, selectedTime.first)
            calendar.set(java.util.Calendar.MINUTE, selectedTime.second)
            
            val form = BookingForm(
                type = currentState.type,
                pickupName = currentState.fromLocation.address,
                dropName = currentState.toLocation.address,
                rideType = currentState.rideType,
                scheduledAt = Timestamp(calendar.time)
            )
            
            bookingRepository.createBooking(userId, form)
                .onSuccess {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        isCreated = true,
                        error = null
                    )
                }
                .onFailure { exception ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Failed to create booking"
                    )
                }
        }
    }
    
    // Keep the existing method for backward compatibility
    fun createBooking(userId: String) {
        val currentState = _state.value
        
        // Validate form
        if (currentState.pickupName.isBlank()) {
            _state.value = currentState.copy(error = "Pickup location is required")
            return
        }
        
        if (currentState.dropName.isBlank()) {
            _state.value = currentState.copy(error = "Drop location is required")
            return
        }
        
        viewModelScope.launch {
            _state.value = currentState.copy(isLoading = true, error = null)
            
            val form = BookingForm(
                type = currentState.type,
                pickupName = currentState.pickupName,
                dropName = currentState.dropName,
                rideType = currentState.rideType,
                scheduledAt = currentState.scheduledAt
            )
            
            bookingRepository.createBooking(userId, form)
                .onSuccess {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        isCreated = true,
                        error = null
                    )
                }
                .onFailure { exception ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Failed to create booking"
                    )
                }
        }
    }
    
    fun resetForm() {
        _state.value = BookingState()
    }
    
    fun loadMyBookings(userId: String) {
        viewModelScope.launch {
            bookingRepository.myBookingsQuery(userId).collect { result ->
                result.onSuccess { bookings ->
                    _state.value = _state.value.copy(bookings = bookings)
                }.onFailure { exception ->
                    _state.value = _state.value.copy(
                        error = exception.message ?: "Failed to load bookings"
                    )
                }
            }
        }
    }

    fun myBookingsFlow(userId: String): Flow<List<Booking>> {
        return bookingRepository.myBookingsQuery(userId).map { result ->
            result.getOrElse { emptyList() }
        }
    }

    fun rateBooking(bookingId: String, stars: Int, comment: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isRating = true, error = null, ratingSuccess = false)
            
            bookingRepository.rateBooking(bookingId, stars, comment)
                .onSuccess {
                    _state.value = _state.value.copy(
                        isRating = false,
                        ratingSuccess = true,
                        error = null
                    )
                }
                .onFailure { exception ->
                    _state.value = _state.value.copy(
                        isRating = false,
                        error = exception.message ?: "Failed to submit rating"
                    )
                }
        }
    }

    fun clearRatingState() {
        _state.value = _state.value.copy(ratingSuccess = false, error = null)
    }
}