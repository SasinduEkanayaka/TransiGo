package com.transigo.app.booking;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\f\u001a\u00020\rJ\u0006\u0010\u000e\u001a\u00020\rJ\u000e\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0011J\u001a\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u00150\u00142\u0006\u0010\u0010\u001a\u00020\u0011J\u001e\u0010\u0017\u001a\u00020\r2\u0006\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u0011J\u0006\u0010\u001c\u001a\u00020\rJ\u000e\u0010\u001d\u001a\u00020\r2\u0006\u0010\u001e\u001a\u00020\u0011J\u000e\u0010\u001f\u001a\u00020\r2\u0006\u0010 \u001a\u00020\u0011J\u000e\u0010!\u001a\u00020\r2\u0006\u0010\"\u001a\u00020#J\u0010\u0010$\u001a\u00020\r2\b\u0010%\u001a\u0004\u0018\u00010&J\u000e\u0010\'\u001a\u00020\r2\u0006\u0010(\u001a\u00020)R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006*"}, d2 = {"Lcom/transigo/app/booking/BookingViewModel;", "Landroidx/lifecycle/ViewModel;", "bookingRepository", "Lcom/transigo/app/data/repository/BookingRepository;", "(Lcom/transigo/app/data/repository/BookingRepository;)V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/transigo/app/booking/BookingState;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "clearError", "", "clearRatingState", "createBooking", "userId", "", "loadMyBookings", "myBookingsFlow", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/transigo/app/data/model/Booking;", "rateBooking", "bookingId", "stars", "", "comment", "resetForm", "updateDropName", "dropName", "updatePickupName", "pickupName", "updateRideType", "rideType", "Lcom/transigo/app/data/model/RideType;", "updateScheduledAt", "scheduledAt", "Lcom/google/firebase/Timestamp;", "updateType", "type", "Lcom/transigo/app/data/model/BookingType;", "app_debug"})
public final class BookingViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.transigo.app.data.repository.BookingRepository bookingRepository = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.transigo.app.booking.BookingState> _state = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.transigo.app.booking.BookingState> state = null;
    
    public BookingViewModel(@org.jetbrains.annotations.NotNull
    com.transigo.app.data.repository.BookingRepository bookingRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.transigo.app.booking.BookingState> getState() {
        return null;
    }
    
    public final void updateType(@org.jetbrains.annotations.NotNull
    com.transigo.app.data.model.BookingType type) {
    }
    
    public final void updatePickupName(@org.jetbrains.annotations.NotNull
    java.lang.String pickupName) {
    }
    
    public final void updateDropName(@org.jetbrains.annotations.NotNull
    java.lang.String dropName) {
    }
    
    public final void updateRideType(@org.jetbrains.annotations.NotNull
    com.transigo.app.data.model.RideType rideType) {
    }
    
    public final void updateScheduledAt(@org.jetbrains.annotations.Nullable
    com.google.firebase.Timestamp scheduledAt) {
    }
    
    public final void clearError() {
    }
    
    public final void createBooking(@org.jetbrains.annotations.NotNull
    java.lang.String userId) {
    }
    
    public final void loadMyBookings(@org.jetbrains.annotations.NotNull
    java.lang.String userId) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.transigo.app.data.model.Booking>> myBookingsFlow(@org.jetbrains.annotations.NotNull
    java.lang.String userId) {
        return null;
    }
    
    public final void rateBooking(@org.jetbrains.annotations.NotNull
    java.lang.String bookingId, int stars, @org.jetbrains.annotations.NotNull
    java.lang.String comment) {
    }
    
    public final void clearRatingState() {
    }
    
    public final void resetForm() {
    }
    
    public BookingViewModel() {
        super();
    }
}