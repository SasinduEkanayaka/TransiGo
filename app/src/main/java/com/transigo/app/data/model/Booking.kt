package com.transigo.app.data.model

import com.google.firebase.Timestamp

data class Booking(
    val id: String = "",
    val userId: String = "",
    val type: BookingType = BookingType.AIRPORT,
    val pickupName: String = "",
    val dropName: String = "",
    val rideType: RideType = RideType.STANDARD,
    val requestedAt: Timestamp? = null, // Will be set via serverTimestamp
    val scheduledAt: Timestamp? = null, // Optional scheduling
    val status: BookingStatus = BookingStatus.REQUESTED,
    val driverId: String? = null,
    val rating: BookingRating = BookingRating()
)

data class BookingRating(
    val stars: Int = 0,
    val comment: String = "",
    val ratedAt: Timestamp? = null
)

enum class BookingType {
    AIRPORT,
    HOTEL
}

enum class RideType {
    STANDARD,
    VAN,
    LUX
}

enum class BookingStatus {
    REQUESTED,
    APPROVED,
    REJECTED,
    COMPLETED,
    CANCELLED,
    // Keep existing ones for compatibility
    CONFIRMED,
    IN_PROGRESS
}
