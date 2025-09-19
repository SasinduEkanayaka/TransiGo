package com.transigo.app.booking;

@kotlin.Metadata(mv = {1, 8, 0}, k = 2, xi = 48, d1 = {"\u0000L\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a&\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0014\b\u0002\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a$\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\fH\u0007\u001ah\u0010\r\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\u001326\u0010\u0014\u001a2\u0012\u0013\u0012\u00110\u0016\u00a2\u0006\f\b\u0017\u0012\b\b\u0018\u0012\u0004\b\b(\u0019\u0012\u0013\u0012\u00110\u0011\u00a2\u0006\f\b\u0017\u0012\b\b\u0018\u0012\u0004\b\b(\u001a\u0012\u0004\u0012\u00020\u00010\u0015H\u0007\u00a8\u0006\u001b"}, d2 = {"BookingCard", "", "booking", "Lcom/transigo/app/data/model/Booking;", "onRateClick", "Lkotlin/Function1;", "BookingHistoryScreen", "navController", "Landroidx/navigation/NavController;", "bookingViewModel", "Lcom/transigo/app/booking/BookingViewModel;", "authViewModel", "Lcom/transigo/app/auth/AuthViewModel;", "RatingBottomSheet", "isLoading", "", "error", "", "onDismiss", "Lkotlin/Function0;", "onSubmitRating", "Lkotlin/Function2;", "", "Lkotlin/ParameterName;", "name", "stars", "comment", "app_release"})
public final class BookingHistoryScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void BookingHistoryScreen(@org.jetbrains.annotations.NotNull
    androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull
    com.transigo.app.booking.BookingViewModel bookingViewModel, @org.jetbrains.annotations.NotNull
    com.transigo.app.auth.AuthViewModel authViewModel) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void BookingCard(@org.jetbrains.annotations.NotNull
    com.transigo.app.data.model.Booking booking, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.transigo.app.data.model.Booking, kotlin.Unit> onRateClick) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void RatingBottomSheet(@org.jetbrains.annotations.NotNull
    com.transigo.app.data.model.Booking booking, boolean isLoading, @org.jetbrains.annotations.Nullable
    java.lang.String error, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function2<? super java.lang.Integer, ? super java.lang.String, kotlin.Unit> onSubmitRating) {
    }
}