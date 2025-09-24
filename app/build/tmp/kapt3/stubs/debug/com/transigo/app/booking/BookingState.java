package com.transigo.app.booking;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b0\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\u00c3\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\n\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0011\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0013\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0015\u0012\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0017\u0012\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\u0019\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\u0011\u0012\b\b\u0002\u0010\u001c\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u001d\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u001eJ\t\u00107\u001a\u00020\u0003H\u00c6\u0003J\t\u00108\u001a\u00020\u0013H\u00c6\u0003J\t\u00109\u001a\u00020\u0015H\u00c6\u0003J\u000b\u0010:\u001a\u0004\u0018\u00010\u0017H\u00c6\u0003J\u000b\u0010;\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\t\u0010<\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010=\u001a\b\u0012\u0004\u0012\u00020\u001b0\u0011H\u00c6\u0003J\t\u0010>\u001a\u00020\u0003H\u00c6\u0003J\t\u0010?\u001a\u00020\u0003H\u00c6\u0003J\t\u0010@\u001a\u00020\u0005H\u00c6\u0003J\t\u0010A\u001a\u00020\u0007H\u00c6\u0003J\t\u0010B\u001a\u00020\u0007H\u00c6\u0003J\t\u0010C\u001a\u00020\nH\u00c6\u0003J\t\u0010D\u001a\u00020\nH\u00c6\u0003J\u000b\u0010E\u001a\u0004\u0018\u00010\rH\u00c6\u0003J\u000b\u0010F\u001a\u0004\u0018\u00010\u000fH\u00c6\u0003J\u000f\u0010G\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0011H\u00c6\u0003J\u00c7\u0001\u0010H\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\n2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u00152\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u00172\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\u0019\u001a\u00020\u00032\u000e\b\u0002\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\u00112\b\b\u0002\u0010\u001c\u001a\u00020\u00032\b\b\u0002\u0010\u001d\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010I\u001a\u00020\u00032\b\u0010J\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010K\u001a\u00020LH\u00d6\u0001J\t\u0010M\u001a\u00020\u0007H\u00d6\u0001R\u0017\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0011\u0010\b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0013\u0010\u0018\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\"R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0011\u0010\u0019\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010&R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010&R\u0011\u0010\u001c\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010&R\u0013\u0010\f\u001a\u0004\u0018\u00010\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010(R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\"R\u0011\u0010\u0012\u001a\u00020\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u0011\u0010\u001d\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010&R\u0011\u0010\u0014\u001a\u00020\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010.R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u0010 R\u0013\u0010\u0016\u001a\u0004\u0018\u00010\u0017\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u00101R\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b2\u00103R\u0011\u0010\u000b\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b4\u0010%R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b5\u00106\u00a8\u0006N"}, d2 = {"Lcom/transigo/app/booking/BookingState;", "", "isLoading", "", "type", "Lcom/transigo/app/data/model/BookingType;", "pickupName", "", "dropName", "fromLocation", "Lcom/transigo/app/data/model/Location;", "toLocation", "paymentMethod", "Lcom/transigo/app/data/model/PaymentMethod;", "selectedCard", "Lcom/transigo/app/data/model/SavedCard;", "savedCards", "", "pricing", "Lcom/transigo/app/data/model/BookingPricing;", "rideType", "Lcom/transigo/app/data/model/RideType;", "scheduledAt", "Lcom/google/firebase/Timestamp;", "error", "isCreated", "bookings", "Lcom/transigo/app/data/model/Booking;", "isRating", "ratingSuccess", "(ZLcom/transigo/app/data/model/BookingType;Ljava/lang/String;Ljava/lang/String;Lcom/transigo/app/data/model/Location;Lcom/transigo/app/data/model/Location;Lcom/transigo/app/data/model/PaymentMethod;Lcom/transigo/app/data/model/SavedCard;Ljava/util/List;Lcom/transigo/app/data/model/BookingPricing;Lcom/transigo/app/data/model/RideType;Lcom/google/firebase/Timestamp;Ljava/lang/String;ZLjava/util/List;ZZ)V", "getBookings", "()Ljava/util/List;", "getDropName", "()Ljava/lang/String;", "getError", "getFromLocation", "()Lcom/transigo/app/data/model/Location;", "()Z", "getPaymentMethod", "()Lcom/transigo/app/data/model/PaymentMethod;", "getPickupName", "getPricing", "()Lcom/transigo/app/data/model/BookingPricing;", "getRatingSuccess", "getRideType", "()Lcom/transigo/app/data/model/RideType;", "getSavedCards", "getScheduledAt", "()Lcom/google/firebase/Timestamp;", "getSelectedCard", "()Lcom/transigo/app/data/model/SavedCard;", "getToLocation", "getType", "()Lcom/transigo/app/data/model/BookingType;", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class BookingState {
    private final boolean isLoading = false;
    @org.jetbrains.annotations.NotNull
    private final com.transigo.app.data.model.BookingType type = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String pickupName = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String dropName = null;
    @org.jetbrains.annotations.NotNull
    private final com.transigo.app.data.model.Location fromLocation = null;
    @org.jetbrains.annotations.NotNull
    private final com.transigo.app.data.model.Location toLocation = null;
    @org.jetbrains.annotations.Nullable
    private final com.transigo.app.data.model.PaymentMethod paymentMethod = null;
    @org.jetbrains.annotations.Nullable
    private final com.transigo.app.data.model.SavedCard selectedCard = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.transigo.app.data.model.SavedCard> savedCards = null;
    @org.jetbrains.annotations.NotNull
    private final com.transigo.app.data.model.BookingPricing pricing = null;
    @org.jetbrains.annotations.NotNull
    private final com.transigo.app.data.model.RideType rideType = null;
    @org.jetbrains.annotations.Nullable
    private final com.google.firebase.Timestamp scheduledAt = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String error = null;
    private final boolean isCreated = false;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.transigo.app.data.model.Booking> bookings = null;
    private final boolean isRating = false;
    private final boolean ratingSuccess = false;
    
    public BookingState(boolean isLoading, @org.jetbrains.annotations.NotNull
    com.transigo.app.data.model.BookingType type, @org.jetbrains.annotations.NotNull
    java.lang.String pickupName, @org.jetbrains.annotations.NotNull
    java.lang.String dropName, @org.jetbrains.annotations.NotNull
    com.transigo.app.data.model.Location fromLocation, @org.jetbrains.annotations.NotNull
    com.transigo.app.data.model.Location toLocation, @org.jetbrains.annotations.Nullable
    com.transigo.app.data.model.PaymentMethod paymentMethod, @org.jetbrains.annotations.Nullable
    com.transigo.app.data.model.SavedCard selectedCard, @org.jetbrains.annotations.NotNull
    java.util.List<com.transigo.app.data.model.SavedCard> savedCards, @org.jetbrains.annotations.NotNull
    com.transigo.app.data.model.BookingPricing pricing, @org.jetbrains.annotations.NotNull
    com.transigo.app.data.model.RideType rideType, @org.jetbrains.annotations.Nullable
    com.google.firebase.Timestamp scheduledAt, @org.jetbrains.annotations.Nullable
    java.lang.String error, boolean isCreated, @org.jetbrains.annotations.NotNull
    java.util.List<com.transigo.app.data.model.Booking> bookings, boolean isRating, boolean ratingSuccess) {
        super();
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.transigo.app.data.model.BookingType getType() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getPickupName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getDropName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.transigo.app.data.model.Location getFromLocation() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.transigo.app.data.model.Location getToLocation() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.transigo.app.data.model.PaymentMethod getPaymentMethod() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.transigo.app.data.model.SavedCard getSelectedCard() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.transigo.app.data.model.SavedCard> getSavedCards() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.transigo.app.data.model.BookingPricing getPricing() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.transigo.app.data.model.RideType getRideType() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.google.firebase.Timestamp getScheduledAt() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getError() {
        return null;
    }
    
    public final boolean isCreated() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.transigo.app.data.model.Booking> getBookings() {
        return null;
    }
    
    public final boolean isRating() {
        return false;
    }
    
    public final boolean getRatingSuccess() {
        return false;
    }
    
    public BookingState() {
        super();
    }
    
    public final boolean component1() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.transigo.app.data.model.BookingPricing component10() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.transigo.app.data.model.RideType component11() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.google.firebase.Timestamp component12() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component13() {
        return null;
    }
    
    public final boolean component14() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.transigo.app.data.model.Booking> component15() {
        return null;
    }
    
    public final boolean component16() {
        return false;
    }
    
    public final boolean component17() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.transigo.app.data.model.BookingType component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.transigo.app.data.model.Location component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.transigo.app.data.model.Location component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.transigo.app.data.model.PaymentMethod component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.transigo.app.data.model.SavedCard component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.transigo.app.data.model.SavedCard> component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.transigo.app.booking.BookingState copy(boolean isLoading, @org.jetbrains.annotations.NotNull
    com.transigo.app.data.model.BookingType type, @org.jetbrains.annotations.NotNull
    java.lang.String pickupName, @org.jetbrains.annotations.NotNull
    java.lang.String dropName, @org.jetbrains.annotations.NotNull
    com.transigo.app.data.model.Location fromLocation, @org.jetbrains.annotations.NotNull
    com.transigo.app.data.model.Location toLocation, @org.jetbrains.annotations.Nullable
    com.transigo.app.data.model.PaymentMethod paymentMethod, @org.jetbrains.annotations.Nullable
    com.transigo.app.data.model.SavedCard selectedCard, @org.jetbrains.annotations.NotNull
    java.util.List<com.transigo.app.data.model.SavedCard> savedCards, @org.jetbrains.annotations.NotNull
    com.transigo.app.data.model.BookingPricing pricing, @org.jetbrains.annotations.NotNull
    com.transigo.app.data.model.RideType rideType, @org.jetbrains.annotations.Nullable
    com.google.firebase.Timestamp scheduledAt, @org.jetbrains.annotations.Nullable
    java.lang.String error, boolean isCreated, @org.jetbrains.annotations.NotNull
    java.util.List<com.transigo.app.data.model.Booking> bookings, boolean isRating, boolean ratingSuccess) {
        return null;
    }
    
    @java.lang.Override
    public boolean equals(@org.jetbrains.annotations.Nullable
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public java.lang.String toString() {
        return null;
    }
}