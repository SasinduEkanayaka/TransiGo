package com.transigo.app.payment;

@kotlin.Metadata(mv = {1, 8, 0}, k = 2, xi = 48, d1 = {"\u00006\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\u001a0\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\tH\u0007\u001a&\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u0010H\u0003\u001a\u0010\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u0005H\u0002\u001a\u0010\u0010\u0013\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u0005H\u0002\u001a(\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u00020\u0005H\u0002\u00a8\u0006\u0019"}, d2 = {"PaymentMethodScreen", "", "navController", "Landroidx/navigation/NavController;", "selectedMethod", "", "bookingViewModel", "Lcom/transigo/app/booking/BookingViewModel;", "authViewModel", "Lcom/transigo/app/auth/AuthViewModel;", "SavedCardItem", "card", "Lcom/transigo/app/data/model/SavedCard;", "isSelected", "", "onSelected", "Lkotlin/Function0;", "formatCardNumber", "input", "formatExpiryDate", "isNewCardValid", "cardNumber", "cardholderName", "expiryDate", "cvv", "app_debug"})
public final class PaymentMethodScreenKt {
    
    @androidx.compose.runtime.Composable
    public static final void PaymentMethodScreen(@org.jetbrains.annotations.NotNull
    androidx.navigation.NavController navController, @org.jetbrains.annotations.Nullable
    java.lang.String selectedMethod, @org.jetbrains.annotations.NotNull
    com.transigo.app.booking.BookingViewModel bookingViewModel, @org.jetbrains.annotations.NotNull
    com.transigo.app.auth.AuthViewModel authViewModel) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void SavedCardItem(com.transigo.app.data.model.SavedCard card, boolean isSelected, kotlin.jvm.functions.Function0<kotlin.Unit> onSelected) {
    }
    
    private static final java.lang.String formatCardNumber(java.lang.String input) {
        return null;
    }
    
    private static final java.lang.String formatExpiryDate(java.lang.String input) {
        return null;
    }
    
    private static final boolean isNewCardValid(java.lang.String cardNumber, java.lang.String cardholderName, java.lang.String expiryDate, java.lang.String cvv) {
        return false;
    }
}