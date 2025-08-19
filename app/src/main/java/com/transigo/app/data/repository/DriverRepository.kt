package com.transigo.app.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.transigo.app.data.model.Driver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for driver operations.
 * Handles driver management and queries.
 */
@Singleton
class DriverRepository @Inject constructor(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    private val driversCollection = firestore.collection("drivers")

    /**
     * Get all drivers
     */
    fun getAllDrivers(): Flow<Result<List<Driver>>> = flow {
        try {
            val snapshot = driversCollection.get().await()
            val drivers = snapshot.documents.mapNotNull { doc ->
                doc.toObject(Driver::class.java)?.copy(id = doc.id)
            }
            emit(Result.success(drivers))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    /**
     * Get active drivers
     */
    fun getActiveDrivers(): Flow<Result<List<Driver>>> = flow {
        try {
            val snapshot = driversCollection
                .whereEqualTo("isActive", true)
                .get()
                .await()
            
            val drivers = snapshot.documents.mapNotNull { doc ->
                doc.toObject(Driver::class.java)?.copy(id = doc.id)
            }
            emit(Result.success(drivers))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    /**
     * Get driver by ID
     */
    suspend fun getDriverById(driverId: String): Result<Driver?> {
        return try {
            val document = driversCollection.document(driverId).get().await()
            val driver = document.toObject(Driver::class.java)?.copy(id = document.id)
            Result.success(driver)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Update driver status
     */
    suspend fun updateDriverStatus(driverId: String, isActive: Boolean): Result<Unit> {
        return try {
            driversCollection.document(driverId)
                .update("isActive", isActive)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
