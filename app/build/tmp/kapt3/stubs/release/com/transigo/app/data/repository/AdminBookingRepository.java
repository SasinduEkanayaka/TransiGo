package com.transigo.app.data.repository;

/**
 * Repository for admin booking operations.
 * Handles booking management, status updates, and driver assignments.
 */
@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u0019\b\u0007\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006JB\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u000e\u001a\u00020\u000f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u000f2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u000fH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00f8\u0001\u0002\u00a2\u0006\u0004\b\u0012\u0010\u0013J6\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u000e\u001a\u00020\u000f2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u000fH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00f8\u0001\u0002\u00a2\u0006\u0004\b\u0015\u0010\u0016J6\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u000e\u001a\u00020\u000f2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u000fH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00f8\u0001\u0002\u00a2\u0006\u0004\b\u0018\u0010\u0016J(\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001b0\u001a0\fH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00f8\u0001\u0002\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u001b\u0010\u001e\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020 0\u001a0\f0\u001f\u00f8\u0001\u0002J#\u0010!\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020 0\u001a0\f0\u001f2\u0006\u0010\"\u001a\u00020#\u00f8\u0001\u0002J,\u0010$\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010%0\f2\u0006\u0010&\u001a\u00020\u000fH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00f8\u0001\u0002\u00a2\u0006\u0004\b\'\u0010(J6\u0010)\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u000e\u001a\u00020\u000f2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u000fH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00f8\u0001\u0002\u00a2\u0006\u0004\b*\u0010\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000f\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b\u0019\u00a8\u0006+"}, d2 = {"Lcom/transigo/app/data/repository/AdminBookingRepository;", "", "firestore", "Lcom/google/firebase/firestore/FirebaseFirestore;", "notificationService", "Lcom/transigo/app/core/service/NotificationService;", "(Lcom/google/firebase/firestore/FirebaseFirestore;Lcom/transigo/app/core/service/NotificationService;)V", "bookingsCollection", "Lcom/google/firebase/firestore/CollectionReference;", "driversCollection", "usersCollection", "approveBooking", "Lkotlin/Result;", "", "bookingId", "", "driverId", "adminName", "approveBooking-BWLJW6A", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "cancelBooking", "cancelBooking-0E7RQCE", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "completeBooking", "completeBooking-0E7RQCE", "getActiveDrivers", "", "Lcom/transigo/app/data/model/Driver;", "getActiveDrivers-IoAF18A", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllBookings", "Lkotlinx/coroutines/flow/Flow;", "Lcom/transigo/app/data/model/Booking;", "getBookingsByStatus", "status", "Lcom/transigo/app/data/model/BookingStatus;", "getUser", "Lcom/transigo/app/data/model/User;", "userId", "getUser-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "rejectBooking", "rejectBooking-0E7RQCE", "app_release"})
public final class AdminBookingRepository {
    @org.jetbrains.annotations.NotNull
    private final com.google.firebase.firestore.FirebaseFirestore firestore = null;
    @org.jetbrains.annotations.NotNull
    private final com.transigo.app.core.service.NotificationService notificationService = null;
    @org.jetbrains.annotations.NotNull
    private final com.google.firebase.firestore.CollectionReference bookingsCollection = null;
    @org.jetbrains.annotations.NotNull
    private final com.google.firebase.firestore.CollectionReference usersCollection = null;
    @org.jetbrains.annotations.NotNull
    private final com.google.firebase.firestore.CollectionReference driversCollection = null;
    
    @javax.inject.Inject
    public AdminBookingRepository(@org.jetbrains.annotations.NotNull
    com.google.firebase.firestore.FirebaseFirestore firestore, @org.jetbrains.annotations.NotNull
    com.transigo.app.core.service.NotificationService notificationService) {
        super();
    }
    
    /**
     * Get bookings filtered by status
     */
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<kotlin.Result<java.util.List<com.transigo.app.data.model.Booking>>> getBookingsByStatus(@org.jetbrains.annotations.NotNull
    com.transigo.app.data.model.BookingStatus status) {
        return null;
    }
    
    /**
     * Get all bookings for admin overview
     */
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<kotlin.Result<java.util.List<com.transigo.app.data.model.Booking>>> getAllBookings() {
        return null;
    }
}