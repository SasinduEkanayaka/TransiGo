package com.transigo.app.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.transigo.app.data.model.User
import com.transigo.app.data.model.UserType
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

/**
 * Repository for handling authentication operations with Firebase Auth and Firestore.
 * Manages user authentication, registration, and profile creation.
 */
class AuthRepository(
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    private val usersCollection = firestore.collection("users")

    /**
     * Registers a new user with email and password, then creates their profile in Firestore.
     * @param email User's email address
     * @param password User's password
     * @param fullName User's full name (optional, defaults to empty string)
     * @return Result containing the created User object or an error
     */
    suspend fun register(
        email: String, 
        password: String, 
        fullName: String = ""
    ): Result<User> {
        return try {
            // Create user account with Firebase Auth
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user
                ?: return Result.failure(Exception("Failed to create user account"))

            // Create user profile object
            val user = User(
                id = firebaseUser.uid,
                email = email,
                name = fullName,
                phoneNumber = "",
                userType = UserType.CUSTOMER, // Default role is customer
                createdAt = System.currentTimeMillis()
            )

            // Save user profile to Firestore at /users/{uid}
            usersCollection.document(firebaseUser.uid).set(user).await()

            Result.success(user)
        } catch (e: Exception) {
            // If Firestore save fails but auth succeeded, we should clean up
            firebaseAuth.currentUser?.delete()
            Result.failure(e)
        }
    }

    /**
     * Authenticates user with email and password.
     * @param email User's email address
     * @param password User's password
     * @return Result containing the User object from Firestore or an error
     */
    suspend fun login(email: String, password: String): Result<User> {
        return try {
            // Authenticate with Firebase Auth
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user
                ?: return Result.failure(Exception("Authentication failed"))

            // Fetch user profile from Firestore
            val userDoc = usersCollection.document(firebaseUser.uid).get().await()
            val user = userDoc.toObject(User::class.java)
                ?: return Result.failure(Exception("User profile not found"))

            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Signs out the current user from Firebase Auth.
     * @return Result indicating success or failure
     */
    fun logout(): Result<Unit> {
        return try {
            firebaseAuth.signOut()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Sends a password reset email to the specified email address.
     * @param email Email address to send reset link to
     * @return Result indicating success or failure
     */
    suspend fun resetPassword(email: String): Result<Unit> {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Provides a Flow that emits the current user state.
     * Emits User object when authenticated, null when not authenticated.
     * @return Flow<User?> representing current authentication state
     */
    fun currentUserFlow(): Flow<User?> = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            val firebaseUser = auth.currentUser
            if (firebaseUser != null) {
                // User is authenticated, fetch their profile from Firestore
                usersCollection.document(firebaseUser.uid)
                    .addSnapshotListener { snapshot, error ->
                        if (error != null) {
                            // Error fetching user profile
                            trySend(null)
                            return@addSnapshotListener
                        }
                        
                        val user = snapshot?.toObject(User::class.java)
                        trySend(user)
                    }
            } else {
                // User is not authenticated
                trySend(null)
            }
        }

        firebaseAuth.addAuthStateListener(authStateListener)
        
        awaitClose {
            firebaseAuth.removeAuthStateListener(authStateListener)
        }
    }

    /**
     * Gets the current authenticated user synchronously.
     * @return Current FirebaseUser or null if not authenticated
     */
    fun getCurrentFirebaseUser(): FirebaseUser? = firebaseAuth.currentUser

    /**
     * Checks if a user is currently authenticated.
     * @return true if user is authenticated, false otherwise
     */
    fun isUserAuthenticated(): Boolean = firebaseAuth.currentUser != null
}
