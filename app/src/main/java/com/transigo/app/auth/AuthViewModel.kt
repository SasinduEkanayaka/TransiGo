package com.transigo.app.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.transigo.app.data.model.User
import com.transigo.app.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for managing authentication state and operations.
 * Exposes loading state, error messages, and current user information.
 */
class AuthViewModel(
    private val authRepository: AuthRepository = AuthRepository()
) : ViewModel() {

    // Private mutable state flows
    private val _isLoading = MutableStateFlow(false)
    private val _error = MutableStateFlow<String?>(null)
    private val _user = MutableStateFlow<User?>(null)

    // Public read-only state flows
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    val error: StateFlow<String?> = _error.asStateFlow()
    val user: StateFlow<User?> = _user.asStateFlow()

    init {
        // Start listening to authentication state changes
        observeAuthState()
    }

    /**
     * Observes the current user authentication state and updates the user StateFlow.
     * This will automatically update the UI when authentication state changes.
     */
    private fun observeAuthState() {
        viewModelScope.launch {
            authRepository.currentUserFlow().collect { user ->
                _user.value = user
            }
        }
    }

    /**
     * Registers a new user with email, password, and full name.
     * Updates loading state and handles errors appropriately.
     * 
     * @param email User's email address
     * @param password User's password
     * @param fullName User's full name
     * @param onSuccess Callback invoked when registration succeeds
     */
    fun register(
        email: String,
        password: String,
        fullName: String,
        onSuccess: () -> Unit = {}
    ) {
        // Validate input
        if (!isValidEmail(email)) {
            _error.value = "Please enter a valid email address"
            return
        }
        
        if (!isValidPassword(password)) {
            _error.value = "Password must be at least 6 characters long"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            authRepository.register(email.trim(), password, fullName.trim())
                .onSuccess { user ->
                    _user.value = user
                    _isLoading.value = false
                    onSuccess()
                }
                .onFailure { exception ->
                    _error.value = getErrorMessage(exception)
                    _isLoading.value = false
                }
        }
    }

    /**
     * Authenticates user with email and password.
     * Updates loading state and handles errors appropriately.
     * 
     * @param email User's email address
     * @param password User's password
     * @param onSuccess Callback invoked when login succeeds
     */
    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit = {}
    ) {
        // Validate input
        if (!isValidEmail(email)) {
            _error.value = "Please enter a valid email address"
            return
        }
        
        if (password.isEmpty()) {
            _error.value = "Please enter your password"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            authRepository.login(email.trim(), password)
                .onSuccess { user ->
                    _user.value = user
                    _isLoading.value = false
                    onSuccess()
                }
                .onFailure { exception ->
                    _error.value = getErrorMessage(exception)
                    _isLoading.value = false
                }
        }
    }

    /**
     * Signs out the current user.
     * Clears user state and handles any errors.
     */
    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            _user.value = null
            _error.value = null
        }
    }

    /**
     * Sends a password reset email to the specified email address.
     * 
     * @param email Email address to send reset link to
     * @param onSuccess Callback invoked when reset email is sent successfully
     */
    fun resetPassword(
        email: String,
        onSuccess: () -> Unit = {}
    ) {
        if (!isValidEmail(email)) {
            _error.value = "Please enter a valid email address"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            authRepository.resetPassword(email.trim())
                .onSuccess {
                    _isLoading.value = false
                    onSuccess()
                }
                .onFailure { exception ->
                    _error.value = getErrorMessage(exception)
                    _isLoading.value = false
                }
        }
    }

    /**
     * Clears any current error message.
     * Useful for dismissing error dialogs or messages.
     */
    fun clearError() {
        _error.value = null
    }

    /**
     * Checks if the current user is authenticated.
     * @return true if user is authenticated, false otherwise
     */
    fun isAuthenticated(): Boolean {
        return _user.value != null
    }

    /**
     * Validates email format using a simple regex pattern.
     * @param email Email string to validate
     * @return true if email format is valid, false otherwise
     */
    private fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && 
               android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    /**
     * Validates password meets minimum requirements.
     * @param password Password string to validate
     * @return true if password is valid, false otherwise
     */
    private fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

    /**
     * Converts various exception types to user-friendly error messages.
     * @param exception The exception to convert
     * @return User-friendly error message
     */
    private fun getErrorMessage(exception: Throwable): String {
        return when {
            exception.message?.contains("network") == true ->
                "Network error. Please check your connection."
            exception.message?.contains("email") == true ->
                "Invalid email address."
            exception.message?.contains("password") == true ->
                "Invalid password."
            else -> exception.message ?: "An unknown error occurred"
        }
    }
}
