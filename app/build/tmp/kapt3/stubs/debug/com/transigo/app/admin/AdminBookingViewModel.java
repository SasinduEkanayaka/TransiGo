package com.transigo.app.admin;

/**
 * ViewModel for admin booking management.
 * Handles booking operations, status filtering, and user/driver data.
 */
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u0002\n\u0002\b\u0011\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001a\u0010\'\u001a\u00020(2\u0006\u0010)\u001a\u00020\u00072\n\b\u0002\u0010*\u001a\u0004\u0018\u00010\u0007J\u000e\u0010+\u001a\u00020(2\u0006\u0010)\u001a\u00020\u0007J\u0006\u0010,\u001a\u00020(J\u000e\u0010-\u001a\u00020(2\u0006\u0010)\u001a\u00020\u0007J\u0006\u0010.\u001a\u00020(J\u0010\u0010/\u001a\u00020\u00072\b\u0010*\u001a\u0004\u0018\u00010\u0007J\u000e\u00100\u001a\u00020\u00072\u0006\u00101\u001a\u00020\u0007J\u0006\u00102\u001a\u00020(J\u0006\u00103\u001a\u00020(J\u0006\u00104\u001a\u00020(J\u0016\u00105\u001a\u00020(2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\f0\tH\u0002J\u000e\u00106\u001a\u00020(2\u0006\u0010)\u001a\u00020\u0007J\u000e\u00107\u001a\u00020(2\u0006\u00108\u001a\u00020\u000eR\u0016\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\u0013\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00150\u00140\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u0016\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0017\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u001d\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u0017\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0019R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\t0\u0017\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0019R\u0017\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0017\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0019R\u0019\u0010 \u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0017\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0019R\u0017\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00110\u0017\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0019R\u0017\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00110\u0017\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0019R#\u0010%\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00150\u00140\u0017\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0019\u00a8\u00069"}, d2 = {"Lcom/transigo/app/admin/AdminBookingViewModel;", "Landroidx/lifecycle/ViewModel;", "adminBookingRepository", "Lcom/transigo/app/data/repository/AdminBookingRepository;", "(Lcom/transigo/app/data/repository/AdminBookingRepository;)V", "_actionInProgress", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_activeDrivers", "", "Lcom/transigo/app/data/model/Driver;", "_bookings", "Lcom/transigo/app/data/model/Booking;", "_currentFilter", "Lcom/transigo/app/data/model/BookingStatus;", "_error", "_isLoading", "", "_showingAllBookings", "_users", "", "Lcom/transigo/app/data/model/User;", "actionInProgress", "Lkotlinx/coroutines/flow/StateFlow;", "getActionInProgress", "()Lkotlinx/coroutines/flow/StateFlow;", "activeDrivers", "getActiveDrivers", "bookings", "getBookings", "currentFilter", "getCurrentFilter", "error", "getError", "isLoading", "showingAllBookings", "getShowingAllBookings", "users", "getUsers", "approveBooking", "", "bookingId", "driverId", "cancelBooking", "clearError", "completeBooking", "createSampleData", "getDriverName", "getUserEmail", "userId", "loadActiveDrivers", "loadAllBookings", "loadBookings", "loadUsersForBookings", "rejectBooking", "setFilter", "status", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel
public final class AdminBookingViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.transigo.app.data.repository.AdminBookingRepository adminBookingRepository = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.transigo.app.data.model.BookingStatus> _currentFilter = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.transigo.app.data.model.BookingStatus> currentFilter = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _showingAllBookings = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> showingAllBookings = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.transigo.app.data.model.Booking>> _bookings = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.transigo.app.data.model.Booking>> bookings = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isLoading = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isLoading = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _error = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> error = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.Map<java.lang.String, com.transigo.app.data.model.User>> _users = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.Map<java.lang.String, com.transigo.app.data.model.User>> users = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.transigo.app.data.model.Driver>> _activeDrivers = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.transigo.app.data.model.Driver>> activeDrivers = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _actionInProgress = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> actionInProgress = null;
    
    @javax.inject.Inject
    public AdminBookingViewModel(@org.jetbrains.annotations.NotNull
    com.transigo.app.data.repository.AdminBookingRepository adminBookingRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.transigo.app.data.model.BookingStatus> getCurrentFilter() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getShowingAllBookings() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.transigo.app.data.model.Booking>> getBookings() {
        return null;
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
    public final kotlinx.coroutines.flow.StateFlow<java.util.Map<java.lang.String, com.transigo.app.data.model.User>> getUsers() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.transigo.app.data.model.Driver>> getActiveDrivers() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getActionInProgress() {
        return null;
    }
    
    /**
     * Set the current filter and reload bookings
     */
    public final void setFilter(@org.jetbrains.annotations.NotNull
    com.transigo.app.data.model.BookingStatus status) {
    }
    
    /**
     * Load bookings based on current filter
     */
    public final void loadBookings() {
    }
    
    /**
     * Load all bookings regardless of status
     */
    public final void loadAllBookings() {
    }
    
    /**
     * Load user details for bookings
     */
    private final void loadUsersForBookings(java.util.List<com.transigo.app.data.model.Booking> bookings) {
    }
    
    /**
     * Load active drivers for assignment
     */
    public final void loadActiveDrivers() {
    }
    
    /**
     * Approve a booking
     */
    public final void approveBooking(@org.jetbrains.annotations.NotNull
    java.lang.String bookingId, @org.jetbrains.annotations.Nullable
    java.lang.String driverId) {
    }
    
    /**
     * Reject a booking
     */
    public final void rejectBooking(@org.jetbrains.annotations.NotNull
    java.lang.String bookingId) {
    }
    
    /**
     * Complete a booking
     */
    public final void completeBooking(@org.jetbrains.annotations.NotNull
    java.lang.String bookingId) {
    }
    
    /**
     * Cancel a booking
     */
    public final void cancelBooking(@org.jetbrains.annotations.NotNull
    java.lang.String bookingId) {
    }
    
    /**
     * Clear error message
     */
    public final void clearError() {
    }
    
    /**
     * Get user email for a user ID
     */
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getUserEmail(@org.jetbrains.annotations.NotNull
    java.lang.String userId) {
        return null;
    }
    
    /**
     * Get driver name for a driver ID
     */
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getDriverName(@org.jetbrains.annotations.Nullable
    java.lang.String driverId) {
        return null;
    }
    
    /**
     * Create sample data for testing
     */
    public final void createSampleData() {
    }
}