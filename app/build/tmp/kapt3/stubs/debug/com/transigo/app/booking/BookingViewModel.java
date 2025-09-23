package com.transigo.app.booking;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\f\u001a\u00020\rH\u0002J\u0006\u0010\u000e\u001a\u00020\rJ\u0006\u0010\u000f\u001a\u00020\rJ\u0006\u0010\u0010\u001a\u00020\rJ\u000e\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u0013J.\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0014\u0010\u0016\u001a\u0010\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u0018\u0018\u00010\u0017J\u0010\u0010\u0019\u001a\u00020\u00132\u0006\u0010\u001a\u001a\u00020\u0013H\u0002J\u000e\u0010\u001b\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u0013J\u000e\u0010\u001c\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u0013J\u001a\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020 0\u001f0\u001e2\u0006\u0010\u0012\u001a\u00020\u0013J\u001e\u0010!\u001a\u00020\r2\u0006\u0010\"\u001a\u00020\u00132\u0006\u0010#\u001a\u00020\u00182\u0006\u0010$\u001a\u00020\u0013J\u0006\u0010%\u001a\u00020\rJ.\u0010&\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u001a\u001a\u00020\u00132\u0006\u0010\'\u001a\u00020\u00132\u0006\u0010(\u001a\u00020\u00132\u0006\u0010)\u001a\u00020\u0013J\u000e\u0010*\u001a\u00020\r2\u0006\u0010+\u001a\u00020,J\u000e\u0010-\u001a\u00020\r2\u0006\u0010.\u001a\u00020\u0013J\u000e\u0010/\u001a\u00020\r2\u0006\u00100\u001a\u000201J\u000e\u00102\u001a\u00020\r2\u0006\u00103\u001a\u00020\u0013J\u000e\u00104\u001a\u00020\r2\u0006\u00105\u001a\u000206J\u000e\u00107\u001a\u00020\r2\u0006\u00108\u001a\u00020\u0013J\u000e\u00109\u001a\u00020\r2\u0006\u0010:\u001a\u00020;J\u0010\u0010<\u001a\u00020\r2\b\u0010=\u001a\u0004\u0018\u00010>J\u000e\u0010?\u001a\u00020\r2\u0006\u00100\u001a\u000201J\u000e\u0010@\u001a\u00020\r2\u0006\u00103\u001a\u00020\u0013J\u000e\u0010A\u001a\u00020\r2\u0006\u0010B\u001a\u00020CR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006D"}, d2 = {"Lcom/transigo/app/booking/BookingViewModel;", "Landroidx/lifecycle/ViewModel;", "bookingRepository", "Lcom/transigo/app/data/repository/BookingRepository;", "(Lcom/transigo/app/data/repository/BookingRepository;)V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/transigo/app/booking/BookingState;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "calculatePricing", "", "clearError", "clearRatingState", "clearSelectedCard", "createBooking", "userId", "", "selectedDate", "Ljava/util/Date;", "selectedTime", "Lkotlin/Pair;", "", "getCardType", "cardNumber", "loadMyBookings", "loadSavedCards", "myBookingsFlow", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/transigo/app/data/model/Booking;", "rateBooking", "bookingId", "stars", "comment", "resetForm", "saveCard", "cardholderName", "expiryDate", "cvv", "selectCard", "card", "Lcom/transigo/app/data/model/SavedCard;", "updateDropName", "dropName", "updateFromLocation", "location", "Lcom/transigo/app/data/model/Location;", "updateFromLocationAddress", "address", "updatePaymentMethod", "paymentMethod", "Lcom/transigo/app/data/model/PaymentMethod;", "updatePickupName", "pickupName", "updateRideType", "rideType", "Lcom/transigo/app/data/model/RideType;", "updateScheduledAt", "scheduledAt", "Lcom/google/firebase/Timestamp;", "updateToLocation", "updateToLocationAddress", "updateType", "type", "Lcom/transigo/app/data/model/BookingType;", "app_debug"})
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
    
    public final void updateFromLocation(@org.jetbrains.annotations.NotNull
    com.transigo.app.data.model.Location location) {
    }
    
    public final void updateToLocation(@org.jetbrains.annotations.NotNull
    com.transigo.app.data.model.Location location) {
    }
    
    public final void updateFromLocationAddress(@org.jetbrains.annotations.NotNull
    java.lang.String address) {
    }
    
    public final void updateToLocationAddress(@org.jetbrains.annotations.NotNull
    java.lang.String address) {
    }
    
    public final void updatePaymentMethod(@org.jetbrains.annotations.NotNull
    com.transigo.app.data.model.PaymentMethod paymentMethod) {
    }
    
    public final void updateRideType(@org.jetbrains.annotations.NotNull
    com.transigo.app.data.model.RideType rideType) {
    }
    
    public final void updateScheduledAt(@org.jetbrains.annotations.Nullable
    com.google.firebase.Timestamp scheduledAt) {
    }
    
    private final void calculatePricing() {
    }
    
    public final void clearError() {
    }
    
    public final void createBooking(@org.jetbrains.annotations.NotNull
    java.lang.String userId, @org.jetbrains.annotations.Nullable
    java.util.Date selectedDate, @org.jetbrains.annotations.Nullable
    kotlin.Pair<java.lang.Integer, java.lang.Integer> selectedTime) {
    }
    
    public final void createBooking(@org.jetbrains.annotations.NotNull
    java.lang.String userId) {
    }
    
    public final void resetForm() {
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
    
    public final void loadSavedCards(@org.jetbrains.annotations.NotNull
    java.lang.String userId) {
    }
    
    public final void saveCard(@org.jetbrains.annotations.NotNull
    java.lang.String userId, @org.jetbrains.annotations.NotNull
    java.lang.String cardNumber, @org.jetbrains.annotations.NotNull
    java.lang.String cardholderName, @org.jetbrains.annotations.NotNull
    java.lang.String expiryDate, @org.jetbrains.annotations.NotNull
    java.lang.String cvv) {
    }
    
    private final java.lang.String getCardType(java.lang.String cardNumber) {
        return null;
    }
    
    public final void selectCard(@org.jetbrains.annotations.NotNull
    com.transigo.app.data.model.SavedCard card) {
    }
    
    public final void clearSelectedCard() {
    }
    
    public BookingViewModel() {
        super();
    }
}