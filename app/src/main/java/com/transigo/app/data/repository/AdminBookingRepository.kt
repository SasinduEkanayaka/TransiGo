package com.transigo.app.data.repository

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
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
                .orderBy("requestedAt", Query.Direction.DESCENDING)
                .get()
                .await()

            val bookings = snapshot.documents.mapNotNull { doc ->
                doc.toObject(Booking::class.java)?.copy(id = doc.id)
            }
            
            emit(Result.success(bookings))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    /**
     * Get all bookings for admin overview
     */
    fun getAllBookings(): Flow<Result<List<Booking>>> = flow {
        try {
            val snapshot = bookingsCollection
                .orderBy("requestedAt", Query.Direction.DESCENDING)
                .get()
                .await()

            val bookings = snapshot.documents.mapNotNull { doc ->
                doc.toObject(Booking::class.java)?.copy(id = doc.id)
            }
            
            emit(Result.success(bookings))
        } catch (e: Exception) {
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
}
