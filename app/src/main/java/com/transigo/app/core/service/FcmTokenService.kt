package com.transigo.app.core.service

import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Service for managing Firebase Cloud Messaging tokens.
 */
@Singleton
class FcmTokenService @Inject constructor(
    private val firebaseMessaging: FirebaseMessaging = FirebaseMessaging.getInstance()
) {

    /**
     * Gets the current FCM token.
     * @return FCM token or null if unable to retrieve
     */
    suspend fun getFcmToken(): String? {
        return try {
            firebaseMessaging.token.await()
        } catch (e: Exception) {
            println("Failed to get FCM token: ${e.message}")
            null
        }
    }

    /**
     * Subscribes to a topic for push notifications.
     * @param topic The topic name to subscribe to
     */
    suspend fun subscribeToTopic(topic: String) {
        try {
            firebaseMessaging.subscribeToTopic(topic).await()
        } catch (e: Exception) {
            println("Failed to subscribe to topic $topic: ${e.message}")
        }
    }

    /**
     * Unsubscribes from a topic.
     * @param topic The topic name to unsubscribe from
     */
    suspend fun unsubscribeFromTopic(topic: String) {
        try {
            firebaseMessaging.unsubscribeFromTopic(topic).await()
        } catch (e: Exception) {
            println("Failed to unsubscribe from topic $topic: ${e.message}")
        }
    }
}
