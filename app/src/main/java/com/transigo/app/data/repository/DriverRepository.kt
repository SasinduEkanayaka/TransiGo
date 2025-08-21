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
                "phoneNumber" to driver.phoneNumber,
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
