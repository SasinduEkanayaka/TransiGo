package com.transigo.app.data.repository

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.transigo.app.data.model.BookingStatus
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

data class DashboardStats(
    val totalBookings: Int = 0,
    val bookingsToday: Int = 0,
    val completedThisMonth: Int = 0,
    val activeDrivers: Int = 0,
    val pendingRequestsCount: Int = 0
)

/**
 * Repository for admin dashboard statistics.
 * Handles aggregate queries for dashboard KPIs.
 */
@Singleton
class AdminDashboardRepository @Inject constructor(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    private val bookingsCollection = firestore.collection("bookings")
    private val driversCollection = firestore.collection("drivers")

    /**
     * Get all dashboard statistics
     */
    fun getDashboardStats(): Flow<Result<DashboardStats>> = flow {
        try {
            val stats = coroutineScope {
                val totalBookingsDeferred = async { getTotalBookings() }
                val bookingsTodayDeferred = async { getBookingsToday() }
                val completedThisMonthDeferred = async { getCompletedThisMonth() }
                val activeDriversDeferred = async { getActiveDrivers() }
                val pendingRequestsDeferred = async { getPendingRequests() }

                DashboardStats(
                    totalBookings = totalBookingsDeferred.await(),
                    bookingsToday = bookingsTodayDeferred.await(),
                    completedThisMonth = completedThisMonthDeferred.await(),
                    activeDrivers = activeDriversDeferred.await(),
                    pendingRequestsCount = pendingRequestsDeferred.await()
                )
            }

            emit(Result.success(stats))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    /**
     * Get total bookings count (all time)
     */
    private suspend fun getTotalBookings(): Int {
        return try {
            val snapshot = bookingsCollection.get().await()
            snapshot.size()
        } catch (e: Exception) {
            0
        }
    }

    /**
     * Get bookings count for today
     * TODO: Create composite index in Firestore:
     * Collection: bookings
     * Fields: requestedAt (Ascending), __name__ (Ascending)
     */
    private suspend fun getBookingsToday(): Int {
        return try {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            val startOfDay = Timestamp(calendar.time)

            calendar.add(Calendar.DAY_OF_MONTH, 1)
            val startOfNextDay = Timestamp(calendar.time)

            val snapshot = bookingsCollection
                .whereGreaterThanOrEqualTo("requestedAt", startOfDay)
                .whereLessThan("requestedAt", startOfNextDay)
                .get()
                .await()

            snapshot.size()
        } catch (e: Exception) {
            0
        }
    }

    /**
     * Get completed bookings count for current month
     * TODO: Create composite index in Firestore:
     * Collection: bookings
     * Fields: status (Ascending), requestedAt (Ascending), __name__ (Ascending)
     */
    private suspend fun getCompletedThisMonth(): Int {
        return try {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            val startOfMonth = Timestamp(calendar.time)

            calendar.add(Calendar.MONTH, 1)
            val startOfNextMonth = Timestamp(calendar.time)

            val snapshot = bookingsCollection
                .whereEqualTo("status", BookingStatus.COMPLETED.name)
                .whereGreaterThanOrEqualTo("requestedAt", startOfMonth)
                .whereLessThan("requestedAt", startOfNextMonth)
                .get()
                .await()

            snapshot.size()
        } catch (e: Exception) {
            0
        }
    }

    /**
     * Get active drivers count
     * TODO: Create index in Firestore:
     * Collection: drivers
     * Fields: isActive (Ascending), __name__ (Ascending)
     */
    private suspend fun getActiveDrivers(): Int {
        return try {
            val snapshot = driversCollection
                .whereEqualTo("isActive", true)
                .get()
                .await()

            snapshot.size()
        } catch (e: Exception) {
            0
        }
    }

    /**
     * Get pending requests count
     * TODO: Create index in Firestore:
     * Collection: bookings
     * Fields: status (Ascending), __name__ (Ascending)
     */
    private suspend fun getPendingRequests(): Int {
        return try {
            val snapshot = bookingsCollection
                .whereEqualTo("status", BookingStatus.REQUESTED.name)
                .get()
                .await()

            snapshot.size()
        } catch (e: Exception) {
            0
        }
    }
}
