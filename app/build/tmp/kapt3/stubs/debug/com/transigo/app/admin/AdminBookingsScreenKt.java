package com.transigo.app.admin;

@kotlin.Metadata(mv = {1, 8, 0}, k = 2, xi = 48, d1 = {"\u0000Z\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\u001a\u001a\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u0007\u001a<\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\u00052\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u000eH\u0007\u001a>\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0004\u001a\u00020\u00052\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u000eH\u0007\u001aD\u0010\u0012\u001a\u00020\u00012\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\b0\u00142\u0006\u0010\t\u001a\u00020\n2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0004\u001a\u00020\u00052\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u000eH\u0007\u001aF\u0010\u0015\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u00142\u0018\u0010\u0018\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00010\u00192\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00010\u001bH\u0007\u001a\u0010\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\nH\u0007\u001a,\u0010\u001e\u001a\u00020\u00012\u0006\u0010\u001f\u001a\u00020\u00112\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00010\u001b2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00010\u001bH\u0007\u001a$\u0010!\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\n2\u0012\u0010\"\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010\u000eH\u0007\u001a\u0010\u0010#\u001a\u00020\u00012\u0006\u0010$\u001a\u00020\nH\u0007\u001a\u0012\u0010%\u001a\u00020\u00112\b\u0010&\u001a\u0004\u0018\u00010\'H\u0002\u00a8\u0006("}, d2 = {"AdminBookingsScreen", "", "navController", "Landroidx/navigation/NavController;", "viewModel", "Lcom/transigo/app/admin/AdminBookingViewModel;", "BookingActions", "booking", "Lcom/transigo/app/data/model/Booking;", "currentFilter", "Lcom/transigo/app/data/model/BookingStatus;", "isLoading", "", "onAssignDriver", "Lkotlin/Function1;", "BookingCard", "actionInProgress", "", "BookingsList", "bookings", "", "DriverSelectionDialog", "drivers", "Lcom/transigo/app/data/model/Driver;", "onDriverSelected", "Lkotlin/Function2;", "onDismiss", "Lkotlin/Function0;", "EmptyStateContent", "filter", "ErrorContent", "error", "onRetry", "FilterTabs", "onFilterChanged", "StatusChip", "status", "formatTimestamp", "timestamp", "Lcom/google/firebase/Timestamp;", "app_debug"})
public final class AdminBookingsScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void AdminBookingsScreen(@org.jetbrains.annotations.NotNull
    androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull
    com.transigo.app.admin.AdminBookingViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void FilterTabs(@org.jetbrains.annotations.NotNull
    com.transigo.app.data.model.BookingStatus currentFilter, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.transigo.app.data.model.BookingStatus, kotlin.Unit> onFilterChanged) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void BookingsList(@org.jetbrains.annotations.NotNull
    java.util.List<com.transigo.app.data.model.Booking> bookings, @org.jetbrains.annotations.NotNull
    com.transigo.app.data.model.BookingStatus currentFilter, @org.jetbrains.annotations.Nullable
    java.lang.String actionInProgress, @org.jetbrains.annotations.NotNull
    com.transigo.app.admin.AdminBookingViewModel viewModel, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.transigo.app.data.model.Booking, kotlin.Unit> onAssignDriver) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void BookingCard(@org.jetbrains.annotations.NotNull
    com.transigo.app.data.model.Booking booking, @org.jetbrains.annotations.NotNull
    com.transigo.app.data.model.BookingStatus currentFilter, @org.jetbrains.annotations.Nullable
    java.lang.String actionInProgress, @org.jetbrains.annotations.NotNull
    com.transigo.app.admin.AdminBookingViewModel viewModel, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.transigo.app.data.model.Booking, kotlin.Unit> onAssignDriver) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void StatusChip(@org.jetbrains.annotations.NotNull
    com.transigo.app.data.model.BookingStatus status) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void BookingActions(@org.jetbrains.annotations.NotNull
    com.transigo.app.data.model.Booking booking, @org.jetbrains.annotations.NotNull
    com.transigo.app.data.model.BookingStatus currentFilter, boolean isLoading, @org.jetbrains.annotations.NotNull
    com.transigo.app.admin.AdminBookingViewModel viewModel, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.transigo.app.data.model.Booking, kotlin.Unit> onAssignDriver) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void DriverSelectionDialog(@org.jetbrains.annotations.NotNull
    com.transigo.app.data.model.Booking booking, @org.jetbrains.annotations.NotNull
    java.util.List<com.transigo.app.data.model.Driver> drivers, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function2<? super com.transigo.app.data.model.Booking, ? super java.lang.String, kotlin.Unit> onDriverSelected, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void EmptyStateContent(@org.jetbrains.annotations.NotNull
    com.transigo.app.data.model.BookingStatus filter) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void ErrorContent(@org.jetbrains.annotations.NotNull
    java.lang.String error, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onRetry, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
    
    private static final java.lang.String formatTimestamp(com.google.firebase.Timestamp timestamp) {
        return null;
    }
}