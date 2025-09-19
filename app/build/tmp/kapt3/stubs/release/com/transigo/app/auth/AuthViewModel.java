package com.transigo.app.auth;

/**
 * ViewModel for managing authentication state and operations.
 * Exposes loading state, error messages, and current user information.
 */
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0013\u001a\u00020\u0014J\u0010\u0010\u0015\u001a\u00020\u00072\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\u0006\u0010\u0018\u001a\u00020\tJ\u0010\u0010\u0019\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\u0007H\u0002J\u0010\u0010\u001b\u001a\u00020\t2\u0006\u0010\u001c\u001a\u00020\u0007H\u0002J&\u0010\u001d\u001a\u00020\u00142\u0006\u0010\u001a\u001a\u00020\u00072\u0006\u0010\u001c\u001a\u00020\u00072\u000e\b\u0002\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00140\u001fJ\u0006\u0010 \u001a\u00020\u0014J\b\u0010!\u001a\u00020\u0014H\u0002J.\u0010\"\u001a\u00020\u00142\u0006\u0010\u001a\u001a\u00020\u00072\u0006\u0010\u001c\u001a\u00020\u00072\u0006\u0010#\u001a\u00020\u00072\u000e\b\u0002\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00140\u001fJ\u001e\u0010$\u001a\u00020\u00142\u0006\u0010\u001a\u001a\u00020\u00072\u000e\b\u0002\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00140\u001fR\u0016\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\t0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0019\u0010\u0011\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000f\u00a8\u0006%"}, d2 = {"Lcom/transigo/app/auth/AuthViewModel;", "Landroidx/lifecycle/ViewModel;", "authRepository", "Lcom/transigo/app/data/repository/AuthRepository;", "(Lcom/transigo/app/data/repository/AuthRepository;)V", "_error", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_isLoading", "", "_user", "Lcom/transigo/app/data/model/User;", "error", "Lkotlinx/coroutines/flow/StateFlow;", "getError", "()Lkotlinx/coroutines/flow/StateFlow;", "isLoading", "user", "getUser", "clearError", "", "getErrorMessage", "exception", "", "isAuthenticated", "isValidEmail", "email", "isValidPassword", "password", "login", "onSuccess", "Lkotlin/Function0;", "logout", "observeAuthState", "register", "fullName", "resetPassword", "app_release"})
@dagger.hilt.android.lifecycle.HiltViewModel
public final class AuthViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.transigo.app.data.repository.AuthRepository authRepository = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isLoading = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _error = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.transigo.app.data.model.User> _user = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isLoading = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> error = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.transigo.app.data.model.User> user = null;
    
    @javax.inject.Inject
    public AuthViewModel(@org.jetbrains.annotations.NotNull
    com.transigo.app.data.repository.AuthRepository authRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isLoading() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getError() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.transigo.app.data.model.User> getUser() {
        return null;
    }
    
    /**
     * Observes the current user authentication state and updates the user StateFlow.
     * This will automatically update the UI when authentication state changes.
     */
    private final void observeAuthState() {
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
    public final void register(@org.jetbrains.annotations.NotNull
    java.lang.String email, @org.jetbrains.annotations.NotNull
    java.lang.String password, @org.jetbrains.annotations.NotNull
    java.lang.String fullName, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onSuccess) {
    }
    
    /**
     * Authenticates user with email and password.
     * Updates loading state and handles errors appropriately.
     *
     * @param email User's email address
     * @param password User's password
     * @param onSuccess Callback invoked when login succeeds
     */
    public final void login(@org.jetbrains.annotations.NotNull
    java.lang.String email, @org.jetbrains.annotations.NotNull
    java.lang.String password, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onSuccess) {
    }
    
    /**
     * Signs out the current user.
     * Clears user state and handles any errors.
     */
    public final void logout() {
    }
    
    /**
     * Sends a password reset email to the specified email address.
     *
     * @param email Email address to send reset link to
     * @param onSuccess Callback invoked when reset email is sent successfully
     */
    public final void resetPassword(@org.jetbrains.annotations.NotNull
    java.lang.String email, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onSuccess) {
    }
    
    /**
     * Clears any current error message.
     * Useful for dismissing error dialogs or messages.
     */
    public final void clearError() {
    }
    
    /**
     * Checks if the current user is authenticated.
     * @return true if user is authenticated, false otherwise
     */
    public final boolean isAuthenticated() {
        return false;
    }
    
    /**
     * Validates email format using a simple regex pattern.
     * @param email Email string to validate
     * @return true if email format is valid, false otherwise
     */
    private final boolean isValidEmail(java.lang.String email) {
        return false;
    }
    
    /**
     * Validates password meets minimum requirements.
     * @param password Password string to validate
     * @return true if password is valid, false otherwise
     */
    private final boolean isValidPassword(java.lang.String password) {
        return false;
    }
    
    /**
     * Converts various exception types to user-friendly error messages.
     * @param exception The exception to convert
     * @return User-friendly error message
     */
    private final java.lang.String getErrorMessage(java.lang.Throwable exception) {
        return null;
    }
}