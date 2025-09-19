package com.transigo.app.profile;

/**
 * ViewModel for managing user profile operations.
 * Handles profile loading, updates, and FCM token management.
 */
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u0012\u001a\u00020\u0013J\u0006\u0010\u0014\u001a\u00020\u0013J\u0006\u0010\u0015\u001a\u00020\u0013J\u000e\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u0018J\b\u0010\u0019\u001a\u00020\u0013H\u0002J\u000e\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020\u001cJ\u0016\u0010\u001d\u001a\u00020\u00132\u0006\u0010\u001e\u001a\u00020\u00182\u0006\u0010\u001f\u001a\u00020\u0018R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0019\u0010\u0010\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000f\u00a8\u0006 "}, d2 = {"Lcom/transigo/app/profile/ProfileViewModel;", "Landroidx/lifecycle/ViewModel;", "profileRepository", "Lcom/transigo/app/data/repository/ProfileRepository;", "fcmTokenService", "Lcom/transigo/app/core/service/FcmTokenService;", "(Lcom/transigo/app/data/repository/ProfileRepository;Lcom/transigo/app/core/service/FcmTokenService;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/transigo/app/profile/ProfileUiState;", "_user", "Lcom/transigo/app/data/model/User;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "user", "getUser", "clearError", "", "clearUpdateSuccess", "loadProfile", "updateFcmToken", "token", "", "updateFcmTokenIfAvailable", "updateNotificationPreference", "enabled", "", "updateProfile", "fullName", "phone", "app_release"})
@dagger.hilt.android.lifecycle.HiltViewModel
public final class ProfileViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.transigo.app.data.repository.ProfileRepository profileRepository = null;
    @org.jetbrains.annotations.NotNull
    private final com.transigo.app.core.service.FcmTokenService fcmTokenService = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.transigo.app.profile.ProfileUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.transigo.app.profile.ProfileUiState> uiState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.transigo.app.data.model.User> _user = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.transigo.app.data.model.User> user = null;
    
    @javax.inject.Inject
    public ProfileViewModel(@org.jetbrains.annotations.NotNull
    com.transigo.app.data.repository.ProfileRepository profileRepository, @org.jetbrains.annotations.NotNull
    com.transigo.app.core.service.FcmTokenService fcmTokenService) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.transigo.app.profile.ProfileUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.transigo.app.data.model.User> getUser() {
        return null;
    }
    
    /**
     * Loads the current user's profile.
     */
    public final void loadProfile() {
    }
    
    /**
     * Updates the user's profile information.
     * @param fullName Updated full name
     * @param phone Updated phone number
     */
    public final void updateProfile(@org.jetbrains.annotations.NotNull
    java.lang.String fullName, @org.jetbrains.annotations.NotNull
    java.lang.String phone) {
    }
    
    /**
     * Updates the FCM token for push notifications.
     * @param token FCM token to store
     */
    public final void updateFcmToken(@org.jetbrains.annotations.NotNull
    java.lang.String token) {
    }
    
    /**
     * Clears the update success state.
     */
    public final void clearUpdateSuccess() {
    }
    
    /**
     * Updates the notification preference for the current user.
     * @param enabled Whether notifications should be enabled
     */
    public final void updateNotificationPreference(boolean enabled) {
    }
    
    /**
     * Clears any error state.
     */
    public final void clearError() {
    }
    
    /**
     * Updates the FCM token if available.
     * This is automatically called when the ViewModel is initialized.
     */
    private final void updateFcmTokenIfAvailable() {
    }
}