package com.transigo.app.data.repository

import com.transigo.app.data.model.User
import com.transigo.app.data.model.UserType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*

/**
 * Mock AuthRepository for demonstration purposes.
 * This will be replaced with Firebase implementation later.
 */
class AuthRepository {
    
    // Mock storage for demo purposes
    private val users = mutableMapOf<String, User>()
    private val _currentUser = MutableStateFlow<User?>(null)
    
    /**
     * Mock registration method for demo purposes
     */
    suspend fun register(
        email: String, 
        password: String, 
        fullName: String = ""
    ): Result<User> {
        return try {
            // Simulate network delay
            delay(1000)
            
            // Check if user already exists
            if (users.values.any { it.email == email }) {
                return Result.failure(Exception("An account with this email already exists"))
            }
            
            val userId = UUID.randomUUID().toString()
            val user = User(
                id = userId,
                email = email,
                name = fullName,
                phoneNumber = "",
                userType = UserType.CUSTOMER,
                createdAt = System.currentTimeMillis()
            )
            
            // Store user (mock)
            users[userId] = user
            _currentUser.value = user
            
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Mock login method for demo purposes
     */
    suspend fun login(email: String, password: String): Result<User> {
        return try {
            // Simulate network delay
            delay(1000)
            
            val user = users.values.find { it.email == email }
                ?: return Result.failure(Exception("No account found with this email address"))
            
            _currentUser.value = user
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Mock logout method
     */
    fun logout(): Result<Unit> {
        return try {
            _currentUser.value = null
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Mock password reset method
     */
    suspend fun resetPassword(email: String): Result<Unit> {
        return try {
            delay(500)
            // In real implementation, this would send email
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Mock current user flow
     */
    fun currentUserFlow(): Flow<User?> = _currentUser.asStateFlow()

    /**
     * Mock authentication check
     */
    fun isUserAuthenticated(): Boolean = _currentUser.value != null
}
