package com.transigo.app.data.repository;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J2\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\fH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00f8\u0001\u0002\u00a2\u0006\u0004\b\r\u0010\u000eJ,\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100\b2\u0006\u0010\u0011\u001a\u00020\tH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00f8\u0001\u0002\u00a2\u0006\u0004\b\u0012\u0010\u0013J#\u0010\u0014\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u00160\b0\u00152\u0006\u0010\n\u001a\u00020\t\u00f8\u0001\u0002J:\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\b2\u0006\u0010\u0011\u001a\u00020\t2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\tH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00f8\u0001\u0002\u00a2\u0006\u0004\b\u001c\u0010\u001dJ2\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00180\b2\u0006\u0010\u0011\u001a\u00020\t2\u0006\u0010\u001f\u001a\u00020 H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00f8\u0001\u0002\u00a2\u0006\u0004\b!\u0010\"R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000f\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b\u0019\u00a8\u0006#"}, d2 = {"Lcom/transigo/app/data/repository/BookingRepository;", "", "firestore", "Lcom/google/firebase/firestore/FirebaseFirestore;", "(Lcom/google/firebase/firestore/FirebaseFirestore;)V", "bookingsCollection", "Lcom/google/firebase/firestore/CollectionReference;", "createBooking", "Lkotlin/Result;", "", "userId", "form", "Lcom/transigo/app/data/repository/BookingForm;", "createBooking-0E7RQCE", "(Ljava/lang/String;Lcom/transigo/app/data/repository/BookingForm;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getBooking", "Lcom/transigo/app/data/model/Booking;", "bookingId", "getBooking-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "myBookingsQuery", "Lkotlinx/coroutines/flow/Flow;", "", "rateBooking", "", "stars", "", "comment", "rateBooking-BWLJW6A", "(Ljava/lang/String;ILjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateBookingStatus", "status", "Lcom/transigo/app/data/model/BookingStatus;", "updateBookingStatus-0E7RQCE", "(Ljava/lang/String;Lcom/transigo/app/data/model/BookingStatus;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_release"})
public final class BookingRepository {
    @org.jetbrains.annotations.NotNull
    private final com.google.firebase.firestore.FirebaseFirestore firestore = null;
    @org.jetbrains.annotations.NotNull
    private final com.google.firebase.firestore.CollectionReference bookingsCollection = null;
    
    public BookingRepository(@org.jetbrains.annotations.NotNull
    com.google.firebase.firestore.FirebaseFirestore firestore) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<kotlin.Result<java.util.List<com.transigo.app.data.model.Booking>>> myBookingsQuery(@org.jetbrains.annotations.NotNull
    java.lang.String userId) {
        return null;
    }
    
    public BookingRepository() {
        super();
    }
}