package com.transigo.app.data.repository

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.FirebaseFirestoreException
import com.transigo.app.core.service.NotificationService
import com.transigo.app.data.model.Booking
import com.transigo.app.data.model.BookingStatus
import com.transigo.app.data.model.BookingType
import com.transigo.app.data.model.RideType
import com.transigo.app.data.model.User
import com.transigo.app.data.model.Driver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for admin booking operations.
 * Handles booking management, status updates, and driver assignments.
 */
@Singleton
class AdminBookingRepository @Inject constructor(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val notificationService: NotificationService
) {
    private val bookingsCollection = firestore.collection("bookings")
    private val usersCollection = firestore.collection("users")
    private val driversCollection = firestore.collection("drivers")

    /**
     * Get bookings filtered by status
     */
    fun getBookingsByStatus(status: BookingStatus): Flow<Result<List<Booking>>> = flow {
        try {
            val snapshot = bookingsCollection
                .whereEqualTo("status", status.name)
                .get()
                .await()

            val bookings = snapshot.documents.mapNotNull { doc ->
                try {
                    doc.toObject(Booking::class.java)?.copy(id = doc.id)
                } catch (e: Exception) {
                    // Log the error but continue processing other bookings
                    null
                }
            }
            
            val sorted = bookings.sortedByDescending { it.requestedAt?.toDate() }
            emit(Result.success(sorted))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    /**
     * Get all bookings for admin overview
     */
    fun getAllBookings(): Flow<Result<List<Booking>>> = flow {
        try {
            println("AdminBookingRepository: Fetching all bookings...")
            val snapshot = bookingsCollection.get().await()
            println("AdminBookingRepository: Got ${snapshot.documents.size} documents")

            val bookings = snapshot.documents.mapNotNull { doc ->
                try {
                    doc.toObject(Booking::class.java)?.copy(id = doc.id)
                } catch (e: Exception) {
                    // Log the error but continue processing other bookings
                    println("AdminBookingRepository: Error parsing booking ${doc.id}: ${e.message}")
                    null
                }
            }
            
            // Sort manually after fetching to avoid index issues
            val sorted = bookings.sortedByDescending { it.requestedAt?.toDate() }
            println("AdminBookingRepository: Returning ${sorted.size} valid bookings")
            emit(Result.success(sorted))
        } catch (e: Exception) {
            println("AdminBookingRepository: Error fetching bookings: ${e.message}")
            emit(Result.failure(e))
        }
    }

    /**
     * Get user details by ID
     */
    suspend fun getUser(userId: String): Result<User?> {
        return try {
            val document = usersCollection.document(userId).get().await()
            val user = document.toObject(User::class.java)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get active drivers for assignment
     */
    suspend fun getActiveDrivers(): Result<List<Driver>> {
        return try {
            val snapshot = driversCollection
                .whereEqualTo("isActive", true)
                .get()
                .await()

            val drivers = snapshot.documents.mapNotNull { doc ->
                doc.toObject(Driver::class.java)?.copy(id = doc.id)
            }
            
            Result.success(drivers)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Approve a booking request
     */
    suspend fun approveBooking(bookingId: String, driverId: String? = null, adminName: String? = null): Result<Unit> {
        return try {
            // First get the booking to get the userId
            val bookingDoc = bookingsCollection.document(bookingId).get().await()
            val booking = bookingDoc.toObject(Booking::class.java)
            
            if (booking == null) {
                return Result.failure(Exception("Booking not found"))
            }
            
            val batch = firestore.batch()
            val bookingRef = bookingsCollection.document(bookingId)
            
            val updateData = hashMapOf<String, Any>(
                "status" to BookingStatus.APPROVED.name,
                "updatedAt" to FieldValue.serverTimestamp()
            )
            
            if (driverId != null) {
                updateData["driverId"] = driverId
            }
            
            batch.update(bookingRef, updateData)
            batch.commit().await()
            
            // Send notification to user
            notificationService.notifyBookingStatusUpdate(
                userId = booking.userId,
                bookingId = bookingId,
                newStatus = BookingStatus.APPROVED.name,
                adminName = adminName
            )
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Reject a booking request
     */
    suspend fun rejectBooking(bookingId: String, adminName: String? = null): Result<Unit> {
        return try {
            // First get the booking to get the userId
            val bookingDoc = bookingsCollection.document(bookingId).get().await()
            val booking = bookingDoc.toObject(Booking::class.java)
            
            if (booking == null) {
                return Result.failure(Exception("Booking not found"))
            }
            
            val batch = firestore.batch()
            val bookingRef = bookingsCollection.document(bookingId)
            
            val updateData = hashMapOf<String, Any>(
                "status" to BookingStatus.REJECTED.name,
                "updatedAt" to FieldValue.serverTimestamp()
            )
            
            batch.update(bookingRef, updateData)
            batch.commit().await()
            
            // Send notification to user
            notificationService.notifyBookingStatusUpdate(
                userId = booking.userId,
                bookingId = bookingId,
                newStatus = BookingStatus.REJECTED.name,
                adminName = adminName
            )
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Mark booking as completed
     */
    suspend fun completeBooking(bookingId: String, adminName: String? = null): Result<Unit> {
        return try {
            // First get the booking to get the userId
            val bookingDoc = bookingsCollection.document(bookingId).get().await()
            val booking = bookingDoc.toObject(Booking::class.java)
            
            if (booking == null) {
                return Result.failure(Exception("Booking not found"))
            }
            
            val batch = firestore.batch()
            val bookingRef = bookingsCollection.document(bookingId)
            
            val updateData = hashMapOf<String, Any>(
                "status" to BookingStatus.COMPLETED.name,
                "updatedAt" to FieldValue.serverTimestamp()
            )
            
            batch.update(bookingRef, updateData)
            batch.commit().await()
            
            // Send notification to user
            notificationService.notifyBookingStatusUpdate(
                userId = booking.userId,
                bookingId = bookingId,
                newStatus = BookingStatus.COMPLETED.name,
                adminName = adminName
            )
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Cancel a booking
     */
    suspend fun cancelBooking(bookingId: String, adminName: String? = null): Result<Unit> {
        return try {
            // First get the booking to get the userId
            val bookingDoc = bookingsCollection.document(bookingId).get().await()
            val booking = bookingDoc.toObject(Booking::class.java)
            
            if (booking == null) {
                return Result.failure(Exception("Booking not found"))
            }
            
            val batch = firestore.batch()
            val bookingRef = bookingsCollection.document(bookingId)
            
            val updateData = hashMapOf<String, Any>(
                "status" to BookingStatus.CANCELLED.name,
                "updatedAt" to FieldValue.serverTimestamp()
            )
            
            batch.update(bookingRef, updateData)
            batch.commit().await()
            
            // Send notification to user
            notificationService.notifyBookingStatusUpdate(
                userId = booking.userId,
                bookingId = bookingId,
                newStatus = BookingStatus.CANCELLED.name,
                adminName = adminName
            )
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Create sample bookings for testing (admin only)
     */
    suspend fun createSampleBookings(): Result<Unit> {
        return try {
            val sampleBookings = listOf(
                hashMapOf<String, Any>(
                    "userId" to "sample_user_1",
                    "type" to BookingType.AIRPORT.name,
                    "pickupName" to "Matara",
                    "dropName" to "Colombo",
                    "rideType" to RideType.STANDARD.name,
                    "requestedAt" to FieldValue.serverTimestamp(),
                    "status" to BookingStatus.REQUESTED.name,
                    "fare" to 200.0
                ),
                hashMapOf<String, Any>(
                    "userId" to "sample_user_2",
                    "type" to BookingType.AIRPORT.name,
                    "pickupName" to "Matara",
                    "dropName" to "Katunayake",
                    "rideType" to RideType.VAN.name,
                    "requestedAt" to FieldValue.serverTimestamp(),
                    "status" to BookingStatus.REQUESTED.name,
                    "fare" to 120.0
                ),
                hashMapOf<String, Any>(
                    "userId" to "sample_user_3",
                    "type" to BookingType.HOTEL.name,
                    "pickupName" to "Colombo",
                    "dropName" to "Galle",
                    "rideType" to RideType.LUX.name,
                    "requestedAt" to FieldValue.serverTimestamp(),
                    "status" to BookingStatus.APPROVED.name,
                    "fare" to 150.0,
                    "driverId" to "sample_driver_1"
                )
            )

            val batch = firestore.batch()
            sampleBookings.forEach { booking ->
                val docRef = bookingsCollection.document()
                batch.set(docRef, booking)
            }
            
            // Also create sample users
            val sampleUsers = listOf(
                hashMapOf<String, Any>(
                    "email" to "user1@example.com",
                    "name" to "John Doe"
                ),
                hashMapOf<String, Any>(
                    "email" to "user2@example.com", 
                    "name" to "Jane Smith"
                ),
                hashMapOf<String, Any>(
                    "email" to "user3@example.com",
                    "name" to "Mike Johnson"
                )
            )

            sampleUsers.forEachIndexed { index, user ->
                val userRef = usersCollection.document("sample_user_${index + 1}")
                batch.set(userRef, user)
            }

            batch.commit().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
