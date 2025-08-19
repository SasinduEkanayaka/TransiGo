package com.transigo.app.core.service

import com.google.firebase.firestore.FirebaseFirestore
import com.transigo.app.data.model.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Service for sending push notifications to users.
 * This service would typically make HTTP calls to your backend/Cloud Functions.
 * For now, it includes the logic structure that your backend would implement.
 */
@Singleton
class NotificationService @Inject constructor(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    private val usersCollection = firestore.collection("users")
    
    /**
     * Triggers a notification to be sent to a user when booking status changes.
     * In a real implementation, this would call your backend API or Cloud Function.
     * 
     * @param userId The ID of the user to notify
     * @param bookingId The ID of the booking that was updated
     * @param newStatus The new status of the booking
     * @param adminName Optional name of the admin who made the change
     */
    suspend fun notifyBookingStatusUpdate(
        userId: String,
        bookingId: String,
        newStatus: String,
        adminName: String? = null
    ): Result<Unit> {
        return try {
            // Get user to check notification preferences and FCM token
            val userDoc = usersCollection.document(userId).get().await()
            val user = userDoc.toObject(User::class.java)
            
            if (user == null) {
                return Result.failure(Exception("User not found"))
            }
            
            // Check if user has notifications enabled
            if (!user.notificationsEnabled) {
                return Result.success(Unit) // Skip sending notification
            }
            
            // Check if user has an FCM token
            if (user.fcmToken.isEmpty()) {
                return Result.failure(Exception("User has no FCM token"))
            }
            
            // Here you would typically make an HTTP call to your backend/Cloud Function
            // to send the actual push notification. The backend would use the FCM Admin SDK.
            // For example:
            // httpClient.post("/api/send-notification") {
            //     body = NotificationRequest(
            //         fcmToken = user.fcmToken,
            //         title = getNotificationTitle(newStatus),
            //         body = getNotificationBody(newStatus, adminName),
            //         data = mapOf("bookingId" to bookingId)
            //     )
            // }
            
            // For now, we'll just log what would be sent
            val title = getNotificationTitle(newStatus)
            val body = getNotificationBody(newStatus, adminName)
            
            println("Would send notification to ${user.email}:")
            println("Title: $title")
            println("Body: $body")
            println("BookingId: $bookingId")
            println("FCM Token: ${user.fcmToken}")
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Gets the notification title based on booking status
     */
    private fun getNotificationTitle(status: String): String {
        return when (status.uppercase()) {
            "APPROVED" -> "Booking Approved! 🎉"
            "REJECTED" -> "Booking Update"
            "COMPLETED" -> "Trip Completed"
            "CANCELLED" -> "Booking Cancelled"
            else -> "Booking Update"
        }
    }
    
    /**
     * Gets the notification body based on booking status
     */
    private fun getNotificationBody(status: String, adminName: String?): String {
        val adminText = adminName?.let { " by $it" } ?: ""
        return when (status.uppercase()) {
            "APPROVED" -> "Your booking has been approved$adminText. Get ready for your trip!"
            "REJECTED" -> "Your booking has been rejected$adminText. Please contact support if you have questions."
            "COMPLETED" -> "Your trip has been completed$adminText. Thank you for using TransiGo!"
            "CANCELLED" -> "Your booking has been cancelled$adminText."
            else -> "Your booking status has been updated to $status$adminText."
        }
    }
}
