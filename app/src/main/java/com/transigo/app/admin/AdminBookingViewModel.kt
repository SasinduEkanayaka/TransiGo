package com.transigo.app.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.transigo.app.data.model.Booking
import com.transigo.app.data.model.BookingStatus
import com.transigo.app.data.model.Driver
import com.transigo.app.data.model.User
import com.transigo.app.data.repository.AdminBookingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for admin booking management.
 * Handles booking operations, status filtering, and user/driver data.
 */
@HiltViewModel
class AdminBookingViewModel @Inject constructor(
    private val adminBookingRepository: AdminBookingRepository
) : ViewModel() {

    // Current filter state - start with showing all bookings
    private val _currentFilter = MutableStateFlow(BookingStatus.REQUESTED)
    val currentFilter: StateFlow<BookingStatus> = _currentFilter.asStateFlow()
    
    // Track if we're showing all bookings vs filtered
    private val _showingAllBookings = MutableStateFlow(true)
    val showingAllBookings: StateFlow<Boolean> = _showingAllBookings.asStateFlow()

    // Bookings data
    private val _bookings = MutableStateFlow<List<Booking>>(emptyList())
    val bookings: StateFlow<List<Booking>> = _bookings.asStateFlow()

    // Loading states
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Error state
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    // User cache for resolving user emails
    private val _users = MutableStateFlow<Map<String, User>>(emptyMap())
    val users: StateFlow<Map<String, User>> = _users.asStateFlow()

    // Available drivers
    private val _activeDrivers = MutableStateFlow<List<Driver>>(emptyList())
    val activeDrivers: StateFlow<List<Driver>> = _activeDrivers.asStateFlow()

    // Action states
    private val _actionInProgress = MutableStateFlow<String?>(null)
    val actionInProgress: StateFlow<String?> = _actionInProgress.asStateFlow()

    init {
        // Start by loading all bookings
        loadAllBookings()
        loadActiveDrivers()
    }

    /**
     * Set the current filter and reload bookings
     */
    fun setFilter(status: BookingStatus) {
        if (_currentFilter.value != status) {
            _currentFilter.value = status
            _showingAllBookings.value = false
            loadBookings()
        }
    }

    /**
     * Load bookings based on current filter
     */
    fun loadBookings() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            adminBookingRepository.getBookingsByStatus(_currentFilter.value)
                .collect { result ->
                    result.fold(
                        onSuccess = { bookings ->
                            _bookings.value = bookings
                            _isLoading.value = false
                            
                            // Load user details for each booking
                            loadUsersForBookings(bookings)
                        },
                        onFailure = { exception ->
                            _error.value = exception.message ?: "Failed to load bookings"
                            _isLoading.value = false
                            
                            // Clear bookings on failure
                            _bookings.value = emptyList()
                        }
                    )
                }
        }
    }

    /**
     * Load all bookings regardless of status
     */
    fun loadAllBookings() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            _showingAllBookings.value = true

            adminBookingRepository.getAllBookings()
                .collect { result ->
                    result.fold(
                        onSuccess = { bookings ->
                            println("AdminBookingViewModel: Loaded ${bookings.size} bookings")
                            _bookings.value = bookings
                            _isLoading.value = false
                            
                            // Load user details for each booking
                            loadUsersForBookings(bookings)
                        },
                        onFailure = { exception ->
                            println("AdminBookingViewModel: Failed to load bookings: ${exception.message}")
                            _error.value = exception.message ?: "Failed to load bookings"
                            _isLoading.value = false
                        }
                    )
                }
        }
    }

    /**
     * Load user details for bookings
     */
    private fun loadUsersForBookings(bookings: List<Booking>) {
        viewModelScope.launch {
            val userIds = bookings.map { it.userId }.distinct()
            val currentUsers = _users.value.toMutableMap()

            userIds.forEach { userId ->
                if (!currentUsers.containsKey(userId)) {
                    adminBookingRepository.getUser(userId).fold(
                        onSuccess = { user ->
                            user?.let { currentUsers[userId] = it }
                        },
                        onFailure = { /* Handle error silently for individual user lookup */ }
                    )
                }
            }

            _users.value = currentUsers
        }
    }

    /**
     * Load active drivers for assignment
     */
    fun loadActiveDrivers() {
        viewModelScope.launch {
            adminBookingRepository.getActiveDrivers().fold(
                onSuccess = { drivers ->
                    _activeDrivers.value = drivers
                },
                onFailure = { exception ->
                    _error.value = "Failed to load drivers: ${exception.message}"
                }
            )
        }
    }

    /**
     * Approve a booking
     */
    fun approveBooking(bookingId: String, driverId: String? = null) {
        viewModelScope.launch {
            _actionInProgress.value = bookingId
            
            adminBookingRepository.approveBooking(bookingId, driverId).fold(
                onSuccess = {
                    _actionInProgress.value = null
                    loadBookings() // Refresh the list
                },
                onFailure = { exception ->
                    _error.value = "Failed to approve booking: ${exception.message}"
                    _actionInProgress.value = null
                }
            )
        }
    }

    /**
     * Reject a booking
     */
    fun rejectBooking(bookingId: String) {
        viewModelScope.launch {
            _actionInProgress.value = bookingId
            
            adminBookingRepository.rejectBooking(bookingId).fold(
                onSuccess = {
                    _actionInProgress.value = null
                    loadBookings() // Refresh the list
                },
                onFailure = { exception ->
                    _error.value = "Failed to reject booking: ${exception.message}"
                    _actionInProgress.value = null
                }
            )
        }
    }

    /**
     * Complete a booking
     */
    fun completeBooking(bookingId: String) {
        viewModelScope.launch {
            _actionInProgress.value = bookingId
            
            adminBookingRepository.completeBooking(bookingId).fold(
                onSuccess = {
                    _actionInProgress.value = null
                    loadBookings() // Refresh the list
                },
                onFailure = { exception ->
                    _error.value = "Failed to complete booking: ${exception.message}"
                    _actionInProgress.value = null
                }
            )
        }
    }

    /**
     * Cancel a booking
     */
    fun cancelBooking(bookingId: String) {
        viewModelScope.launch {
            _actionInProgress.value = bookingId
            
            adminBookingRepository.cancelBooking(bookingId).fold(
                onSuccess = {
                    _actionInProgress.value = null
                    loadBookings() // Refresh the list
                },
                onFailure = { exception ->
                    _error.value = "Failed to cancel booking: ${exception.message}"
                    _actionInProgress.value = null
                }
            )
        }
    }

    /**
     * Clear error message
     */
    fun clearError() {
        _error.value = null
    }

    /**
     * Get user email for a user ID
     */
    fun getUserEmail(userId: String): String {
        return _users.value[userId]?.email ?: "Loading..."
    }

    /**
     * Get driver name for a driver ID
     */
    fun getDriverName(driverId: String?): String {
        if (driverId == null) return "Not assigned"
        return _activeDrivers.value.find { it.id == driverId }?.fullName ?: "Unknown Driver"
    }

    /**
     * Create sample data for testing
     */
    fun createSampleData() {
        viewModelScope.launch {
            _isLoading.value = true
            adminBookingRepository.createSampleBookings().fold(
                onSuccess = {
                    loadBookings() // Refresh after creating sample data
                },
                onFailure = { exception ->
                    _error.value = "Failed to create sample data: ${exception.message}"
                    _isLoading.value = false
                }
            )
        }
    }
}
