package com.transigo.app.booking;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b \n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B}\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0014J\t\u0010#\u001a\u00020\u0003H\u00c6\u0003J\t\u0010$\u001a\u00020\u0003H\u00c6\u0003J\t\u0010%\u001a\u00020\u0003H\u00c6\u0003J\t\u0010&\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\'\u001a\u00020\u0007H\u00c6\u0003J\t\u0010(\u001a\u00020\u0007H\u00c6\u0003J\t\u0010)\u001a\u00020\nH\u00c6\u0003J\u000b\u0010*\u001a\u0004\u0018\u00010\fH\u00c6\u0003J\u000b\u0010+\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\t\u0010,\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010-\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H\u00c6\u0003J\u0081\u0001\u0010.\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\u000e\u001a\u00020\u00032\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\b\b\u0002\u0010\u0012\u001a\u00020\u00032\b\b\u0002\u0010\u0013\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010/\u001a\u00020\u00032\b\u00100\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00101\u001a\u000202H\u00d6\u0001J\t\u00103\u001a\u00020\u0007H\u00d6\u0001R\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0013\u0010\r\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0018R\u0011\u0010\u000e\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u001aR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u001aR\u0011\u0010\u0012\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u001aR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0018R\u0011\u0010\u0013\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001aR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0013\u0010\u000b\u001a\u0004\u0018\u00010\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"\u00a8\u00064"}, d2 = {"Lcom/transigo/app/booking/BookingState;", "", "isLoading", "", "type", "Lcom/transigo/app/data/model/BookingType;", "pickupName", "", "dropName", "rideType", "Lcom/transigo/app/data/model/RideType;", "scheduledAt", "Lcom/google/firebase/Timestamp;", "error", "isCreated", "bookings", "", "Lcom/transigo/app/data/model/Booking;", "isRating", "ratingSuccess", "(ZLcom/transigo/app/data/model/BookingType;Ljava/lang/String;Ljava/lang/String;Lcom/transigo/app/data/model/RideType;Lcom/google/firebase/Timestamp;Ljava/lang/String;ZLjava/util/List;ZZ)V", "getBookings", "()Ljava/util/List;", "getDropName", "()Ljava/lang/String;", "getError", "()Z", "getPickupName", "getRatingSuccess", "getRideType", "()Lcom/transigo/app/data/model/RideType;", "getScheduledAt", "()Lcom/google/firebase/Timestamp;", "getType", "()Lcom/transigo/app/data/model/BookingType;", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class BookingState {
    private final boolean isLoading = false;
    @org.jetbrains.annotations.NotNull
    private final com.transigo.app.data.model.BookingType type = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String pickupName = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String dropName = null;
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
    
    public final boolean component10() {
        return false;
    }
    
    public final boolean component11() {
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
    public final com.transigo.app.data.model.RideType component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.google.firebase.Timestamp component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component7() {
        return null;
    }
    
    public final boolean component8() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.transigo.app.data.model.Booking> component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.transigo.app.booking.BookingState copy(boolean isLoading, @org.jetbrains.annotations.NotNull
    com.transigo.app.data.model.BookingType type, @org.jetbrains.annotations.NotNull
    java.lang.String pickupName, @org.jetbrains.annotations.NotNull
    java.lang.String dropName, @org.jetbrains.annotations.NotNull
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