package com.transigo.app.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.transigo.app.data.model.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for managing user data operations with Firestore.
 * Handles user profile creation, retrieval, and updates.
 */
@Singleton
class UserRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val usersCollection = firestore.collection("users")

    /**
     * Saves a user profile to Firestore.
     * Creates a document at /users/{userId} with the user data.
     * @param user User object to save
     * @return Result indicating success or failure
     */
    suspend fun saveUser(user: User): Result<Unit> {
        return try {
            usersCollection.document(user.id).set(user).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Retrieves a user profile from Firestore.
     * @param userId The user's unique identifier
     * @return Result containing the User object or null if not found
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
     * Updates a user profile in Firestore.
     * @param user Updated User object
     * @return Result indicating success or failure
     */
    suspend fun updateUser(user: User): Result<Unit> {
        return try {
            usersCollection.document(user.id).set(user).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Updates specific fields of a user profile.
     * Useful for partial updates like FCM token updates.
     * @param userId User's unique identifier
     * @param updates Map of field names to new values
     * @return Result indicating success or failure
     */
    suspend fun updateUserFields(userId: String, updates: Map<String, Any>): Result<Unit> {
        return try {
            usersCollection.document(userId).update(updates).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Updates the FCM token for a user.
     * This is typically called when the FCM token refreshes.
     * @param userId User's unique identifier
     * @param fcmToken New FCM token
     * @return Result indicating success or failure
     */
    suspend fun updateFcmToken(userId: String, fcmToken: String): Result<Unit> {
        return updateUserFields(userId, mapOf("fcmToken" to fcmToken))
    }
}
