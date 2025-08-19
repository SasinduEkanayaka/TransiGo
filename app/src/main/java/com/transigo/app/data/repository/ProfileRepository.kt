package com.transigo.app.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.transigo.app.data.model.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for managing user profile operations.
 * Provides methods for profile retrieval, updates, and FCM token management.
 */
@Singleton
class ProfileRepository @Inject constructor(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {
    private val usersCollection = firestore.collection("users")

    /**
     * Gets the current user's profile from Firestore.
     * @param uid User's unique identifier
     * @return Result containing the User object or error
     */
    suspend fun getProfile(uid: String): Result<User?> {
        return try {
            val document = usersCollection.document(uid).get().await()
            val user = document.toObject(User::class.java)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Updates user profile with the provided data.
     * @param uid User's unique identifier
     * @param updates Map of field names to new values
     * @return Result indicating success or failure
     */
    suspend fun updateProfile(uid: String, updates: Map<String, Any>): Result<Unit> {
        return try {
            usersCollection.document(uid).update(updates).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Updates the FCM token for the user.
     * @param uid User's unique identifier
     * @param token FCM token to store
     * @return Result indicating success or failure
     */
    suspend fun updateFcmToken(uid: String, token: String): Result<Unit> {
        return try {
            usersCollection.document(uid).update(mapOf("fcmToken" to token)).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Gets the current user's ID from Firebase Auth.
     * @return Current user's UID or null if not authenticated
     */
    fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }
    
    /**
     * Updates the notification preference for the user.
     * @param uid User's unique identifier
     * @param enabled Whether notifications should be enabled
     * @return Result indicating success or failure
     */
    suspend fun updateNotificationPreference(uid: String, enabled: Boolean): Result<Unit> {
        return try {
            usersCollection.document(uid).update(mapOf("notificationsEnabled" to enabled)).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
