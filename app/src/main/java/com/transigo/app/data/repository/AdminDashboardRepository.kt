package com.transigo.app.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.transigo.app.data.model.BookingStatus
import com.transigo.app.data.model.DashboardStats
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdminDashboardRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val bookingsCollection = firestore.collection("bookings")
    private val driversCollection = firestore.collection("drivers")

    fun getDashboardStats(): Flow<Result<DashboardStats>> = flow {
        try {
            // Calculate date ranges
            val now = Calendar.getInstance()
            val startOfDay = now.clone() as Calendar
            startOfDay.set(Calendar.HOUR_OF_DAY, 0)
            startOfDay.set(Calendar.MINUTE, 0)
            startOfDay.set(Calendar.SECOND, 0)
            startOfDay.set(Calendar.MILLISECOND, 0)

            val startOfMonth = now.clone() as Calendar
            startOfMonth.set(Calendar.DAY_OF_MONTH, 1)
            startOfMonth.set(Calendar.HOUR_OF_DAY, 0)
            startOfMonth.set(Calendar.MINUTE, 0)
            startOfMonth.set(Calendar.SECOND, 0)
            startOfMonth.set(Calendar.MILLISECOND, 0)

            // Total bookings
            val totalBookings = bookingsCollection.get().await().size()

            // Bookings today (requestedAt >= startOfDay)
            val todayBookings = bookingsCollection
                .whereGreaterThanOrEqualTo("requestedAt", com.google.firebase.Timestamp(startOfDay.time))
                .get()
                .await()
                .size()

            // Completed this month
            val completedThisMonth = bookingsCollection
                .whereEqualTo("status", BookingStatus.COMPLETED.name)
                .whereGreaterThanOrEqualTo("requestedAt", com.google.firebase.Timestamp(startOfMonth.time))
                .get()
                .await()
                .size()

            // Active drivers
            val activeDrivers = driversCollection
                .whereEqualTo("isActive", true)
                .get()
                .await()
                .size()

            // Pending requests
            val pendingRequests = bookingsCollection
                .whereEqualTo("status", BookingStatus.REQUESTED.name)
                .get()
                .await()
                .size()

            emit(
                Result.success(
                    DashboardStats(
                        totalBookings = totalBookings,
                        bookingsToday = todayBookings,
                        completedThisMonth = completedThisMonth,
                        activeDrivers = activeDrivers,
                        pendingRequestsCount = pendingRequests
                    )
                )
            )
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}
