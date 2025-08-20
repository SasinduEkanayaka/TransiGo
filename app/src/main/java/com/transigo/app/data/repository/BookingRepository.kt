package com.transigo.app.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ServerTimestamp
import com.google.firebase.firestore.FieldValue
import com.transigo.app.data.model.Booking
import com.transigo.app.data.model.BookingType
import com.transigo.app.data.model.RideType
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

data class BookingForm(
    val type: BookingType,
    val pickupName: String,
    val dropName: String,
    val rideType: RideType,
    val scheduledAt: Timestamp? = null
)

class BookingRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    private val bookingsCollection = firestore.collection("bookings")

    suspend fun createBooking(userId: String, form: BookingForm): Result<String> {
        return try {
            val booking = hashMapOf(
                "userId" to userId,
                "type" to form.type.name,
                "pickupName" to form.pickupName,
                "dropName" to form.dropName,
                "rideType" to form.rideType.name,
                "requestedAt" to FieldValue.serverTimestamp(),
                "scheduledAt" to form.scheduledAt,
                "status" to "REQUESTED",
                "driverId" to null,
                "rating" to hashMapOf(
                    "stars" to 0,
                    "comment" to "",
                    "ratedAt" to null
                )
            )
            
            val documentRef = bookingsCollection.add(booking).await()
            Result.success(documentRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun myBookingsQuery(userId: String): Flow<Result<List<Booking>>> = flow {
        try {
            val querySnapshot = bookingsCollection
                .whereEqualTo("userId", userId)
                .orderBy("requestedAt", Query.Direction.DESCENDING)
                .get()
                .await()
            
            val bookings = querySnapshot.documents.mapNotNull { document ->
                document.toObject(Booking::class.java)?.copy(id = document.id)
            }
            emit(Result.success(bookings))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    suspend fun getBooking(bookingId: String): Result<Booking?> {
        return try {
            val document = bookingsCollection.document(bookingId).get().await()
            val booking = document.toObject(Booking::class.java)?.copy(id = document.id)
            Result.success(booking)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateBookingStatus(bookingId: String, status: com.transigo.app.data.model.BookingStatus): Result<Unit> {
        return try {
            bookingsCollection.document(bookingId)
                .update("status", status.name)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun rateBooking(bookingId: String, stars: Int, comment: String): Result<Unit> {
        return try {
            bookingsCollection.document(bookingId)
                .update(
                    mapOf(
                        "rating.stars" to stars,
                        "rating.comment" to comment,
                        "rating.ratedAt" to FieldValue.serverTimestamp()
                    )
                )
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
