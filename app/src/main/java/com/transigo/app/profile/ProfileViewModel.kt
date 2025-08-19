package com.transigo.app.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.transigo.app.data.model.User
import com.transigo.app.data.repository.ProfileRepository
import com.transigo.app.core.service.FcmTokenService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing user profile operations.
 * Handles profile loading, updates, and FCM token management.
 */
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val fcmTokenService: FcmTokenService
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    init {
        loadProfile()
        updateFcmTokenIfAvailable()
    }

    /**
     * Loads the current user's profile.
     */
    fun loadProfile() {
        val userId = profileRepository.getCurrentUserId()
        if (userId == null) {
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                error = "User not authenticated"
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            profileRepository.getProfile(userId)
                .onSuccess { user ->
                    _user.value = user
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = null
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Failed to load profile: ${exception.message}"
                    )
                }
        }
    }

    /**
     * Updates the user's profile information.
     * @param fullName Updated full name
     * @param phone Updated phone number
     */
    fun updateProfile(fullName: String, phone: String) {
        val userId = profileRepository.getCurrentUserId()
        if (userId == null) {
            _uiState.value = _uiState.value.copy(error = "User not authenticated")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val updates = mapOf(
                "name" to fullName.trim(),
                "phoneNumber" to phone.trim()
            )

            profileRepository.updateProfile(userId, updates)
                .onSuccess {
                    // Update the local user state
                    _user.value = _user.value?.copy(
                        name = fullName.trim(),
                        phoneNumber = phone.trim()
                    )
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = null,
                        isUpdateSuccessful = true
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Failed to update profile: ${exception.message}"
                    )
                }
        }
    }

    /**
     * Updates the FCM token for push notifications.
     * @param token FCM token to store
     */
    fun updateFcmToken(token: String) {
        val userId = profileRepository.getCurrentUserId()
        if (userId == null) return

        viewModelScope.launch {
            profileRepository.updateFcmToken(userId, token)
                .onSuccess {
                    // Update the local user state
                    _user.value = _user.value?.copy(fcmToken = token)
                }
                .onFailure { exception ->
                    // Log error but don't show to user as this is background operation
                    println("Failed to update FCM token: ${exception.message}")
                }
        }
    }

    /**
     * Clears the update success state.
     */
    fun clearUpdateSuccess() {
        _uiState.value = _uiState.value.copy(isUpdateSuccessful = false)
    }
    
    /**
     * Updates the notification preference for the current user.
     * @param enabled Whether notifications should be enabled
     */
    fun updateNotificationPreference(enabled: Boolean) {
        val userId = profileRepository.getCurrentUserId()
        if (userId == null) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            profileRepository.updateNotificationPreference(userId, enabled)
                .onSuccess {
                    // Update the local user state
                    _user.value = _user.value?.copy(notificationsEnabled = enabled)
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isUpdateSuccessful = true
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Failed to update notification preference: ${exception.message}"
                    )
                }
        }
    }

    /**
     * Clears any error state.
     */
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    /**
     * Updates the FCM token if available.
     * This is automatically called when the ViewModel is initialized.
     */
    private fun updateFcmTokenIfAvailable() {
        val userId = profileRepository.getCurrentUserId()
        if (userId == null) return

        viewModelScope.launch {
            fcmTokenService.getFcmToken()?.let { token ->
                updateFcmToken(token)
            }
        }
    }
}

/**
 * UI state for the profile screen.
 */
data class ProfileUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isUpdateSuccessful: Boolean = false
)
