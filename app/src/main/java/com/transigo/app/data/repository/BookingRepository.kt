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

// DriverRepository - TODO: Move to separate file
package com.transigo.app.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.transigo.app.data.model.Driver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.channels.awaitClose
import com.google.firebase.firestore.FieldValue

@Singleton
class DriverRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val driversCollection = firestore.collection("drivers")

    fun getDriversFlow(): Flow<List<Driver>> = callbackFlow {
        val listenerRegistration = driversCollection
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                
                val drivers = snapshot?.toObjects(Driver::class.java) ?: emptyList()
                trySend(drivers)
            }
        
        awaitClose { listenerRegistration.remove() }
    }

    fun getActiveDriversFlow(): Flow<List<Driver>> = getDriversFlow().map { drivers ->
        drivers.filter { it.isActive }
    }

    suspend fun addDriver(driver: Driver): Result<String> {
        return try {
            val docRef = driversCollection.add(
                driver.copy(
                    createdAt = com.google.firebase.Timestamp.now(),
                    updatedAt = com.google.firebase.Timestamp.now()
                )
            ).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateDriver(driverId: String, driver: Driver): Result<Unit> {
        return try {
            val driverMap = mapOf(
                "fullName" to driver.fullName,
                "phone" to driver.phone,
                "vehicleType" to driver.vehicleType.toString(),
                "vehicleNumber" to driver.vehicleNumber,
                "isActive" to driver.isActive,
                "updatedAt" to com.google.firebase.Timestamp.now()
            )
            
            driversCollection.document(driverId).update(driverMap).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteDriver(driverId: String): Result<Unit> {
        return try {
            driversCollection.document(driverId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getDriver(driverId: String): Result<Driver?> {
        return try {
            val snapshot = driversCollection.document(driverId).get().await()
            val driver = snapshot.toObject(Driver::class.java)
            Result.success(driver)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
